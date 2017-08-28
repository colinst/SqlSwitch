package com.recruit.controller.data;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangrongtao on 15/6/2.
 */
@Service
public class RegisterDataSource implements ApplicationListener<ContextRefreshedEvent> {
    private final String DATA_SOURCE_BEAN_ID = "dataSource3";
    private final String DATA_SOURCE_DRIVER = "com.mysql.jdbc.Driver";
    private final String DATA_SOURCE_URL = "jdbc:mysql://localhost:3306/passport";
    private final String DATA_SOURCE_USER_NAME = "root";
    private final String DATA_SOURCE_USER_PASSWORD = "admin";

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try {
            DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) contextRefreshedEvent.getApplicationContext().getAutowireCapableBeanFactory();
            AbstractRoutingDataSource dataSource = (AbstractRoutingDataSource) beanFactory.getBean("dataSource");
            System.out.println(dataSource.toString());
            if (beanFactory.containsBean(DATA_SOURCE_BEAN_ID)) {
                BasicDataSource obj = (BasicDataSource) beanFactory.getBean(DATA_SOURCE_BEAN_ID);
                obj.setUrl(DATA_SOURCE_URL);
                obj.setUsername(DATA_SOURCE_USER_NAME);
                obj.setPassword(DATA_SOURCE_USER_PASSWORD);
            } else {
                Map map = new HashMap();
                BeanDefinitionBuilder userBeanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(BasicDataSource.class);
                userBeanDefinitionBuilder.addPropertyValue("driverClassName", DATA_SOURCE_DRIVER);
                userBeanDefinitionBuilder.addPropertyValue("url", DATA_SOURCE_URL);
                userBeanDefinitionBuilder.addPropertyValue("username", DATA_SOURCE_USER_NAME);
                userBeanDefinitionBuilder.addPropertyValue("password", DATA_SOURCE_USER_PASSWORD);
                beanFactory.registerBeanDefinition(DATA_SOURCE_BEAN_ID, userBeanDefinitionBuilder.getBeanDefinition());
                map.put(DATA_SOURCE_BEAN_ID, beanFactory.getBean(DATA_SOURCE_BEAN_ID));
                //取得bean，并修改属性
                //map.put("defaultDataSource", beanFactory.getBean("defaultDataSource"));
                dataSource.setTargetDataSources(map);
                //修改完bean属性，需要执行afterPropertiesSet生效
            }
            dataSource.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
