package com.luban.dao;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

public class IndexDao1 {
	public IndexDao1(){

		System.out.println("dao1-init");
	}

	public void query(){
		System.out.println("index1");
	}

	@PostConstruct
	public void query4(){
		System.out.println("index1");
	}
}
