package com.luban.beantest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

/**
 * 注意TestBeanFactoryPostProcessor 是自定义的一个 "工厂”后置处理器，内置的那个是ConfigurationClassPostProcessor
 * 这里是 没有 Compnent的，还有一种写法是加注解
 * */
//@Component
@Description("test-TestBeanFactoryPostProcessorWhereNoComponent--")
public class TestBeanFactoryPostProcessorWhereNoComponent implements BeanFactoryPostProcessor {

	/* *
	*这个工厂后置处理器的集合定义不在bean工厂里，原因是考虑外面这样调用：oc.addBeanFactoryPostProcessor(new TestBeanFactoryPostProcessorWhereNoComponent());
   * 且此TestBeanFactoryPostProcessorWhereNoComponent 并没有加组件注解@component其实它就不会给注册成bd,无bean生命,而如果采用另一种使用方式的话，
	* 会注册到bd里面的，完成bean生命，且，对应维护的 beanFactoryPostProcessors 是在 bean工厂里DefaultLisableBeanFactory 的,
	* 即看出，对于这工厂后置处理器，spring是做了两套分别适配的，和 在reader中那个 包扫描器，类似，对比来，感觉多处采用此策略吧！！！！
	 **/
	public TestBeanFactoryPostProcessorWhereNoComponent() {
		System.out.println("++++++++[A.1.one ] TestBeanFactoryPostProcessorWhereNoComponent 自定义的外部 实现了-->BeanFactoryPostProcessor 的工厂后置处理器 ，构造 +++++++++");
	}

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException{
		//可以拿到刚扫描完后，都没有其他xxx, 这里就是可以通过 暴露传来工厂beanFactory 能进行任何 额外的操作，
		// 达到干预 工厂初始后的一系列 后置处理器操作
		System.out.println("-----自定义的一个 \"工厂”后置处理器 ,但没有加@component ,采用main中获取ioc后，ioc.addxxx 调用到这进行对工厂初始 扩展（其他任何bean类都还未被 生命，最多是 app等内置的注册了bd而已，所以可称作干预工厂）--------------");
	}
}
