package com.luban.test;

import com.luban.app.Appconfig_mine_myMapperScan;
import com.luban.dao.CityMapper;
import com.luban.dao.CityMapper2;
import com.luban.service.IndexService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test_mine_myMapperScan {
    public static void main(String[] args) {
		AnnotationConfigApplicationContext ioc = new AnnotationConfigApplicationContext(Appconfig_mine_myMapperScan.class);

		System.out.println("");
		CityMapper b = ioc.getBean(CityMapper.class);
		b.list();
		CityMapper2 b2 = ioc.getBean(CityMapper2.class);
		b2.list2();

		System.out.println("----------------");
		IndexService i = ioc.getBean(IndexService.class);
		i.list();

	}
}
