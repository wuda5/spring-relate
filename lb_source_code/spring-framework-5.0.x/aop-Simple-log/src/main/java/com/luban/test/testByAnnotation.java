package com.luban.test;

import app.Appconfig;
import com.luban.service.TimeService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/***
 * 5】、编写单元测试类进行测试 IOCTest_IOC.java
 * */
public class testByAnnotation {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ioc = new AnnotationConfigApplicationContext(Appconfig.class);

		TimeService time = ioc.getBean(TimeService.class);

		/** 测试运行结果--次测试1除以0的异常情况：可见在div运行之前，运行之后，以及出现异常时都打印出相关的信息。
		 其他情况，大家可以自行测试
		 aop的效果就是这样，在div运行前后的各个位置进行了处理，把日志的代码与业务逻辑的代码进行--解耦 **/
		time.timeYes(9, 9);
		System.out.println("===========================================");
		time.timeNo(1, 9);

		ioc.close();

	}
}
