package com.luban.app;

import com.luban.anno.EanbleLuabn;
import com.luban.dao.Dao;
import com.luban.dao.IndexDao;
import com.luban.dao.IndexDao1;
//import com.luban.imports.MyImportSelector;
import com.luban.imports.MyImportSelector;
import org.springframework.context.annotation.*;
@ComponentScan({"com.luban"})
@Configuration
// proxy-target-class="true" -- 代表使用 cglib 代理，
// 默认采用的是jdk 代理，但也只会代理是接口的，普通的类有内部判断非接口时会设置 proxyFactory.setProxyTargetClass(true);后使用 cglib代理！！
@EnableAspectJAutoProxy(proxyTargetClass = true)
/** 加了他，会利用import 注册一个aop 的后置处理器 AnnotationAutoProxyCreator，
 * 再完成所有的注册xx后，在注册后置处理器过程完成此 后置处理器的生命，后
 * 在对其他所有的bean 3类，的实例化生命时，都会作用调用到此后置处理器的方法 afterInitxxx **/
//@Import({MyImportSelector.class})
@EanbleLuabn(value= true) // value= false 就不会动态代理xxx
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
