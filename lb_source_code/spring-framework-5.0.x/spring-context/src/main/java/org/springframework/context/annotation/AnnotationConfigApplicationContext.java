/*
 * Copyright 2002-2018 the original author or authors.
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

package org.springframework.context.annotation;

import java.util.function.Supplier;

import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * Spring中出来注解Bean定义的类有两个：
 * AnnotationConfigApplicationContext和
 * AnnotationConfigWebApplicationContex。
 * nnotationConfigWebApplicationContext
 * 是AnnotationConfigApplicationContext的web版本
 * 两者的用法以及对注解的处理方式几乎没有什么差别
 * 通过分析这个类我们知道注册一个bean到spring容器有两种办法
 * 一、直接将注解Bean注册到容器中：（参考）public void register(Class<?>... annotatedClasses)
 * 但是直接把一个注解的bean注册到容器当中也分为两种方法
 * 1、在初始化容器时注册并且解析
 * 2、也可以在容器创建之后手动调用注册方法向容器注册，然后通过手动刷新容器，使得容器对注册的注解Bean进行处理。
 * 思考：为什么@profile要使用这类的第2种方法
 *
 * 二、通过扫描指定的包及其子包下的所有类
 * 扫描其实同上，也是两种方法，初始化的时候扫描，和初始化之后再扫描
 *
 * Standalone application context, accepting annotated classes as input - in particular
 * {@link Configuration @Configuration}-annotated classes, but also plain
 * {@link org.springframework.stereotype.Component @Component} types and JSR-330 compliant
 * classes using {@code javax.inject} annotations. Allows for registering classes one by
 * one using {@link #register(Class...)} as well as for classpath scanning using
 * {@link #scan(String...)}.
 *
 * <p>In case of multiple {@code @Configuration} classes, @{@link Bean} methods defined in
 * later classes will override those defined in earlier classes. This can be leveraged to
 * deliberately override certain bean definitions via an extra {@code @Configuration}
 * class.
 *
 * <p>See @{@link Configuration}'s javadoc for usage examples.
 *
 * @author Juergen Hoeller
 * @author Chris Beams
 * @since 3.0
 * @see #register
 * @see #scan
 * @see AnnotatedBeanDefinitionReader
 * @see ClassPathBeanDefinitionScanner
 * @see org.springframework.context.support.GenericXmlApplicationContext
 */
public class AnnotationConfigApplicationContext extends GenericApplicationContext implements AnnotationConfigRegistry {

	/**
	 * 被注解的bfr
	 * AnnotatedBeanDefinitionReader这个类顾名思义是一个reader，一个读取器
	 * 读取什么呢？还是顾名思义AnnotatedBeanDefinition意思是读取一个被加了注解的bean
	 * 这个类在构造方法中实例化的
	 */
	private final AnnotatedBeanDefinitionReader reader;

	/**
	 * 同意顾名思义，这是一个扫描器，扫描所有加了注解的bean
	 *  同样是在构造方法中被实例化的
	 */
	private final ClassPathBeanDefinitionScanner scanner;


	/**
	 * 初始化一个bean的读取和扫描器-------在之前调用了其父类的构造方法初始了：DefaultListableBeanFactory beanFactory;
	 * 何谓读取器和扫描器参考上面的属性注释
	 * 默认构造函数，如果直接调用这个默认构造方法，需要在稍后通过调用其register()
	 * 去注册配置类（javaconfig），并调用refresh()方法刷新容器，
	 * 触发容器对注解Bean的载入、解析和注册过程
	 * 这种使用过程我在ioc应用的第二节课讲@profile的时候讲过
	 * Create a new AnnotationConfigApplicationContext that needs to be populated
	 * through {@link #register} calls and then manually {@linkplain #refresh refreshed}.
	 */
	public AnnotationConfigApplicationContext() {
		/**
		 * 作用： 创建一个读取注解的Bean定义读取器 reader (AnnotatedBeanDefinitionReader)--这个类它无实现--也无继承
		 * 父类的构造方法中已经初始了很多信息如：DefaultResourceLoader
		 * 创建一个读取注解的Bean定义读取器--把自己付给它--传入参数是自己this
		 * 什么是bean定义？BeanDefinition
		 *
		 * 完成了spring内部BeanDefinition的注册（主要是后置处理器）
		 * --其实这里就有父类中初始出的：DefaultListableBeanFactory beanFactory;用到作用了的
		 * * --重要：最终调用注册就是在DefaultBeanFactory 中：this.beanDefinitionMap.put(beanName, beanDefinition)
		 * 	 * 将spring！！默认的7个beandef 注册put入map
		 */
		this.reader = new AnnotatedBeanDefinitionReader(this);

		/**
		 * 创建BeanDefinition扫描器--ClassPathBeanDefnitionScanner--
		 * 可以用来扫描包或者类，继而转换为bd
		 *
		 * spring默认的扫描器其实不是这个scanner对象--ClassPathBeanDefinitionScanner中有关联了属性StandardEnvironment--
		 * 而是在后面自己又重新new了一个ClassPathBeanDefinitionScanner--ClassPathBeanDefinitionScanner相当于专门来扫描注解bean的，他是非常重要的后置处理？
		 * spring在执行工程后置处理器ConfigurationClassPostProcessor时，去扫描包时会new一个ClassPathBeanDefinitionScanner
		 *
		 * 这里的scanner仅仅是为了程序员可以手动调用AnnotationConfigApplicationContext对象的scan方法
		 *
		 * 子路的：
		 * 		//可以用来扫描包或者类，继而转换成bd
		 * 		//但是实际上我们扫描包工作不是scanner这个对象来完成的
		 * 		//是spring自己new的一个ClassPathBeanDefinitionScanner--具体是哪里呢？是由上面的 哪个AnnotatedBeanDefinitionReader 对象reader
		 * 	在自己内部搞出来？然后调用xxx 完成默认扫描工作，所以上面哪个 reader	其实是spring最核心的！！
		 *
		 * 	哪里搞出来的？是名字？--在reader 内部它要注册 7个内置的spring xx时候，其中一个很重要的 一个 ConfigruatinClassPostProcessor 的 BeanFactoryPostProcess接口的实现时，
		 * 	这个 ConfigruatinClassPostProcessor 内部呢又有个属性：ConfigurationClassBeanDefinitionReader(和上下文this关系),  MetadataReaderFactory
		 * 	private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
		 * 	你在new 时它的属性 MetadataReaderFactory 也会被new了，而后面所扫描用到的起作用的就是这个 ConfigurationClassBeanDefinitionReader+ CachingMetadataReaderFactory？
		 *
		 * 	而他 reader 里面的那个 BeanFactoryPostProcessor( ConfigurationClassPostProcessor)是他的最重要的类！！
		 *
		 * 		//这里的scanner仅仅是为了程序员能够在外部调用AnnotationConfigApplicationContext对象的scan方法
		 */
		this.scanner = new ClassPathBeanDefinitionScanner(this);
	}

	/**
	 * Create a new AnnotationConfigApplicationContext with the given DefaultListableBeanFactory.
	 * @param beanFactory the DefaultListableBeanFactory instance to use for this context
	 */
	public AnnotationConfigApplicationContext(DefaultListableBeanFactory beanFactory) {
		super(beanFactory);
		this.reader = new AnnotatedBeanDefinitionReader(this);
		this.scanner = new ClassPathBeanDefinitionScanner(this);
	}

	/**
	 * 这个构造方法需要传入一个被javaconfig注解了的配置类
	 * 然后会把这个被注解了javaconfig的类通过注解读取器读取后继而解析
	 * Create a new AnnotationConfigApplicationContext, deriving bean definitions
	 * from the given annotated classes and automatically refreshing the context.
	 * @param annotatedClasses one or more annotated classes,
	 * e.g. {@link Configuration @Configuration} classes
	 */
	public AnnotationConfigApplicationContext(Class<?>... annotatedClasses) {
		/**
		 * 		annotatedClasses  appconfig.class, 其中的属性 reader 会 注册了默认可能7个 bdf到bdfMap(但一个也灭有实例化)，由类似下面 register的作用
		 * 		this();这里由于他有父类GenericApplicationContext，故而会先调用父类的构造方法(注意：直接点击过去是本类的，看不到父类的小心，)，然后才会调用自己的构造方法
		 * 		1.先调用父类构造
		 * 		public GenericApplicationContext() {
		 * 			// 初始化一个beanFactory，会初始话这个很重要的DefaultListableBeanFactory
		 * 			this.beanFactory = new DefaultListableBeanFactory();
		 *                }
		 *      记住这个DefaultListableBeanFactory，非常重要，spring加载的bean都会放到这里面去
		 * 		2.在自己构造方法中初始一个读取器和扫描器--其中
		 * 	就会注册默认spring	7个 的beanDef 到 register---但是都没有实例话，一个很重要的ConfigurationClassPostProcessor这个内部bean在第一次调用下面的refresh中的xx实例了！！（重要）
		 * 	(其实是在DefaultListableBeanFactory 维护的一个 属性map中 ：Map<String, BeanDefinition> beanDefinitionMap)
		 * */
		this();
		// register 方法也是 父接口AnnotationConfigRegistry的方法
		/**这里就是要注册 自己定义的那些 beandef 到上下文上，如果一般采用的一步到位的话，这里的就是注册appconfig配置类的 bd 到bdmap
		 * 这步执行完后，refresh才是真的开始 做实例话bean
		 * */
		register(annotatedClasses);
		refresh();
	}

	/**
	 * Create a new AnnotationConfigApplicationContext, scanning for bean definitions
	 * in the given packages and automatically refreshing the context.
	 * @param basePackages the packages to check for annotated classes
	 */
	public AnnotationConfigApplicationContext(String... basePackages) {
		this();
		scan(basePackages);
		refresh();
	}


	/**
	 * Propagates the given custom {@code Environment} to the underlying
	 * {@link AnnotatedBeanDefinitionReader} and {@link ClassPathBeanDefinitionScanner}.
	 */
	@Override
	public void setEnvironment(ConfigurableEnvironment environment) {
		super.setEnvironment(environment);
		this.reader.setEnvironment(environment);
		this.scanner.setEnvironment(environment);
	}

	/**
	 * Provide a custom {@link BeanNameGenerator} for use with {@link AnnotatedBeanDefinitionReader}
	 * and/or {@link ClassPathBeanDefinitionScanner}, if any.
	 * <p>Default is {@link org.springframework.context.annotation.AnnotationBeanNameGenerator}.
	 * <p>Any call to this method must occur prior to calls to {@link #register(Class...)}
	 * and/or {@link #scan(String...)}.
	 * @see AnnotatedBeanDefinitionReader#setBeanNameGenerator
	 * @see ClassPathBeanDefinitionScanner#setBeanNameGenerator
	 */
	public void setBeanNameGenerator(BeanNameGenerator beanNameGenerator) {
		this.reader.setBeanNameGenerator(beanNameGenerator);
		this.scanner.setBeanNameGenerator(beanNameGenerator);
		getBeanFactory().registerSingleton(
				AnnotationConfigUtils.CONFIGURATION_BEAN_NAME_GENERATOR, beanNameGenerator);
	}

	/**
	 * Set the {@link ScopeMetadataResolver} to use for detected bean classes.
	 * <p>The default is an {@link AnnotationScopeMetadataResolver}.
	 * <p>Any call to this method must occur prior to calls to {@link #register(Class...)}
	 * and/or {@link #scan(String...)}.
	 */
	public void setScopeMetadataResolver(ScopeMetadataResolver scopeMetadataResolver) {
		this.reader.setScopeMetadataResolver(scopeMetadataResolver);
		this.scanner.setScopeMetadataResolver(scopeMetadataResolver);
	}


	//---------------------------------------------------------------------
	// Implementation of AnnotationConfigRegistry
	//---------------------------------------------------------------------

	/**
	 * 注册单个bean给容器
	 * 比如有新加的类可以用这个方法
	 * 但是注册注册之后需要手动调用refresh方法去触发容器解析注解
	 *
	 * 有两个意思
	 * 他可以注册一个配置类
	 * 他还可以单独注册一个bean
	 * Register one or more annotated classes to be processed.
	 * <p>Note that {@link #refresh()} must be called in order for the context
	 * to fully process the new classes.
	 * @param annotatedClasses one or more annotated classes,
	 * e.g. {@link Configuration @Configuration} classes
	 * @see #scan(String...)
	 * @see #refresh()
	 */
	public void register(Class<?>... annotatedClasses) {
		Assert.notEmpty(annotatedClasses, "At least one annotated class must be specified");
		this.reader.register(annotatedClasses);
	}

	/**
	 * Perform a scan within the specified base packages.
	 * <p>Note that {@link #refresh()} must be called in order for the context
	 * to fully process the new classes.
	 * @param basePackages the packages to check for annotated classes
	 * @see #register(Class...)
	 * @see #refresh()
	 */
	public void scan(String... basePackages) {
		Assert.notEmpty(basePackages, "At least one base package must be specified");
		this.scanner.scan(basePackages);
	}


	//---------------------------------------------------------------------
	// Convenient methods for registering individual beans
	//---------------------------------------------------------------------

	/**
	 * Register a bean from the given bean class, deriving its metadata from
	 * class-declared annotations, and optionally providing explicit constructor
	 * arguments for consideration in the autowiring process.
	 * <p>The bean name will be generated according to annotated component rules.
	 * @param annotatedClass the class of the bean
	 * @param constructorArguments argument values to be fed into Spring's
	 * constructor resolution algorithm, resolving either all arguments or just
	 * specific ones, with the rest to be resolved through regular autowiring
	 * (may be {@code null} or empty)
	 * @since 5.0
	 */
	public <T> void registerBean(Class<T> annotatedClass, Object... constructorArguments) {
		registerBean(null, annotatedClass, constructorArguments);
	}

	/**
	 * Register a bean from the given bean class, deriving its metadata from
	 * class-declared annotations, and optionally providing explicit constructor
	 * arguments for consideration in the autowiring process.
	 * @param beanName the name of the bean (may be {@code null})
	 * @param annotatedClass the class of the bean
	 * @param constructorArguments argument values to be fed into Spring's
	 * constructor resolution algorithm, resolving either all arguments or just
	 * specific ones, with the rest to be resolved through regular autowiring
	 * (may be {@code null} or empty)
	 * @since 5.0
	 */
	public <T> void registerBean(@Nullable String beanName, Class<T> annotatedClass, Object... constructorArguments) {
		this.reader.doRegisterBean(annotatedClass, null, beanName, null,
				bd -> {
					for (Object arg : constructorArguments) {
						bd.getConstructorArgumentValues().addGenericArgumentValue(arg);
					}
				});
	}

	@Override
	public <T> void registerBean(@Nullable String beanName, Class<T> beanClass, @Nullable Supplier<T> supplier,
			BeanDefinitionCustomizer... customizers) {

		this.reader.doRegisterBean(beanClass, supplier, beanName, null, customizers);
	}

}
