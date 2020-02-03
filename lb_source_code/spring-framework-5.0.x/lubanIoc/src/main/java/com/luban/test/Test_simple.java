package com.luban.test;

import com.luban.app.Appconfig;
import com.luban.beantest.TestBeanDefinitionRegistryPostProcessorWhereNoComponent;
import com.luban.beantest.TestBeanFactoryPostProcessorWhereNoComponent;
import com.luban.dao.Dao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class Test_simple {
    public static void main(String[] args) {

		AnnotationConfigApplicationContext oc = new AnnotationConfigApplicationContext(Appconfig.class);
//		oc.register(Appconfig.class);
//		oc.refresh();



	}
}
