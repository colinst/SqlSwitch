package com.recruit.dao.data;

import com.recruit.model.Column;

import java.util.List;

/**
 * @author 郭宏禧
 * @createTime 2017/8/19
 * @context **
 */
public interface ColumnMapper {

    //通过表名获得表list
    public List<Column> getTableByName(String table_name);

    //通过数据库名获得所有表名
    public List<String> getTableNames(String dba_name);

    //获得当前数据库实例中所有数据库名
    public List<String> getDbaNames();

}
