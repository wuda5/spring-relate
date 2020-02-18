package com.luban.test;

import com.luban.app.Appconfig;
import com.luban.auto.AutoServiceNoByResouce;
import com.luban.auto.AutoServiceNoByAutowired;
import com.luban.DI.ConstorService;
import com.luban.auto.autoModeTypeUpdate.AutoModeServiceUpdateToByConstor;
import com.luban.auto.autoModeTypeUpdate.AutoModeServiceUpdateToByName;
import com.luban.auto.autoModeTypeUpdate.AutoModeServiceUpdateToByType;
import com.luban.cycle.IndexService;
import com.luban.DI.SetterService;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StringUtils;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class Test {

	public static AnnotationConfigApplicationContext ioc ;
//	public static AnnotationConfigApplicationContext ioc = new AnnotationConfigApplicationContext(Appconfig.class);

	public static void main(String[] args) {
//		testRegister();
		 ioc = new AnnotationConfigApplicationContext(Appconfig.class);

//		// 测试循环引用
//		IndexService index = ioc.getBean(IndexService.class);
//		index.testxx();

		// 测试 setter 注入
		SetterService s = ioc.getBean(SetterService.class);
		s.test();
		getAutoMode("setterService","@autoWired");

		// 测试 构造器注入 ConstorService
		ConstorService c = ioc.getBean(ConstorService.class);
		c.testxx();
		getAutoMode("constorService","@autoWired");

		/**自动装配模型--no,byname, **/
		 ioc.getBean(AutoServiceNoByAutowired.class).test();
		getAutoMode("autoServiceNoByAutowired","@autoWired");

		ioc.getBean(AutoServiceNoByResouce.class).test();
		getAutoMode("autoServiceNoByResouce","@Resource");

		/** 修改指定类bean 的装配模式 */
		System.out.println("================================================================1");
		ioc.getBean(AutoModeServiceUpdateToByName.class).test();
		getAutoMode("autoModeServiceUpdateToByName","@Import 修改xxx-byName");

		System.out.println("================================================================2");
		ioc.getBean(AutoModeServiceUpdateToByType.class).test();
		getAutoMode("autoModeServiceUpdateToByType","@Import 修改xxx");

		System.out.println("================================================================3");
		ioc.getBean(AutoModeServiceUpdateToByConstor.class).test();
		getAutoMode("autoModeServiceUpdateToByConstor","@Import 修改xxx-byConstor");

	}
	public  static  void getAutoMode(String beanName,String ...x){
		GenericBeanDefinition userServiceGenericBeanDefinition = (GenericBeanDefinition)ioc.getBeanDefinition(beanName);
		String a=null;
		if (!StringUtils.isEmpty(x))
		{
			a=x[0];
		}
		System.out.println("***** 【"+beanName+"】自动注入-->【"+a
				+"】 所用模型：【"+userServiceGenericBeanDefinition.getAutowireMode()+"】");
	}










	public  static  void testRegister(){
		AnnotationConfigApplicationContext ioc = new AnnotationConfigApplicationContext();
		ioc.register(Appconfig.class);
        ioc.refresh();// 不加它会报错！！！
		IndexService index = ioc.getBean(IndexService.class);
		index.testxx();
	}
}
