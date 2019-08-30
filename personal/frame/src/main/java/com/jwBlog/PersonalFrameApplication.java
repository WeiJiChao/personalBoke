package com.jwBlog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//禁用系统自动启用自动配置的额数据源 - 因为我们要自己定义数据源 所以要禁用
//@SpringBootApplication
@MapperScan("com.jwBlog.*.dao")//，扫描MyBatis的Mapper于dao包下。
//@ComponentScan({"smm.springboot_ftl.service"}) ：标识业务层的类，用来找到业务层对象，smm.springboot_ftl.service是业务类的路径
@EnableScheduling  //启用Spring Schedule定时任务
public class PersonalFrameApplication {
//public class PersonalFrameApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(PersonalFrameApplication.class, args);
        System.out.println("启动成功");
    }
//    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 注意这里要指向原先用main方法执行的Application启动类
        return builder.sources(PersonalFrameApplication.class);
    }
}
