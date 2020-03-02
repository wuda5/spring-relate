package com.luban.service;

import org.springframework.stereotype.Component;

/**
 *
 * -- 注意：这个类是普通不 会被生成 代理对象！！
 * */
@Component
public class Putong {

	/**
	 * 除法
	 */
	public int haha(int i, int j) {
		System.out.println("Putong.haha方法被调用");
		return i / j;
	}

}
