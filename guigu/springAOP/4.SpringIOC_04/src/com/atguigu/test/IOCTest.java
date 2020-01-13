package com.atguigu.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.atguigu.service.BookService;
import com.atguigu.service.UserService;

public class IOCTest {
	
	

	@Test
	public void test() {
		
		ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
		BookService bookService = ioc.getBean(BookService.class);
		UserService userService = ioc.getBean(UserService.class);
		
		bookService.save();
		userService.save();
		
		
		//父类的类型：com.atguigu.service.BaseService
		//带泛型的父类类型：com.atguigu.service.BaseService<com.atguigu.bean.Book>
		//Spring中可以使用带泛型的父类类型来确定这个子类的类型
		System.out.println(bookService.getClass().getGenericSuperclass());
		
		//ioc是一个容器，帮我们管理所有的组件；
		//1、依赖注入；@Autowired；自动赋值
		//2、某个组件要使用Spring提供的更多（IOC、AOP）必须加入到容器中；
		//体会：
		//1、容器启动。创建所有单实例bean
		//2、autowired自动装配的时候，是从容器中找这些符合要求的bean
		//3、ioc.getBean("bookServlet")；也是从容器中找到这个bean；
		//4、容器中包括了所有的bean；
		//5、调试spring的源码，容器到底是什么？其实就是一个map；
		//6、这个map中保存所有创建好的bean，并提供外界获取功能...
		//7、探索，单实例的bean都保存到哪个map中了。【源码-扩展】
		//8、源码调试的思路；
		//	从helloworld开始的；给helloworld每一个关键步骤打上断点。进去看里面都做了什么工作？
		//		怎么知道哪些方法都是干什么的
		//    1、翻译这个方法是干什么？
		//	  2、放行这个方法，看控制台,看debug的每一个变量的变化
		//    3、看方法注释；
		//  学到的一点：1）、规范注释，   2）、规范方法名和类名;
	}
}
