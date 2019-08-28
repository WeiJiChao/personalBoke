package com.jwBlog.base.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 对应application.properties配置并不能完美启动注入，我们需要写一个数据源的配置类来启动
 */
@Configuration
@ConfigurationProperties(prefix="spring.datasource.db") //获取properties文件中的前缀属性字段
public class ApplicationConfig {
    private String url;
    private String username;
    private String password;

    /**
     * @Bean 会把这个 return的dataSource数据源对象告诉Spring 去读取使用
     * @return
     */
    @Bean
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);// 用户名
        dataSource.setPassword(password);// 密码
        return dataSource;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
