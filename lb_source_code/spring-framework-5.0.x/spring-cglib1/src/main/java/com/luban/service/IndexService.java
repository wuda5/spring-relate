package com.luban.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("index")
public class IndexService {

	@Autowired
	Luban luban;

	public IndexService() {
		System.out.println("+++++++++v 构造器++++ indexservice +++");
	}
}
