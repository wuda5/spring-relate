package com.luban.imports;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
/**
 * 在那边拿的时候就不能根据class 来，而是应该根据 name,因为已经是代理对象了
 * */
public class MyInvocationHandler implements InvocationHandler {

	Object target;

	public MyInvocationHandler(Object target){
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("############  wo shi 返回代理类对象， 增强 了 目标对象target: "+target.getClass().getSimpleName()+"+++++++"+"在其目标对象执行方法后执行，【前置】处理111xxxx");

		proxy = method.invoke(target,args);

		System.out.println("############  wo shi 代理类对象， 增强 了 目标对象target: "+target.getClass().getSimpleName()+"+++++++"+"在其目标对象执行方法后执行，【后置】处理222xxxx");

		return proxy;
	}
}
