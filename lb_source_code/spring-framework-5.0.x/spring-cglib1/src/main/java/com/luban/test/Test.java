package com.luban.test;

import com.luban.app.Appconfig;

import com.luban.dao.UserDao;
import com.luban.service.IndexService;
import com.luban.service.Luban;
import com.luban.service.OrderService;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.cglib.core.SpringNamingPolicy;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
/**
 * 对一个对象的描述：
 * java -- calss --  Class对象
 * spring -- bean -- BeanDefinition接口的实现
 * */
public class  Test {
	public static void main(String[] args) {

//		new ClassPathXmlApplicationContext("");
		// 注解配置应用上下文--把spring所有的前提环境准备好
		AnnotationConfigApplicationContext ac
				= new AnnotationConfigApplicationContext(Appconfig.class);
		//Appconfig appconfig = ac.getBean(Appconfig.class);
		UserDao beanDao = ac.getBean(UserDao.class);
		beanDao.query();

		ac.getBean(IndexService.class);
//		LubanAppcofig lubanAppcofig = new LubanAppcofig();
//		lubanAppcofig.testProxy();
//		lubanAppcofig.testProxy();
new HashMap<>();
//
//		Enhancer enhancer = new Enhancer();
//		//增强父类，地球人都知道cglib是基于继承来的
//		enhancer.setSuperclass(LubanAppcofig.class);
//
//		//不继承Factory接口
//		enhancer.setUseFactory(false);
//		enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
//		// BeanFactoryAwareGeneratorStrategy是一个生成策略
//		// 主要为生成的CGLIB类中添加成员变量$$beanFactory
//		// 同时基于接口EnhancedConfiguration的父接口BeanFactoryAware中的setBeanFactory方法，
//		// 设置此变量的值为当前Context中的beanFactory,这样一来我们这个cglib代理的对象就有了beanFactory
//		//有了factory就能获得对象，而不用去通过方法获得对象了，因为通过方法获得对象不能控制器过程
//		//该BeanFactory的作用是在this调用时拦截该调用，并直接在beanFactory中获得目标bean
//		//enhancer.setStrategy(new ConfigurationClassEnhancer.BeanFactoryAwareGeneratorStrategy(classLoader));
//		//过滤方法，不能每次都去new
//		enhancer.setCallback(new LubanCallback());
//
//		LubanAppcofig chil = (LubanAppcofig) enhancer.create();
//		chil.testProxy();
//
//		chil.testProxy();


	}
}
