package com.luban.beantest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Description;

/**
 * 注意 TestBeanDefinitionRegistryPostProcessorWhereNoComponent 是自定义的一个 "工厂”后置处理器，内置的那个是ConfigurationClassPostProcessor
 * 这里是 没有 Compnent的，还有一种写法是加注解
 * */
//@Component
@Description("test-TestBeanDefinitionRegistryPostProcessorWhereNoComponent--")
public class TestBeanDefinitionRegistryPostProcessorWhereNoComponent implements BeanDefinitionRegistryPostProcessor {

	/* *
*/
	public TestBeanDefinitionRegistryPostProcessorWhereNoComponent() {
		System.out.println("++++++++[A.1.] TestBeanFactoryPostProcessorWhereNoComponent 自定义的外部 实现了-->BeanFactoryPostProcessor 的工厂后置处理器 ，构造 +++++++++");
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException{

	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

	}
}
