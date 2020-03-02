package app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 1. 采用默认  @EnableAspectJAutoProxy(proxyTargetClass = false)
 * 即 jdk 代理，只是遇到非接口的代理也是 cglib;
 *
 * 4】、将切面类和业务逻辑类加入到容器中 MainConfigOfAOP.java
 * 告诉spring哪个是切面类，(在切面类LogAspects加一个注解@Aspect)
 * 在配置类中加 @EnableAspectJAutoProxy 开启基于注解的aop模式
 * */
@ComponentScan("com.luban")
@Configuration
//proxyTargetClass = false  默认采用的是jdk 代理，--> 此时
// 但也只会代理是接口的，普通的类有内部判断非接口时会设置 proxyFactory.setProxyTargetClass(true);后使用 cglib代理！！
@EnableAspectJAutoProxy(proxyTargetClass = false)
public class Appconfig {
	public Appconfig() {

		System.out.println("+++++++ [4.] Appconfig 特殊的配置类@Configuration（用包扫描）的构造器 ++++++");
	}

//	/*** 或者 用@compnent 管理 bean */
//	@Bean
//	public MathCalculator calculator() {
//		return new MathCalculator();
//	}
//	@Bean
//	public LogAspects logAspects() {
//		return new LogAspects();
//	}



	//	@Bean
//	public IndexDao1 indexDao1(){
//
//		return new IndexDao1();
//	}

}
