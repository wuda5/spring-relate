package com.luban.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;

@Component
public class IndexServiceByResouce {
	// --注意：jdk9加载不到这个Resource类！！，要实现xx jdk的版本需注意！！！
	// 加他都可以不用加setLuban方法-->那是xml时候写法且是在xml中配置了自动专配的方式的？，
	// 这个就直接含xxx
	@Resource
//    @Autowired
	Luban luban;

	public void testxx() {
		boolean present = ClassUtils.isPresent("javax.annotation.Resource", AnnotationConfigUtils.class.getClassLoader());

		System.out.println("属性luban:["+luban +"],boolean:"+present);
	}



}
