package com.recruit.controller.data;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author williams
 * @describe 实现动态数据源切换逻辑
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private Logger log = Logger.getLogger(this.getClass());
    private Map<Object, Object> _targetDataSources = new HashMap<>();

    /**
     * @describe 数据源为空或者为ds2时，自动切换至默认数据源，即在配置文件中定义的dataSource数据源
     * @see AbstractRoutingDataSource#determineCurrentLookupKey()
     */
    @Override
    protected Object determineCurrentLookupKey() {
        String dataSourceName = DbContextHolder.getDbType();
        if (dataSourceName == null) {
            dataSourceName = "dataSource";
        } else {
            this.selectDataSource(dataSourceName);
            if (dataSourceName.equals("ds2"))
                dataSourceName = "dataSource";
        }
        log.debug("--------> use datasource " + dataSourceName);
        return dataSourceName;
    }

    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        this._targetDataSources = targetDataSources;
        super.setTargetDataSources(this._targetDataSources);
        afterPropertiesSet();
    }

    public void addTargetDataSource(String key, BasicDataSource dataSource) {
        this._targetDataSources.put(key, dataSource);
        this.setTargetDataSources(this._targetDataSources);
    }

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

    /**
     * @param serverId
     * @describe 数据源存在时不做处理，不存在时创建新的数据源链接，并将新数据链接添加至缓存
     */
    public void selectDataSource(String javaname) {
        Object sid = DbContextHolder.getDbType();
        if ("ds2".equals(javaname + "")) {
            DbContextHolder.setDbType("ds2");
            return;
        }
        Object obj = this._targetDataSources.get(javaname);
        if (obj != null && sid.equals(javaname + "")) {
            return;
        } else {
            BasicDataSource dataSource = this.getDataSource(javaname);
            if (null != dataSource)
                this.setDataSource(javaname, dataSource);
        }
    }

    /**
     * @param serverId
     * @return
     * @describe 查询serverId对应的数据源记录
     */
    public BasicDataSource getDataSource(String javaname) {
        this.selectDataSource("ds2");
        this.determineCurrentLookupKey();
        Connection conn = null;
        HashMap<String, Object> map = null;
        try {
            conn = this.getConnection();
            PreparedStatement ps = conn
                    .prepareStatement("SELECT * FROM dbsource WHERE javaname = ?");
            ps.setString(1, javaname);
            ResultSet rs = ps.executeQuery();
            map = new HashMap<String, Object>();
            if (rs.next()) {
                map.put("DBS_ID", rs.getInt("id"));
                map.put("DBS_DriverClassName", rs
                        .getString("driverclass"));
                map.put("DBS_URL", rs.getString("url"));
                map.put("DBS_UserName", rs.getString("username"));
                map.put("DBS_Password", rs.getString("password"));
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
            String driverClassName = map.get("DBS_DriverClassName").toString();
            String url = map.get("DBS_URL").toString();
            String userName = map.get("DBS_UserName").toString();
            String password = map.get("DBS_Password").toString();
            BasicDataSource dataSource = this.createDataSource(driverClassName,
                    url, userName, password);
            return dataSource;
        }
        return null;
    }

    /**
     * @param serverId
     * @param dataSource
     */
    public void setDataSource(String javaname, BasicDataSource dataSource) {
        this.addTargetDataSource(javaname + "", dataSource);
        DbContextHolder.setDbType(javaname + "");
    }

}