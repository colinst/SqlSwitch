package com.recruit.controller.data;

import com.alibaba.fastjson.JSONArray;
import com.recruit.model.Column;
import com.recruit.service.data.DataCompareService;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 郭宏禧
 * @createTime 2017/8/19
 * @context **
 */
@Controller
@RequestMapping("/data")
public class DataCompareController {

    private Map<String, List<Column>> dba1;//数据库Map
    private Map<String, List<Column>> dba2;
    private DynamicDataSource dynamicDataSource = new DynamicDataSource();//
    private RegisterDataSource registerDataSource = new RegisterDataSource();

    @Resource
    private DataCompareService service;




    //取得JSON格式的数据库Map
    @RequestMapping(value = "getdba.do",produces="text/plain;charset=utf8")
    @ResponseBody  //返回ajax数据
    public String getDba(String dba_name){

        Map dataBase = getDbaMap(dba_name);

        String str = JSONArray.toJSONString(dataBase);
        return str;
    }


    //测试
    @RequestMapping(value = "test.do",produces="text/plain;charset=utf8")
    @ResponseBody  //返回ajax数据
    public String test(){

        String str = JSONArray.toJSONString("");
        return str;
    }
    //测试1
    @RequestMapping("test1.do")
    public void test1(){

        System.out.println("测试===");
        System.out.println(Dbs.getDbType());
        //dynamicDataSource.determineCurrentLookupKey();


        List list = service.getDbaNames();
        System.out.println(list);

        registerDataSource.onApplicationEvent();

        Dbs.setDbType("1");
        list=service.getDbaNames();


        Dbs.setDbType("0");
        list=service.getDbaNames();


        dynamicDataSource.selectDataSource("0");


    }





    //格式化打印Map====================================================
    public void printDba(Map<String,List<Column>> dbaa){

        for (Object table_name:dbaa.keySet()) {
            System.out.println(table_name);
            List<Column> columns = dbaa.get(table_name);

            for (Column column:columns) {
                System.out.println("        "+column);
            }
        }
    }

    //获得数据库Map(通过数据库名)
    public Map<String,List<Column>> getDbaMap(String dba_name){

        Map<String,List<Column>> dataBase = new HashMap();

        //获得所有的表名
        List<String> table_names = service.getTableNames(dba_name);

        //填充Map
        for (String table_name:table_names) {
            //补全SQL查询中的tableName
            String fullTableName = dba_name + "." + table_name;
            //得到表结构
            List<Column> table_columns = service.getTableByName(fullTableName);
            //添加键值对到dataBase
            dataBase.put(table_name,table_columns );
        }

        return dataBase;
    }

    //表对象对比返回结果List（通过两表名）
    public List<Column> getResults(String table_name1,String table_name2){

        List<Column> cols1 = service.getTableByName(table_name1);//基准表
        List<Column> cols2 = service.getTableByName(table_name2);//目标对象
        List<Column> results = new LinkedList<Column>();//对比结果     相同属性标记为0  不同属性标记为1

        for (int i = 0; i <cols1.size() ; i++) {
            for (int j = 0; j <cols2.size() ; j++) {

                //两列名相同才进行比较
                if (cols1.get(i).getField().equals(cols2.get(j).getField())) {

                    //添加列名
                    Column result = new Column();
                    result.setField(cols1.get(i).getField());
                    //空保护处理

                    if (cols1.get(i).getType() == null)     cols1.get(i).setType("");
                    if (cols1.get(i).getKey() == null)     cols1.get(i).setKey("");
                    if (cols1.get(i).getNull() == null)     cols1.get(i).setNull("");
                    if (cols1.get(i).getDefault() == null)     cols1.get(i).setDefault("");
                    if (cols1.get(i).getExtra() == null)     cols1.get(i).setExtra("");

                    if (cols2.get(j).getType() == null)     cols2.get(j).setType("");
                    if (cols2.get(j).getKey() == null)     cols2.get(j).setKey("");
                    if (cols2.get(j).getNull() == null)     cols2.get(j).setNull("");
                    if (cols2.get(j).getDefault() == null)     cols2.get(j).setDefault("");
                    if (cols2.get(j).getExtra() == null)     cols2.get(j).setExtra("");
                   
                    
                    //属性相同为0，不同为1
                    if (cols1.get(i).getType().equals(cols2.get(j).getType()))  result.setType("0");    else result.setType("1");
                    if (cols1.get(i).getNull().equals(cols2.get(j).getNull()))  result.setNull("0");    else result.setNull("1");
                    if (cols1.get(i).getKey().equals(cols2.get(j).getKey()))    result.setKey("0");     else result.setKey("1");
                    if (cols1.get(i).getDefault().equals(cols2.get(j).getDefault()))    result.setDefault("0");     else result.setDefault("1");
                    if (cols1.get(i).getExtra().equals(cols2.get(j).getExtra()))    result.setExtra("0");   else  result.setExtra("1");

                    results.add(result);
                }
            }
        }
        //返回
        //System.out.println(results.size());
        return results;
    }

    //数据库对象对比返回结果Map（通过两数据库名）
    public Map<String,List<Column>> getResultsMap(Map<String, List<Column>> dba1,Map<String, List<Column>> dba2,String dba_name1,String dba_name2){
/*
        Map dba1 = getDbaMap(dba_name1);
        Map dba2 = getDbaMap(dba_name2);*/
        Map<String, List<Column>> result = new HashMap<>();

        for (Object tname1:dba1.keySet()) {
            for (Object tname2:dba2.keySet()) {

                //表名相同才进行比较
                if (tname1.equals(tname2)){

                    List<Column> columns = getResults(dba_name1 + "." + tname1,dba_name2 + "." + tname2);

                    //加入对比结果Map
                    result.put(tname1.toString(), columns);
                }
            }
        }

        return result;
    }



    //===============
    public DataCompareService getService() {
        return service;
    }

    public void setService(DataCompareService service) {
        this.service = service;
    }


}
