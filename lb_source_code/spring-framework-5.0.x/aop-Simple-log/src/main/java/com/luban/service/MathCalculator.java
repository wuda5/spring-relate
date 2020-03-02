package com.luban.service;

import org.springframework.stereotype.Component;

/**
 * 2】、定义一个业务逻辑类 MathCalculator.java
 * -- 注意：就是这个类 会被生成 代理对象！！
 * */
@Component
public class MathCalculator {

	/**
	 * 除法
	 */
	public int div(int i, int j) {
		System.out.println("MathCalculator.div方法被调用");
		return i / j;
	}

	/**
	 * 专门写个方法，这个方法是不会被那些通知所适配，即不会对它争强，但是对当前整个类还是代理类的！
	 */
	public int notProxyed(int i, int j) {
		System.out.println("MathCalculator.notProxyed 方法被调用");
		return i / j;
	}

}
