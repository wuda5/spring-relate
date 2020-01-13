package com.luban.test;

import com.luban.app.Appconfig;
import com.luban.dao.IndexDao;
import com.luban.dao.IndexDao1;
import com.luban.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class Test {
    public static void main(String[] args) {
//        BeanFactory beanFactory = new BeanFactory("spring.xml");
//
//        UserService service = (UserService) beanFactory.getBean("service");
//
//        service.find();

		AnnotationConfigApplicationContext oc = new AnnotationConfigApplicationContext(Appconfig.class);

		IndexDao bean0 = oc.getBean(IndexDao.class);
		IndexDao1 bean =(IndexDao1) oc.getBean("indexDao");
//		IndexDao bean2 =(IndexDao) oc.getBean("indexDao");
//		System.out.println(bean);
		System.out.println();
		bean.query4();

//		bean.getObject();

	}
}
