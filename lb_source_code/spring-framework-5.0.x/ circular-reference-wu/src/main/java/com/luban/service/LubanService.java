package com.luban.service;

import org.springframework.stereotype.Component;

@Component
public class LuBanService {

	private  String hahaName ="haha";

	public LuBanService() {
		System.out.println("LubanService create ");
	}

	@Override
	public String toString() {
		return "LubanService{" +
				"hahaName='" + hahaName + '\'' +
				'}';
	}
}
