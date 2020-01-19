package com.luban.app;

import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;

@ComponentScan(basePackages = "com.luban" ,excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {Appconfig_mine_myMapperScan.class})})
@Configuration
@MapperScan("com.luban.dao")
//@MineMapperScan("com.luban.dao")
public class Appconfig {
    public Appconfig() {
        System.out.println("+++++++ [4.] Appconfig 特殊的配置类@Configuration（用包扫描）的构造器 ++++++");
    }
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(){
        SqlSessionFactoryBean sb =  new SqlSessionFactoryBean();
        sb.setDataSource(dataSource());// --小心

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setLogImpl(Log4jImpl.class);

        sb.setConfiguration(configuration);

        return  sb;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dm = new DriverManagerDataSource();
        dm.setPassword("root");
        dm.setUsername("root");
        dm.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8");
        dm.setDriverClassName("com.mysql.jdbc.Driver");
        // 表是 job_info
        return dm;
    }
}
