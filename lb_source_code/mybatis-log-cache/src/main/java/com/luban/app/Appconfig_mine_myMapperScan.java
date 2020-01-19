package com.luban.app;

import com.luban.ann.MineMapperScan;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.logging.Logger;

@ComponentScan(basePackages = "com.luban",excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {Appconfig.class})})
@Configuration
//@MapperScan("com.luban")
@MineMapperScan("com.luban.dao")
public class Appconfig_mine_myMapperScan {
    public Appconfig_mine_myMapperScan() {

        System.out.println("+++++++ [4.] Appconfig 特殊的配置类@Configuration（用包扫描）的构造器 ++++++");

        Logger.getLogger("").info("test----log");
    }

//    @Bean
//    public SqlSessionFactoryBean sqlSessionFactory(){
//        SqlSessionFactoryBean sb =  new SqlSessionFactoryBean();
//        sb.setDataSource(dataSource());// --小心
////        sb.setDataSource(dataSource());// --小心
//        return  sb;
//    }
//
//    @Bean
//    public DataSource dataSource() {
//
//        DriverManagerDataSource dm = new DriverManagerDataSource();
//        dm.setPassword("root");
//        dm.setUsername("root");
//
//        dm.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8");
//        dm.setDriverClassName("com.mysql.jdbc.Driver");
//
//        // 表是 job_info
//        return dm;
//    }
}
