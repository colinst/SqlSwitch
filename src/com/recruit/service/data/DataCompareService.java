package com.recruit.service.data;

import com.recruit.dao.data.ColumnMapper;
import com.recruit.model.Column;
import com.recruit.service.data.impl.IDataCompareService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 郭宏禧
 * @createTime 2017/8/19
 * @context **
 */
@Service
public class DataCompareService implements IDataCompareService {

    @Resource
    private ColumnMapper columnMapper;


    //通过表名获得表list
    public List<Column> getTableByName(String table_name) {
        return columnMapper.getTableByName(table_name);
    }

    //通过数据库名获得所有表名
    public List<String> getTableNames(String dba_name){
        return columnMapper.getTableNames(dba_name);
    };

    //获得当前数据库实例中所有数据库名
    public List<String> getDbaNames(){
        return columnMapper.getDbaNames();
    }

    //Mapper调用
    public ColumnMapper getColumnMapper() {
        return columnMapper;
    }

    public void setColumnMapper(ColumnMapper columnMapper) {
        this.columnMapper = columnMapper;
    }
}
