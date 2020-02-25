package com.luban.app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

//import com.luban.imports.MyImportSelector;

@ComponentScan(basePackages = "com.luban.FactoryBean",excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {Appconfig.class})})
@Configuration
//@Import({MyImportBeanDefinitionRegistrar.class, MyImportSelectorTest.class})
//@ImportResource("classpath:spring.xml")
// proxy-target-class="true" -- 代表使用 cglib 代理，
// 默认采用的是jdk 代理，但也只会代理是接口的，普通的类有内部判断非接口时会设置 proxyFactory.setProxyTargetClass(true);后使用 cglib代理！！
//@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AppconfigSimple {
	public AppconfigSimple() {

		System.out.println("+++++++ [4.] AppconfigSimple 特殊的配置类@Configuration（用包扫描）的构造器 ++++++");
	}


}
