package com.luban.test;

import com.luban.app.Appconfig;
import com.luban.beantest.TestBeanFactoryPostProcessorWhereNoComponent;
import com.luban.dao.Dao;
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

//		AnnotationConfigApplicationContext oc = new AnnotationConfigApplicationContext(Appconfig.class);
		AnnotationConfigApplicationContext oc = new AnnotationConfigApplicationContext();
		oc.register(Appconfig.class);

		// 这样使用 TestBeanFactoryPostProcessorWhereNoComponent 都不会注册bd了的！！，而采用注解使用，是会产生bean即会注册bean,
//		oc.addBeanFactoryPostProcessor(new TestBeanFactoryPostProcessorWhereNoComponent());
		oc.refresh();

//		IndexDao bean0 = oc.getBean(IndexDao.class);//--已经是变为代理对象的了，class类对象不是IndexDao.class了而是@proxxx,但在工厂中的bdName 还是以前的indexDao
		Dao proxy =(Dao) oc.getBean("indexDao");//不能用xx来接收？只能用接口Dao来接收
		proxy.query();
//		IndexDao1 bean =(IndexDao1) oc.getBean("indexDao");//不能用xx来接收？？
//		IndexDao bean2 =(IndexDao) oc.getBean("indexDao");


//		System.out.println(bean);
		System.out.println();
//		bean.query4();

//		bean.getObject();

	}
}
