package com.luban.service;

import com.luban.aspects.Annotation.TimeAccord;
import org.springframework.stereotype.Component;

/**
 * 2】、定义一个业务逻辑类 TimeService.java  -- 对应的适配切面类是 TimeAspects
 * -- 注意：就是这个类 会被生成 代理对象！！
 * */
@Component
public class TimeService {

	/**
	 * 除法 -- 自定义的注解 @TimeAccord,
	 */
	@TimeAccord
	public int timeYes(int i, int j) {
		System.out.println("TimeService.div方法被调用");
		return i / j;
	}

	/**
	 * 专门写个方法，这个方法是不会被那些通知所适配，即不会对它争强，但是对当前整个类还是代理类的！
	 */
	public int timeNo(int i, int j) {
		System.out.println("TimeService.notProxyed 方法被调用");
		return i / j;
	}

}
