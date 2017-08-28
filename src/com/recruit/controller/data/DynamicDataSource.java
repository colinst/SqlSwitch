package com.recruit.controller.data;

/**
 * @author 郭宏禧
 * @createTime 2017/8/23
 * @context **
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class DynamicDataSource extends AbstractRoutingDataSource {

    private Logger log = Logger.getLogger(this.getClass());
    private Map targetDataSource = new HashMap();


    //数据源为空或者为0时，自动切换至默认数据源
    @Override
    protected Object determineCurrentLookupKey() {
        // TODO Auto-generated method stub
        String dataSourceName = Dbs.getDbType();
        if (dataSourceName == null) {
            // 默认的数据源名字
            dataSourceName = "dataSource";
        } else {
            this.selectDataSource(dataSourceName);
            if (dataSourceName.equals("0")) {
                dataSourceName = "dataSource";
            }
        }
        log.debug("use datasource : " + dataSourceName);
        return dataSourceName;
    }

    public void setTargetDataSource(Map targetDataSource) {
        this.targetDataSource = targetDataSource;
        super.setTargetDataSources(this.targetDataSource);
    }

    public Map getTargetDataSource(){
        return this.targetDataSource;
    }

    public void addTargetDataSource(String key, BasicDataSource dataSource) {
        this.targetDataSource.put(key, dataSource);
        setTargetDataSources(this.targetDataSource);
    }

    //===================
    //===================
    public BasicDataSource createDataSource(String url) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername("root");
        dataSource.setPassword("admin");
        dataSource.setTestWhileIdle(true);

        return dataSource;
    }

    //数据源存在时不做处理，不存在时创建新的数据源链接
    public BasicDataSource createDataSource(String driverClassName, String url,
                                            String username, String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setTestWhileIdle(true);

        return dataSource;
    }



    public void selectDataSource(String dbType) {
        Object sid = Dbs.getDbType();
        if ("0".equals(dbType)) {
            Dbs.setDbType("0");
            return;
        }
        Object obj = this.targetDataSource.get(dbType);
        if (null != obj && sid.equals(dbType)) {
            return;
        } else {
            BasicDataSource dataSource = this.getDataSource(dbType);
            if(null != dataSource){
                this.setDataSource(dbType, dataSource);
            }
        }
    }

    public BasicDataSource getDataSource(String dbtype) {
        this.selectDataSource("0");
        this.determineCurrentLookupKey();
        Connection conn = null;
        HashMap map = null;

        try {
            conn = this.getConnection();
            PreparedStatement ps = conn
                    .prepareStatement("SELECT * FROM dbs WHERE active = 'y' and type = ?");
            ps.setString(1, dbtype);
            ResultSet rs = ps.executeQuery();
            map = new HashMap();

            if (rs.next()) {
                map.put("active", rs.getString("active"));
                map.put("db_name", rs.getString("db_name"));
                map.put("ip", rs.getString("ip"));
                map.put("port", rs.getInt("port"));
                map.put("type", rs.getString("type"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            log.error(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                log.error(e);
            }
        }
        if (null != map) {
            String url = "jdbc:mysql://" + map.get("ip") + ":"
                    + map.get("port") + "/" + map.get("db_name")
                    + "?useUnicode=true&characterEncoding=utf-8";
            BasicDataSource dataSource = createDataSource(url);
            return dataSource;
        }
        return null;
    }

    public void setDataSource(String type, BasicDataSource dataSource){
        this.addTargetDataSource(type, dataSource);
        Dbs.setDbType(type);
    }

    @Override
    public void setTargetDataSources(Map targetDataSources) {
        // TODO Auto-generated method stub
        super.setTargetDataSources(targetDataSources);
        //重点：通知container容器数据源发生了变化
        super.afterPropertiesSet();
    }

    //controller中用Dbs.setDbType("databasename")
}

