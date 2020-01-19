package com.luban.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

//@Component
public class IndexDao1 {
	public IndexDao1(){

		System.out.println("dao1-init");
		Log log = LogFactory.getLog("jcl");
		log.info("xxxx --- jcl ---打印白色---");
	}

	public void query(){
		System.out.println("index1");
	}

	@PostConstruct
	public void query4(){
		System.out.println("index1");
	}
}
