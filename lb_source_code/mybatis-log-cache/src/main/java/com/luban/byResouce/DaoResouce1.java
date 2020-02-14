package com.luban.byResouce;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("daoResouce1")
public class DaoResouce1 implements  DaoResouceInterface {

	@Override
	public String toString() {
		return "Luban{" +
				"name='" + name + '\'' +
				'}';
	}

	private  String name ="DaoResouce1";

}
