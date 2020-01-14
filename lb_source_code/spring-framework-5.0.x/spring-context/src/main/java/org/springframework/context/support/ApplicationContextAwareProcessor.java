/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.context.support;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.Aware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.EmbeddedValueResolver;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.lang.Nullable;
import org.springframework.util.StringValueResolver;

/**
 *这里 after 啥都不会做，主要就是看 before
 *
 * 这个类说起来相当复杂---终于作用地方看：invokeAwareInterfaces 方法
 * 要从他的父类BeanPostProcessor说起，可以先查看他的父类，
 * 看完父类之后再来下面的注释
 *
 * {@link org.springframework.beans.factory.config.BeanPostProcessor}
 * implementation that passes the ApplicationContext to beans that
 * implement the {@link EnvironmentAware}, {@link EmbeddedValueResolverAware},
 * {@link ResourceLoaderAware}, {@link ApplicationEventPublisherAware},
 * {@link MessageSourceAware} and/or {@link ApplicationContextAware} interfaces.
 *
 * <p>Implemented interfaces are satisfied in order of their mention above.
 *
 * <p>Application contexts will automatically register this with their
 * underlying bean factory. Applications do not use this directly.
 *
 * @author Juergen Hoeller
 * @author Costin Leau
 * @author Chris Beams
 * @since 10.10.2003
 * @see org.springframework.context.EnvironmentAware
 * @see org.springframework.context.EmbeddedValueResolverAware
 * @see org.springframework.context.ResourceLoaderAware
 * @see org.springframework.context.ApplicationEventPublisherAware
 * @see org.springframework.context.MessageSourceAware
 * @see org.springframework.context.ApplicationContextAware
 * @see org.springframework.context.support.AbstractApplicationContext#refresh()
 */
class ApplicationContextAwareProcessor implements BeanPostProcessor {

	private final ConfigurableApplicationContext applicationContext;

	private final StringValueResolver embeddedValueResolver;


	/**
	 * Create a new ApplicationContextAwareProcessor for the given context.
	 */
	public ApplicationContextAwareProcessor(ConfigurableApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.embeddedValueResolver = new EmbeddedValueResolver(applicationContext.getBeanFactory());
		// --自己加一个打印--是在prepareBeanFactory 中执行	beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));调用到这构造的，
		//而并不需要也灭有 在 bdMap中注册！！！
		// 能够在bean中获得到各种*Aware（*Aware都有其作用）---重要！，但不是注册xx!!
		System.out.println(" ******** [0.] 内部的 ApplicationContextAwareProcessor（但它还并没有注册bd!!？后面会？） 实现了-->BeanPostProcessor 的后置处理器， 构造器方法 ********** ");
		System.out.println(" ApplicationContextAwareProcessor 的构造是在prepareBeanFactory 中执行\tbeanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));调用到这构造的，");
	}


	@Override
	@Nullable
	public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {
		AccessControlContext acc = null;

		if (System.getSecurityManager() != null &&
				(bean instanceof EnvironmentAware || bean instanceof EmbeddedValueResolverAware ||
						bean instanceof ResourceLoaderAware || bean instanceof ApplicationEventPublisherAware ||
						bean instanceof MessageSourceAware || bean instanceof ApplicationContextAware)) {
			acc = this.applicationContext.getBeanFactory().getAccessControlContext();
		}

		if (acc != null) {
			AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
				invokeAwareInterfaces(bean);
				return null;
			}, acc);
		}
		else {
			invokeAwareInterfaces(bean);/**重要！！*/
		}

		return bean;
	}
	/** 如下面中最后一个锲约：spring帮你set一个applicationContext对象
	 *
	 * --这里，ApplicationContextAwareProcessor 后置处理器，说明会对那些所实现了 ApplicationContextAware(她虽然不是后置处理器，但是
	 *实现了它接口的bean 却会 被这个 内置的后置处理器类 ApplicationContextAwareProcessor 所捕获
	 * （其实每个 bean 都会把所有的 后置处理器执行，
	 * 只是看 后置处理器自己想定义对哪些 bean 才会去做处理，这就相当是一种锲约，
	 * 就比如当前这个很重要的spring 内部自己提供的后置处理器，他是相当于 定义了 6 种锲约，自己定义的bean，如果
	 * 满足6 种if 锲约 的一种或几种，都就会在当前 锲约内的逻辑中，执行锲约的逻辑，也就可能 干预到 bean 的对象状态如给他的属性赋值等）， 的bean
	 *所以当我们自己的一个对象实现了ApplicationContextAware对象只需要提供setter就能得到applicationContext对象
	 * 此处应该有鲜花。。。。
	 */
	private void invokeAwareInterfaces(Object bean) {
		if (bean instanceof Aware) {
			if (bean instanceof EnvironmentAware) {

				((EnvironmentAware) bean).setEnvironment(this.applicationContext.getEnvironment());
			}
			if (bean instanceof EmbeddedValueResolverAware) {
				((EmbeddedValueResolverAware) bean).setEmbeddedValueResolver(this.embeddedValueResolver);
			}
			if (bean instanceof ResourceLoaderAware) {
				((ResourceLoaderAware) bean).setResourceLoader(this.applicationContext);
			}
			if (bean instanceof ApplicationEventPublisherAware) {
				((ApplicationEventPublisherAware) bean).setApplicationEventPublisher(this.applicationContext);
			}
			if (bean instanceof MessageSourceAware) {
				((MessageSourceAware) bean).setMessageSource(this.applicationContext);
			}
			/**spring帮你set一个applicationContext对象（只要bean 你实现了接口 ApplicationContextAware）
			 *所以当我们自己的一个对象实现了ApplicationContextAware对象只需要提供setter就能得到applicationContext对象
			 * 此处应该有鲜花。。。。
			 */
			if (bean instanceof ApplicationContextAware) {
				if (!bean.getClass().getSimpleName().equals("IndexDao"))
				((ApplicationContextAware) bean).setApplicationContext(this.applicationContext);
			}
		}
	}
     /**这里 after 啥都不会做，主要就是看 before*/
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		return bean;
	}

}
