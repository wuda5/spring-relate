package com.atguigu.test;

import static org.junit.Assert.*;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.bean.Car;
import com.atguigu.bean.Person;

public class IOCTest {
	
	ConfigurableApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
//	ConfigurableApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext03.xml");

	/**
	 * 测试基于xml的自动装配；
	 * 
	 */
	@Test
	public void test03(){
		Person person = (Person) ioc.getBean("person04");
		System.out.println(person);
	}
	
	@Test
	public void test02() throws SQLException{
		//1、从容器中拿到连接池
		//DataSource bean = (DataSource) ioc.getBean("dataSource");
		
		//2、按照类型获取组件，可以获取到这个类型下的所有实现类子类等等...
		DataSource bean = ioc.getBean(DataSource.class);
		
		System.out.println(bean.getConnection());
		
		Car bean2 = ioc.getBean(Car.class);
		System.out.println(bean2);
	}

	/**
	 *单例： Bean的生命周期
	 * 		（容器启动）构造器---->初始化方法---->(容器关闭)销毁方法
	 *多实例：
	 *		获取bean（构造器--->初始化方法）--->容器关闭不会调用bean的销毁方法
	 *
	 *后置处理器：
	 *		（容器启动）构造器------后置处理器before..-----初始化方法-----后置处理器after...-----bean初始化完成
	 *
	 *无论bean是否有初始化方法；后置处理器都会默认其有，还会继续工作；
	 */
	@Test
	public void test() {
		//Object bean = ioc.getBean("book01");
		//System.out.println(""+bean);
		//ioc.close();
	}

}
