package com.luban.test;

import app.Appconfig_proxyTargetClasss_true;
import com.luban.dao.TimeDaoInterface;
import com.luban.dao.impl.TimeDaoImplOne;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/***
 *  spring-aop 的接口的 jdk 代理到底该如何测试？？？
 *
 * --我在网上看到的： 如果要被代理的对象是个实现类，那么Spring会使用JDK动态代理来完成操作（Spirng默认采用JDK动态代理实现机制, 但要是再appconfig 上面强制设置了代理是cglib就不一样）；
 *
 * --- 如果要被代理的对象不是个实现类那么，Spring会强制使用CGLib来实现动态代理。
 * */
public class testInterfaceProxy_cglib {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ioc = new AnnotationConfigApplicationContext(Appconfig_proxyTargetClasss_true.class);

		TimeDaoInterface time = ioc.getBean(TimeDaoImplOne.class);// cglib就可以直接这样获取！！！
		System.out.println("+++++ 采用 cglib代理时 获取的bean（proxy） 是否和 目标对象 类型同？:【 "+	(time instanceof TimeDaoImplOne) +"】bean 代理对象：【"+time.getClass() );
//		TimeDaoInterface time = (TimeDaoInterface)ioc.getBean("timeDaoImplOne");// 如果我在appconfig中配置@EnableAspectJAutoProxy(proxyTargetClass = false) --》那么默认接口jdk代理就会是：com.sun.proxy.$Proxy22

		time.timeDaoYes(1, 9);
		System.out.println("======================1=====================");

//		TimeDaoInterface two = ioc.getBean(TimeDaoImplTwo.class);//如果是jdk代理化，这样写肯定是错的了（参考自己写的模拟mabits的 LubanRegistart，只是我那特殊点），因为会在xx过程改变此bd对应的 beanClass 为 com.sun.proxy.$Proxy22， 即只能用接口所生产的代理类父接口作为beanType 去查询
//		two.timeDaoYes(2, 9);
		System.out.println("===========================================");

		/** 【 NoUniqueBeanDefinitionException】:
		 * No qualifying bean of type 'com.luban.dao.TimeDaoInterface' available: expected single matching bean but found 2: timeDaoImplOne,timeDaoImplTwo*/
		TimeDaoInterface x = ioc.getBean(TimeDaoInterface.class);// 但是我如果在 某一个如：TimeDaoImplTwo 类上加上注解 @Primary-- 那么就会以他为先来返回！！
		x.timeDaoYes(3,5);
		ioc.close();

	}
}
