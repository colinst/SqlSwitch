package com.recruit.controller.data;

/**
 * @author 郭宏禧
 * @createTime 2017/8/23
 * @context **
 */
public final class Dbs {

    //用户切换数据源只要在程序中使用 Dbs.setDbType()
    private static final ThreadLocal local = new ThreadLocal();

    public static String getDbType(){
        return (String) local.get();
    }

    public static void setDbType(String dbName){
        local.set(dbName);
    }

    public static void clear(){
        local.remove();
    }
}
