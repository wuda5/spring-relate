package com.atguigu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.dao.BaseDao;


public class BaseService<T> {
	@Autowired
	private BaseDao<T> baseDao;
	
	public void save(){
		System.out.println("自动注入的dao："+baseDao);
		baseDao.save();
	}

}
