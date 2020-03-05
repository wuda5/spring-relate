package com.luban.test;

import com.luban.FactoryBean.FbTest;
import com.luban.FactoryBean.MyFactoryBean;
import com.luban.app.Appconfig;
import com.luban.app.AppconfigSimple;
import com.luban.cycle.IndexService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class TestFactoryBean {

	public static AnnotationConfigApplicationContext ioc ;

	public static void main(String[] args) {

		 ioc = new AnnotationConfigApplicationContext(AppconfigSimple.class);



		System.out.println("================================================================3");
		/** 1. getbean myFactoryBean 这个bdName 所对应的ioc容器所存在的bean 就算那个 MyfactoryBean ,但是
		 * 2. 当其应该是取出bean 时候，会有判断这个bean 是否 是实现了 接口 FactoryBean， 由于它是，则应不是直接拿此元bean的对象，
		 * 3. 而是会执行此 factorybean 中 的 getObject 对象，其返回的对象才是最终得到的**/
		FbTest bean1 = (FbTest)ioc.getBean("myFactoryBean");
		FbTest bean2 = ioc.getBean(FbTest.class);/**1.String[] candidateNames = getBeanNamesForType(requiredType); --allBeanNamesByType --》doGetBeanNamesForType*/
		FbTest bean3 = ioc.getBean(FbTest.class);/**有一个缓存 factoryBeanObjectCache 放置其实现返回的对象，避免多次来时是new*/
		System.out.println("equal --- :"+(bean1==bean2));


		MyFactoryBean bean = (MyFactoryBean)ioc.getBean("&myFactoryBean");
		/** 1.内部首先经过发展xx 通过 String beanName = transformedBeanName(name); 得到 其bdName ="&myFactoryBean"
		 * 而后流程和3同*/
		MyFactoryBean bean4 = (MyFactoryBean)ioc.getBean(MyFactoryBean.class);
		System.out.println("equal2 --- :"+(bean==bean4));



		FbTest beanError = (FbTest)ioc.getBean("&myFactoryBean");
	}











	public  static  void testRegister(){
		AnnotationConfigApplicationContext ioc = new AnnotationConfigApplicationContext();
		ioc.register(Appconfig.class);
        ioc.refresh();// 不加它会报错！！！
		IndexService index = ioc.getBean(IndexService.class);
		index.testxx();
	}
}
