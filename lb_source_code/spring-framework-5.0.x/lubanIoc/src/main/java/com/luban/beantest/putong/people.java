package com.luban.beantest.putong;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component

public class people {
	private String name="普通人bean";
	private int age=9;


	@PostConstruct
	public void init(){
		System.out.println("-----------执行初始init方法------??为何没有执行？？？--------");
	}

}
