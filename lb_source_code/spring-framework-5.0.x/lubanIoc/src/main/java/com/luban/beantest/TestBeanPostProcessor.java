package com.luban.beantest;

import com.luban.dao.Dao;
import com.luban.dao.IndexDao1;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Description;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 注意看spring自己内置的一个很重要的后置处理器，ApplicationContextAwareProcessor
 * 看他的里面自己写的注释
 * */
@Component
@Description("test-TestBeanPostProcessor--所有bean 都有肯能执行到这个 后置处理器，即下面方法会被执行很多遍的，" +
		"注意：后置处理器 都是优先于 其他bean 先 被初始完成！，让后面的每一个 bean 执行时会执行所有后置处理器")
public class TestBeanPostProcessor implements BeanPostProcessor{

	private List<String> plistprop;

	public List<String> getPlistprop() {
		return plistprop;
	}

	public void setPlistprop(List<String> plistprop) {
		this.plistprop = plistprop;
	}

	public TestBeanPostProcessor() {
		System.out.println("+++++++++ [3.] TestBeanPostProcessor 实现了 -->BeanPostProcessor 【自定义】后置处理器, 构造方法  +++++++++");
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		/**这里高能--可以看到这里可以拿到bean!!,*/
		if (bean.getClass().getSimpleName().equals("xxx"))
		{
			// 做一些xxxxx，aop 就是可以这里 来 生成 代理对象！！
		}

		if (plistprop ==null || plistprop.isEmpty())
		{
			plistprop = new ArrayList<>();
			plistprop.add("初始init执行前，给操作属性在加一个元素这个哈哈");
			System.out.println("postProcessBeforeInitialization: init[ 1 ]执行 前，给操作属性在加一个元素这个哈哈 size is ["+plistprop.size()+"]");
		}
		return bean;
	}

	/**
	 * 从控制台的打印可以发现，就我自己当前这个实现：
	 * 1. postProcessBeforeInitialization 这个init 前方法 只是执行了 一次，而
	 *
	 * 2. beanPostProcessor的类会打印执行很多次，每次执行的数据都会累计，以最后一次执行到的和，每个
	 * 看源码中，也是for循环中执行xxxx,时后面的每个bean 都要来一次？？
	 * **/
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		Assert.notEmpty(plistprop,"+++ 非法了吧");
		plistprop.add("init后，观察plistaprop 集合是否是满足自己期望是1了，在加入此数据到list");
		System.out.println("postProcessBeforeInitialization: init[ 2 ]执行 后，观察plistaprop 集合是否是满足自己期望是1了，在加入此数据到list,size is ["+plistprop.size()+"]");
		return bean;
	}
}
