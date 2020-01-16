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

package org.springframework.context.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.OrderComparator;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.lang.Nullable;

/**
 * 会在currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));里 将重要的 configrurationClassPostProcessor实例初始放入sintonMap
 * Delegate for AbstractApplicationContext's post-processor handling.
 *
 * @author Juergen Hoeller
 * @since 4.0
 */
final class PostProcessorRegistrationDelegate {
    /**--注意了：这里说的BeanDefinitionRegistryPostProcessor 和 后置处理器 beanPostProcessor接口不是一回事没联系！！，
	 * 这里的ConfigurationAnnotationProcessor就是BeanDefinitionRegistryPostProcessor用来扫描配置类注解类上的那些bean的主要*/
	public static void invokeBeanFactoryPostProcessors(
			ConfigurableListableBeanFactory beanFactory, List<BeanFactoryPostProcessor> beanFactoryPostProcessors) {

		// Invoke BeanDefinitionRegistryPostProcessors first, if any.
		Set<String> processedBeans = new HashSet<>();

		if (beanFactory instanceof BeanDefinitionRegistry) {
			BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;

			List<BeanFactoryPostProcessor> regularPostProcessors = new ArrayList<>();
			List<BeanDefinitionRegistryPostProcessor> registryProcessors = new ArrayList<>();

			//自定义的beanFactoryPostProcessors
			for (BeanFactoryPostProcessor postProcessor : beanFactoryPostProcessors) {
				if (postProcessor instanceof BeanDefinitionRegistryPostProcessor) {
					BeanDefinitionRegistryPostProcessor registryProcessor =
							(BeanDefinitionRegistryPostProcessor) postProcessor;
					registryProcessor.postProcessBeanDefinitionRegistry(registry);
					registryProcessors.add(registryProcessor);
				}
				else {//BeanDefinitionRegistryPostProcessor  BeanfactoryPostProcessor
					regularPostProcessors.add(postProcessor);
				}
			}

			// Do not initialize FactoryBeans here: We need to leave all regular beans
			// uninitialized to let the bean factory post-processors apply to them!
			// Separate between BeanDefinitionRegistryPostProcessors that implement
			// PriorityOrdered, Ordered, and the rest.--注意了：这里说的BeanDefinitionRegistryPostProcessor 和 后置处理器 beanPostProcessor接口不是一回事没联系！！，这里的ConfigurationAnnotationProcessor就是BeanDefinitionRegistryPostProcessor用来扫描配置类注解类上的那些bean的主要
			//这个currentRegistryProcessors 放的是spring内部自己实现了BeanDefinitionRegistryPostProcessor接口的对象

			List<BeanDefinitionRegistryPostProcessor> currentRegistryProcessors = new ArrayList<>();

			/** First, invoke the BeanDefinitionRegistryPostProcessors that implement PriorityOrdered.--
			 * ！！第一次调用到这时，postProcessorNames里面是：org.springframework.context.annotation.internalConfigurationAnnotationProcessor，类的全路径，
			 * 其得到的实现过程：大致是在工厂中之前加的bdfMap中查看内部这些哪个实现了BeanDefinitionRegistryPostProcessor接口的，只有那一个，后弄出他的信息来；
			*BeanDefinitionRegistryPostProcessor 等于 BeanFactoryPostProcessor（BeanDefinitionRegistryPostProcessor 继承了 BeanFactoryPostProcessor）
			 * getBeanNamesForType  根据bean的类型获取bean的名字ConfigurationClassPostProcessor
			*/
			String[] postProcessorNames =
					beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
			//这个地方可以得到一个BeanFactoryPostProcessor接口的实现（internal）ConfigurationClassPostProcessor，因为是spring默认在最开始自己注册的，现在是要把它创建bean出来（实例化放到工厂的singtonMap中）
			//为什么要在最开始注册这个呢？
			//因为spring的工厂需要许解析去扫描等等功能
			//而这些功能都是需要在spring工厂初始化完成之前执行
			//要么在工厂最开始的时候、要么在工厂初始化之中，反正不能再之后
			//因为如果在之后就没有意义，因为那个时候已经需要使用工厂了
			//所以这里spring'在一开始就注册了一个BeanFactoryPostProcessor（即完成了ConfigurationClassPostProcessor它的生命），用来插手springfactory的实例化过程
			//在这个地方断点可以知道这个类叫做ConfigurationClassPostProcessor
			//ConfigurationClassPostProcessor那么这个类能干嘛呢？可以参考源码
			//下面我们对这个牛逼哄哄的类（他能插手spring工厂的实例化过程还不牛逼吗？）重点解释--beanFactory.getBean就是再创建！！一个bean放到工厂的singltonMap中供外部可以用了都
			for (String ppName : postProcessorNames) {
				if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
					currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
					processedBeans.add(ppName);
				}
			}
			//排序不重要，况且currentRegistryProcessors这里也只有一个数据
			sortPostProcessors(currentRegistryProcessors, beanFactory);
			//合并list，不重要(为什么要合并，因为还有自己的)
			registryProcessors.addAll(currentRegistryProcessors);
			//最重要。注意这里是方法调用
			//执行所有BeanDefinitionRegistryPostProcessor
			/**
			 * 作用：一句话：扫描生成bdfs放入工厂（即准备好bdf原料）
			 * 如果是第一次通过fresh调用执行到这里话，就已经那么currentRegistryProcessors=上面beanFactory.getBean后刚刚所创建好的一个在已经spring的bean工厂中所存在的真的可用的对象bean了，
			 * 1.无论如何currentRegistryProcessors这个集合中都会有一个上面所说的spring默认的BeanDefinitionRegistryPostProcessor（ConfigurationClassPostProcessor），其中ConfigurationClassPostProcessor
			 * 肯定会执行对appconfig的上面包注解scan路径com.luban后分析经过复杂操作，生成所要注册的bd到上下文（但注意，此时并没有实例化他们），
			 * 2.经过1后，并且经过下面的一系列后beanFactory就有了很多扫描的bd了，而如果此bd中恰好有其他的类定义时实现了接口BeanDefinitionRegistryPostProcessor 继承了 BeanFactoryPostProcessor，
			 * 那么它后面就可一在后来调用时，干预了（因为它也是属于BeanDefinitionRegistryPostProcessor）？理解的对否》？
			 * 第一次fresh调用执行到这里话所用的ConfigurationClassPostProcessor就是实现了接口 BeanDefinitionRegistryPostProcessor的
			 * */
			invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
			//执行完成了所有BeanDefinitionRegistryPostProcessor
			//这个list只是一个临时变量，故而要清除--
			currentRegistryProcessors.clear();

			// Next, invoke the BeanDefinitionRegistryPostProcessors that implement Ordered.
			/**注意：这里再次做了一次从 bdfMap 获取 所有的BeanFactoryPostProcessor 的类路径，因为经过上面内置的哪个 BeanFactoryPostProcessor（ConfigurationClassPostProcessor）后，
			 * 就已经将com.luban下所有满足的 bean 的bdf加入工厂bdfMap了，这里面可能就又有 自己我定义的实现了接口 BeanFactoryPostProcessor 的bdf，所有要再拿一次！！，
			 * 如果真的有自定的那些实现xx的bean(bdf),那下面又会通过 beanFactory.getBean 完成上面类似操作（即将他实例化到 单例池里 singtonMap）,--而内部的哪个ConfigurationClassPostProcessor
			 * 再if条件不会通过，就不会重复执行getBean(上面哪个是没有if判断的！！)--通过新的临时变量保存--currentRegistryProcessors
			 * --下面说的好像有点偏差，真的用于保存自定义实现 BeanFactoryProccessors 的是 regularPostProcessors,是最后一步来 执行的？？？
			 * --解释，下面说的话（我自己）应该执行的是实现子类BeanDefinitionRegistryPostProcessor的回调，
			 * 而最后一行执行的是 ：实现了 BeanFactoryPostProcessor的回调
			 *
			 * -- 这里说的理解好像有点问题：自定义的那些是 再main方法里如：直接ioc.addBeanFactorPostProcessor(new MyBeanFactoryProcessor() )这样用的？
			 * --因为我们自己定义的BeanFactoryProcessor可以有 两种 方式  1、实现BeanFactoryProcessor接口  2、实现BeanDefinitionRegistryPostProcessor
			 *
			 * 因为BeanDefinitionRegistryPostProcessor实现了BeanFactoryProcessor
			 * 于是可以猜想实现bdrp和实现bfp是能够完成不同的功能。 其实也可以理解，因为bdrp是子类，他肯定扩展了bfp的功能
			 *-- 所以造成这里有点复杂！！！！！！！！！
			 *
			 * 接着后面再执行一遍invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
			 * ，这里面的执行的关键是调用的接口BeanFactoryPostProcessor实现postProcessor.postProcessBeanDefinitionRegistry(registry); 方法，
			 * 记住，整个过程其他的bean 都还没实例化（除了整个bean 是恰好是实现
			 * 了 BeanFactoryPostProcessor 的哈哈它此时也就是在这里进行它内部的对BeanFactoryPostProcessor接口的实现操作，
			 * 所以，可以这样理解，实现了BeanFactoryPostProcessor，其实它也就不是
			 * 一个 传统的bean,所以就不会像其他bean 是再后面统一完成生命，而是再这里就getbean了，然后下面完成），只是
			 * */
			postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
			for (String ppName : postProcessorNames) {
				if (!processedBeans.contains(ppName) && beanFactory.isTypeMatch(ppName, Ordered.class)) {
					currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
					processedBeans.add(ppName);
				}
			}
			sortPostProcessors(currentRegistryProcessors, beanFactory);
			registryProcessors.addAll(currentRegistryProcessors);
			/***重要：虽然上面也是调用了一遍（那是spring 内置的实现BeanFactoryPostProcessor的类ConfigurationClassPostProcessor 它完成了所有bean的扫苗有了所有bdf
			 ），这里就是相当是对那些自定义实现BeanFactoryPostProcessor 的类处理他们的扩展功能*/
			invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
			currentRegistryProcessors.clear();/**清空原因？*/

			// Finally, invoke all other BeanDefinitionRegistryPostProcessors until no further ones appear.
			boolean reiterate = true;
			while (reiterate) {
				reiterate = false;
				postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
				for (String ppName : postProcessorNames) {
					if (!processedBeans.contains(ppName)) {
						currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
						processedBeans.add(ppName);
						reiterate = true;
					}
				}
				sortPostProcessors(currentRegistryProcessors, beanFactory);
				registryProcessors.addAll(currentRegistryProcessors);
				invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
				currentRegistryProcessors.clear();
			}

			// Now, invoke the postProcessBeanFactory callback   of all processors handled so far.
			//执行BeanFactoryPostProcessor的回调，前面不是吗？
			/**这里说的点睛之笔了： 前面执行的BeanFactoryPostProcessor的子类BeanDefinitionRegistryPostProcessor的回调*/
			//这是执行的是BeanFactoryPostProcessor    postProcessBeanFactory
			//ConfuguratuonClassPpostProcssor
			invokeBeanFactoryPostProcessors(registryProcessors, beanFactory);
			/**重要： 自定义BeanFactoryPostProcessor**/
			invokeBeanFactoryPostProcessors(regularPostProcessors, beanFactory);
		}

		else {
			// Invoke factory processors registered with the context instance.
			invokeBeanFactoryPostProcessors(beanFactoryPostProcessors, beanFactory);
		}

		// Do not initialize FactoryBeans here: We need to leave all regular beans
		// uninitialized to let the bean factory post-processors apply to them!
		//ConfigurationClassPostProcessor
		String[] postProcessorNames =
				beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false);

		// Separate between BeanFactoryPostProcessors that implement PriorityOrdered,
		// Ordered, and the rest.
		List<BeanFactoryPostProcessor> priorityOrderedPostProcessors = new ArrayList<>();
		List<String> orderedPostProcessorNames = new ArrayList<>();
		List<String> nonOrderedPostProcessorNames = new ArrayList<>();
		for (String ppName : postProcessorNames) {
			if (processedBeans.contains(ppName)) {
				// skip - already processed in first phase above
			}
			else if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
				priorityOrderedPostProcessors.add(beanFactory.getBean(ppName, BeanFactoryPostProcessor.class));
			}
			else if (beanFactory.isTypeMatch(ppName, Ordered.class)) {
				orderedPostProcessorNames.add(ppName);
			}
			else {
				nonOrderedPostProcessorNames.add(ppName);
			}
		}

		// First, invoke the BeanFactoryPostProcessors that implement PriorityOrdered.
		sortPostProcessors(priorityOrderedPostProcessors, beanFactory);
		invokeBeanFactoryPostProcessors(priorityOrderedPostProcessors, beanFactory);

		// Next, invoke the BeanFactoryPostProcessors that implement Ordered.
		List<BeanFactoryPostProcessor> orderedPostProcessors = new ArrayList<>();
		for (String postProcessorName : orderedPostProcessorNames) {
			orderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
		}
		sortPostProcessors(orderedPostProcessors, beanFactory);
		invokeBeanFactoryPostProcessors(orderedPostProcessors, beanFactory);

		// Finally, invoke all other BeanFactoryPostProcessors.
		List<BeanFactoryPostProcessor> nonOrderedPostProcessors = new ArrayList<>();
		for (String postProcessorName : nonOrderedPostProcessorNames) {
			nonOrderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
		}
		invokeBeanFactoryPostProcessors(nonOrderedPostProcessors, beanFactory);

		// Clear cached merged bean definitions since the post-processors might have
		// modified the original metadata, e.g. replacing placeholders in values...
		beanFactory.clearMetadataCache();
	}

	public static void registerBeanPostProcessors(
			ConfigurableListableBeanFactory beanFactory, AbstractApplicationContext applicationContext) {

		//从beanDefinitionMap中得到所有的 BeanPostProcessor,包含自己定义的 testBeanPostProcessor,其中有俩个内置的 internalAutowiredAnnotationProcessor（重要！），internalRequiredAnnotationProcessor
		String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class, true, false);

		// Register BeanPostProcessorChecker that logs an info message when
		// a bean is created during BeanPostProcessor instantiation, i.e. when
		// a bean is not eligible for getting processed by all BeanPostProcessors.
		int beanProcessorTargetCount = beanFactory.getBeanPostProcessorCount() + 1 + postProcessorNames.length;
		beanFactory.addBeanPostProcessor(new BeanPostProcessorChecker(beanFactory, beanProcessorTargetCount));/**这一步会：this.beanPostProcessors.add(beanPostProcessor);*/

		// Separate between BeanPostProcessors that implement PriorityOrdered,
		// Ordered, and the rest.
		List<BeanPostProcessor> priorityOrderedPostProcessors = new ArrayList<>();
		List<BeanPostProcessor> internalPostProcessors = new ArrayList<>();
		List<String> orderedPostProcessorNames = new ArrayList<>();
		List<String> nonOrderedPostProcessorNames = new ArrayList<>();
		for (String ppName : postProcessorNames) {
			if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
				/**注意这个方法：会执行其他的bean的实例化和xxx完成xxx*/
				BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
				priorityOrderedPostProcessors.add(pp);
				if (pp instanceof MergedBeanDefinitionPostProcessor) {
					internalPostProcessors.add(pp);
				}
			}
			else if (beanFactory.isTypeMatch(ppName, Ordered.class)) {
				orderedPostProcessorNames.add(ppName);
			}
			else {
			/**自己定义的且没实现PriorityOrdered或Ordered接口的， testBeanPostProcessor 会在这里执行 放入一个nonOrderedPostProcessorNames 集合中的，
			 * 注意和上面区别：上面马上就实例化了！！？这是考虑优先级问题吧--接口：PriorityOrdered ，Ordered
			 而不是上面第一个if中内部dbf执行并会完成bean，即说明内部的那些xx是优先保证执行完成后，才会执行自定义的xxx,
			 它是在下面的 registerBeanPostProcessors(beanFactory, nonOrderedPostProcessors);中将次processor给工厂上的，
			 但也是先在for循环所有的 自定义prossor执行 beanFactory.getBean 完成此processor的bean生命,后才执行的
			 */
				nonOrderedPostProcessorNames.add(ppName);
			}
		}
		priorityOrderedPostProcessors.remove(1);
		// First, register the BeanPostProcessors that implement PriorityOrdered.
		sortPostProcessors(priorityOrderedPostProcessors, beanFactory);
		/**beanFactory.addBeanPostProcessor(postProcessor);*/
		registerBeanPostProcessors(beanFactory, priorityOrderedPostProcessors);

		// Next, register the BeanPostProcessors that implement Ordered.
		List<BeanPostProcessor> orderedPostProcessors = new ArrayList<>();
		for (String ppName : orderedPostProcessorNames) {
			/**注意这个方法：会执行其他的bean的实例化和xxx完成xxx*/
			BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
			orderedPostProcessors.add(pp);
			if (pp instanceof MergedBeanDefinitionPostProcessor) {
				internalPostProcessors.add(pp);
			}
		}
		sortPostProcessors(orderedPostProcessors, beanFactory);
		registerBeanPostProcessors(beanFactory, orderedPostProcessors);

		// Now, register all regular BeanPostProcessors.
		List<BeanPostProcessor> nonOrderedPostProcessors = new ArrayList<>();
		for (String ppName : nonOrderedPostProcessorNames) {
			/**这一行关键：将自己定义的processor 完成 其bean的生命周期了，后下面才将xx交给工厂的proscessor集合上面！，
			 * 所以由于在完成bean的创建过程时，bean工厂的processors集合中还没有此processor，即此时也就还没有法执行到自定义prossor中的扩展方法，还是在后面完成的？
			*/
			BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);
			nonOrderedPostProcessors.add(pp);
			if (pp instanceof MergedBeanDefinitionPostProcessor) {
				internalPostProcessors.add(pp);
			}
		}
		registerBeanPostProcessors(beanFactory, nonOrderedPostProcessors);

		// Finally, re-register all internal BeanPostProcessors.
		internalPostProcessors.remove(1);
		sortPostProcessors(internalPostProcessors, beanFactory);

		registerBeanPostProcessors(beanFactory, internalPostProcessors);

		// Re-register post-processor for detecting inner beans as ApplicationListeners,
		// moving it to the end of the processor chain (for picking up proxies etc).
		beanFactory.addBeanPostProcessor(new ApplicationListenerDetector(applicationContext));
	}

	private static void sortPostProcessors(List<?> postProcessors, ConfigurableListableBeanFactory beanFactory) {
		Comparator<Object> comparatorToUse = null;
		if (beanFactory instanceof DefaultListableBeanFactory) {
			comparatorToUse = ((DefaultListableBeanFactory) beanFactory).getDependencyComparator();
		}
		if (comparatorToUse == null) {
			comparatorToUse = OrderComparator.INSTANCE;
		}
		postProcessors.sort(comparatorToUse);
	}

	/**
	 * Invoke the given BeanDefinitionRegistryPostProcessor beans.
	 * 注意对比下面这个方法
	 * BeanDefinitionRegistryPostProcessor和BeanFactoryPostProcessor
	 */
	private static void invokeBeanDefinitionRegistryPostProcessors(
			Collection<? extends BeanDefinitionRegistryPostProcessor> postProcessors, BeanDefinitionRegistry registry) {

		//因为只有一条数据--第一次执行fresh的话，postProcessor是内部注册的bean经过currentRegistryProcessors.add(beanFactory.getBean()后创造的实际存在spring可以用的bean（ConfigurationClassPostProcessor）
		//下面这个很关键的方法就是ConfigurationClassPostProcessor中执行的 --processConfigBeanDefinitions(registry);中的this.reader.loadBeanDefinitions(configClasses); 就是来处理注册特殊@import @bean xml的情况的bean的注册的,parser.parse(candidates);是有解析bean注册普通bd
		for (BeanDefinitionRegistryPostProcessor postProcessor : postProcessors) {
			postProcessor.postProcessBeanDefinitionRegistry(registry);//BeanDefinitionRegistryPostProcessor是接口 -第一次执行fresh的话就会到 ConfigurationClassPostProcessor中执行
		}
	}

	/**
	 * Invoke the given BeanFactoryPostProcessor beans.
	 */
	private static void invokeBeanFactoryPostProcessors(
			Collection<? extends BeanFactoryPostProcessor> postProcessors, ConfigurableListableBeanFactory beanFactory) {

		for (BeanFactoryPostProcessor postProcessor : postProcessors) {
			postProcessor.postProcessBeanFactory(beanFactory);
		}
	}

	/**
	 * Register the given BeanPostProcessor beans.
	 */
	private static void registerBeanPostProcessors(
			ConfigurableListableBeanFactory beanFactory, List<BeanPostProcessor> postProcessors) {

		for (BeanPostProcessor postProcessor : postProcessors) {
			beanFactory.addBeanPostProcessor(postProcessor);
		}
	}


	/**
	 * BeanPostProcessor that logs an info message when a bean is created during
	 * BeanPostProcessor instantiation, i.e. when a bean is not eligible for
	 * getting processed by all BeanPostProcessors.
	 * 当Spring的配置中的后处理器还没有被注册就已经开始了bean的初始化
	 *	便会打印出BeanPostProcessorChecker中设定的信息
	 */
	private static final class BeanPostProcessorChecker implements BeanPostProcessor {

		private static final Log logger = LogFactory.getLog(BeanPostProcessorChecker.class);

		private final ConfigurableListableBeanFactory beanFactory;

		private final int beanPostProcessorTargetCount;

		public BeanPostProcessorChecker(ConfigurableListableBeanFactory beanFactory, int beanPostProcessorTargetCount) {
			this.beanFactory = beanFactory;
			this.beanPostProcessorTargetCount = beanPostProcessorTargetCount;
		}

		@Override
		public Object postProcessBeforeInitialization(Object bean, String beanName) {
			return bean;
		}

		@Override
		public Object postProcessAfterInitialization(Object bean, String beanName) {
			if (!(bean instanceof BeanPostProcessor) && !isInfrastructureBean(beanName) &&
					this.beanFactory.getBeanPostProcessorCount() < this.beanPostProcessorTargetCount) {
				if (logger.isInfoEnabled()) {
					logger.info("Bean '" + beanName + "' of type [" + bean.getClass().getName() +
							"] is not eligible for getting processed by all BeanPostProcessors " +
							"(for example: not eligible for auto-proxying)");
				}
			}
			return bean;
		}

		private boolean isInfrastructureBean(@Nullable String beanName) {
			if (beanName != null && this.beanFactory.containsBeanDefinition(beanName)) {
				BeanDefinition bd = this.beanFactory.getBeanDefinition(beanName);
				return (bd.getRole() == RootBeanDefinition.ROLE_INFRASTRUCTURE);
			}
			return false;
		}
	}

}
