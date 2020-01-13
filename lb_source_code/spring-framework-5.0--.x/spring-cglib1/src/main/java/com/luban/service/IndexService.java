package com.luban.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("index")
public class IndexService {

	@Autowired
	Luban luban;

	public IndexService() {
		System.out.println("--wu test -- 是否 可以 生效--- ，构造器中++++  ");
	}
}
