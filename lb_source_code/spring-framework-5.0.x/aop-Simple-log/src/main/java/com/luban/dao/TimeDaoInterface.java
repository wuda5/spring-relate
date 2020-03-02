package com.luban.dao;

//接口上使用@component注解是无意义的，原因想想也很简单的，接口是没有构造方法的，那这个bean就不可能被创建了
//@Component
public interface TimeDaoInterface {


	/**
	 * 除法 -- 自定义的注解 @TimeAccord,
	 */
//	@TimeAccord
	 int timeDaoYes(int i, int j) ;
}
