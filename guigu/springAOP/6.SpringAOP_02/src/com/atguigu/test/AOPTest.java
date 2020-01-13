package com.atguigu.test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.impl.MyMathCalculator;
import com.atguigu.inter.Calculator;

public class AOPTest {
	
	ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	
	
	
	/**
	 * 通知方法的执行顺序；
	 * 
	 *   try{
	 *   	@Before
	 *   	method.invoke(obj,args);
	 *   	@AfterReturning
	 *   }catch(){
	 *   	@AfterThrowing
	 *   }finally{
	 *   	@After
	 *   }
	 * 
	 * 正常执行：  @Before（前置通知）=====@After（后置通知）====@AfterReturning（正常返回）；
	 * 异常执行： @Before（前置通知）=====@After（后置通知）===@AfterThrowing（方法异常）
	 * 
	 */
	@Test
	public void test03(){
		MyMathCalculator bean = ioc.getBean(MyMathCalculator.class);
		
		
		bean.add(1, 1);
		System.out.println("=========");
		bean.div(1, 0);
	}
	
	@Test
	public void test02(){
		MyMathCalculator bean = ioc.getBean(MyMathCalculator.class);
		bean.add(1, 0.2);
		
	}

	/**
	 commons-logging-1.1.3.jar
spring-aop-4.0.0.RELEASE.jar
spring-beans-4.0.0.RELEASE.jar
spring-context-4.0.0.RELEASE.jar
spring-core-4.0.0.RELEASE.jar
spring-expression-4.0.0.RELEASE.jar
spring-aspects-4.0.0.RELEASE.jar：基础版

加强版的面向切面编程（即使目标对象没有实现任何接口也能创建动态代理）
com.springsource.net.sf.cglib-2.2.0.jar
com.springsource.org.aopalliance-1.0.0.jar
com.springsource.org.aspectj.weaver-1.6.8.RELEASE.jar

	 * 
	 */
	@Test
	public void test() {
		
//		MyMathCalculator myMathCalculator = new MyMathCalculator();
//		myMathCalculator.add(2, 12);
		
		//1、从ioc容器中拿到目标对象；注意：如果想要用类型，一定用 他的接口类型，不要用它本类
		//细节一：com.atguigu.impl.MyMathCalculator@6c64cb25
		//class com.sun.proxy.$Proxy12
		
		
		//AOP的底层就是动态代理，容器中保存的组件是他的代理对象；$Proxy12。当然不是本类的类型
		
		//Calculator bean = ioc.getBean(Calculator.class);
		//bean.add(2, 1);
		//System.out.println(bean);
		//System.out.println(bean.getClass());
		
		
		
		//Calculator bean2 = (Calculator) ioc.getBean("myMathCalculator");
		//System.out.println(bean2.getClass());
		
		//没有接口就是本类类型
		//class com.atguigu.impl.MyMathCalculator$$EnhancerByCGLIB$$fe279f42
		//cglib帮我们创建好的代理对象
		MyMathCalculator bean = (MyMathCalculator) ioc.getBean("myMathCalculator");
		bean.add(1, 2);
		System.out.println(bean.getClass());
		
		
	}

}
