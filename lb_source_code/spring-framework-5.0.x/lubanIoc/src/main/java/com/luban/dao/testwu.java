package com.luban.dao;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
public class testwu {
	public testwu(){

		System.out.println("wu-init");
	}

	public void query(){
		System.out.println("wu");
	}

	@PostConstruct
	public void query4(){
		System.out.println("wu");
	}
}
