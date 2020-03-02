package com.luban.dao.impl;

import com.luban.aspects.Annotation.TimeAccord;
import com.luban.dao.TimeDaoInterface;


/**
 * 是否这种接口bean, 被代理只能是jdk ，cglib不行？-- 测试
 * */
//@Component
//@Primary    /** 不加此注解的化，由于它和另一个类 同时都是实现了TimeDao 接口，那么在 ioc.getBean(TimeDao.class) 时候就会找到两个而出错！！！**/
//@Qualifier
public class TimeDaoImpl_x implements TimeDaoInterface {


	@TimeAccord
	@Override
	public int timeDaoYes(int i, int j) {

		System.out.println("接口实现类--x.timeDaoYes 方法被调用");
		return i/j;
	}
}
