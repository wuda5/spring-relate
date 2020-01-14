package com.luban.beantest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 注意TestBeanFactoryPostProcessor 是自定义的一个 "工厂”后置处理器，内置的那个是ConfigurationClassPostProcessor
 * 这里是加了@Compnent的，还有一种写法是不加注解，直接在测试类中搞；
 * */
@Component
@Description("test-TestBeanFactoryPostProcessor--")
public class TestBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	public TestBeanFactoryPostProcessor() {
		System.out.println("++++++++[A.2.] TestBeanFactoryPostProcessor 自定义的外部 实现了-->BeanFactoryPostProcessor 的工厂后置处理器 ，构造 +++++++++");
	}

	/* *
	 *这个工厂后置处理器的集合定义在bean工厂里，而并没有加组件注解@component其实它就不会给注册成bd,无bean生命（因为它处理的地方不同！！！）
	 * 即看出，对于这工厂后置处理器，spring是做了两套分别适配的，和 在reader中那个 包扫描器，类似，对比来，感觉多处采用此策略吧！！！！
	 **/
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException{
		//可以拿到刚扫描完后，都没有其他xxx, 这里就是可以通过 暴露传来工厂beanFactory 能进行任何 额外的操作，
		// 达到干预 工厂初始后的一系列 后置处理器操作
	}
}
