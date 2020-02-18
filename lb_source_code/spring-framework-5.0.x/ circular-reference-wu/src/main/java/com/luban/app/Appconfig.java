package com.luban.app;

import com.luban.auto.autoModeTypeUpdate.MyImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

//import com.luban.imports.MyImportSelector;

@ComponentScan({"com.luban"})
@Configuration
@Import(MyImportBeanDefinitionRegistrar.class)
// proxy-target-class="true" -- 代表使用 cglib 代理，
// 默认采用的是jdk 代理，但也只会代理是接口的，普通的类有内部判断非接口时会设置 proxyFactory.setProxyTargetClass(true);后使用 cglib代理！！
//@EnableAspectJAutoProxy(proxyTargetClass = true)
public class Appconfig {
	public Appconfig() {

		System.out.println("+++++++ [4.] Appconfig 特殊的配置类@Configuration（用包扫描）的构造器 ++++++");
	}


	//	@Bean
//	public IndexDao1 indexDao1(){
//
//		return new IndexDao1();
//	}
//
//	@Bean
//	public IndexDao indexDao(){
//		indexDao1();
//		indexDao1();
//		return new IndexDao();
//	}
}
