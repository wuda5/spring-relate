package com.luban.test;

import com.luban.app.Appconfig;
import com.luban.byResouce.TestResouceService;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class Test_ByResouceJdk8 {

	static final Logger log = Logger.getLogger(Test_ByResouceJdk8.class);
    public static void main(String[] args) {

//		ImportSelector importSelector = BeanUtils.instantiateClass(SpringBean.class, ImportSelector.class);

		AnnotationConfigApplicationContext ioc = new AnnotationConfigApplicationContext(Appconfig.class);


		TestResouceService b = ioc.getBean(TestResouceService.class);
		b.testxx();

	}
}
