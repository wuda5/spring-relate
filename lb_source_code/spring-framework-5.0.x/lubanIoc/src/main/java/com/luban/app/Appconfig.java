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
