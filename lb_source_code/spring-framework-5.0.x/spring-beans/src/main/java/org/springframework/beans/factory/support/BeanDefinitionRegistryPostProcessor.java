/*
 * Copyright 2002-2010 the original author or authors.
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

package org.springframework.beans.factory.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;

/**可以理解成可以--干预后置处理bean 的注册为bd的过程！！！-->其默认实现spring内置的是ConfigurationClassPostProcessor(cgpp)
 * bd注册后置处理器！！！--
 * Extension to the standard {@link BeanFactoryPostProcessor} SPI, allowing for
 * the registration of further bean definitions <i>before</i> regular
 * BeanFactoryPostProcessor detection kicks in. In particular,
 * BeanDefinitionRegistryPostProcessor may register further bean definitions
 * which in turn define BeanFactoryPostProcessor instances.
 *
 * @author Juergen Hoeller
 * @since 3.0.1
 * @see org.springframework.context.annotation.ConfigurationClassPostProcessor
 */
public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor {

	/**
	 * 这个过程发生在那些bean类定义为bd过程后，但是还没进行实例化时，所以可以理解成可以--干预后置处理bean定义 的过程，
	 * 注意：这个方法所执行的过程，再当通过解析到 扫描其他所有bean (后注册完最普通的bd，和执行了此接口的实现类的方法中的
	 * postProcessBeanDefinitionRegistry -->processConfigBeanDefinitions(registry)
	 * -->this.reader.loadBeanDefinitions(configClasses); 当此 （loadBeanDefinitions)方法完成即代表所有的bd 都完成注册了！！
	 * --> 也即说明loadBeanDefinitions 完成后，即代表了 是spring内部的实现的BeanDefinitionRegistryPostProcessor 当然就是指ConfigurationClassPostProcessor,
	 * 就已经全部完成了
	 * 自定义实现继承-->BeanDefinitionRegistryPostProcessor 的再后面再会来调用的
	 * -->可以自己 debug 证明（spring会回调形成的 postProcessBeanDefinitionRegistry方法列表）
	 * 所以仔细品味下 这个接口方法的命名 postProcessBeanDefinitionRegistry --> 专门注册bd!!!!!!
	 *
	 * Modify the application context's internal bean definition registry after its
	 * standard initialization. All regular bean definitions will have been loaded,
	 * but no beans will have been instantiated yet. This allows for adding further
	 * bean definitions before the next post-processing phase kicks in.
	 * @param registry the bean definition registry used by the application context
	 * @throws org.springframework.beans.BeansException in case of errors
	 */
	void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException;

}
