package com.luban.byResouce;

import org.springframework.stereotype.Component;

@Component("daoResouce2")
public class DaoResouce2 implements  DaoResouceInterface {

	@Override
	public String toString() {
		return "Luban{" +
				"name='" + name + '\'' +
				'}';
	}

	private  String name ="DaoResouce2";

}
