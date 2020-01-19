package com.luban.test;

import com.luban.FactoryBean.SpringBean;
import com.luban.app.Appconfig_mine_myMapperScan;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class testFactoryBean {
    static final Logger log = Logger.getLogger(Test_mybatis_withSpring.class);
    public static void main(String[] args) {

        AnnotationConfigApplicationContext ioc = new AnnotationConfigApplicationContext(Appconfig_mine_myMapperScan.class);


//        SpringBean b = (SpringBean)ioc.getBean("&luban");
        SpringBean b = (SpringBean)ioc.getBean("luban");

    }
}
