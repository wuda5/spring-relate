package com.luban.beantest;

import com.luban.beantest.putong.people;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

//import javax.annotation.PostConstruct;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 注意看spring自己内置的一个很重要的后置处理器，ApplicationContextAwareProcessor
 * 看他的里面自己写的注释
 * */
@Component
@Description("test-TestBeanInitializingBean--bean ")
public class TestBeanInitializingBean implements InitializingBean {

	@Autowired
	people pInterface;

	@PostConstruct
	public void init(){
		System.out.println("-----------执行初始init方法------??为何没有执行？？？--------");
	}

	@Override
	public 	void afterPropertiesSet() throws Exception{

		System.out.println("---------implements InitializingBean--------构造------mabtis--factoryBean--set---将测mapper接口交给xxx 解析得到mapper接口上的方法注解信息-->放到mpa---");

	}

}
