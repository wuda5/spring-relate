package com.luban.test;

import com.luban.app.Appconfig;
import com.luban.app.Appconfig_mine_myMapperScan;

import com.luban.service.IndexService;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class Test_mybatis_withSpring {

	static final Logger log = Logger.getLogger(Test_mybatis_withSpring.class);
    public static void main(String[] args) {

//		ImportSelector importSelector = BeanUtils.instantiateClass(SpringBean.class, ImportSelector.class);

		AnnotationConfigApplicationContext ioc = new AnnotationConfigApplicationContext(Appconfig.class);


		IndexService b = ioc.getBean(IndexService.class);
		b.list();

		List<Map<String, Object>> list = b.list();
		System.out.println(list);

		log.info("xxxx ---进行第二次查询--观察是否有缓存sql--应该是没有会打印sql的----- 日志级别设为 debug---");
		b.list();/***/
	}
}
