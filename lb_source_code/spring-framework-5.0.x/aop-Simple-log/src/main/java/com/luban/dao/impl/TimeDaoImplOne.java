package com.luban.dao.impl;

import com.luban.aspects.Annotation.TimeAccord;
import com.luban.dao.TimeDaoInterface;
import org.springframework.stereotype.Component;


/**
 * 是否这种接口bean, 被代理只能是jdk ，cglib不行？-- 测试
 * */
@Component
public class TimeDaoImplOne implements TimeDaoInterface {


	@TimeAccord
	@Override
	public int timeDaoYes(int i, int j) {

		System.out.println("接口实现类--TimeDaoImpl.timeDaoYes 方法被调用");
		return i/j;
	}
}
