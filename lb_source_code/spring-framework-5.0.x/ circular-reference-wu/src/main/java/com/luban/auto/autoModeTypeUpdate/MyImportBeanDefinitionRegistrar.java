package com.luban.auto.autoModeTypeUpdate;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
/**
 * 参考--https://juejin.im/post/5d99fcc7e51d457822796ea1
 * 自定义 public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar
 * 干预xx
 * 需要在Appconfig上增加如下注解 @Import(MyImportBeanDefinitionRegistrar.class)
 *
 * */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

		GenericBeanDefinition autoModeServiceUpdateToByType = (GenericBeanDefinition) registry.getBeanDefinition("autoModeServiceUpdateToByType");
		GenericBeanDefinition autoModeServiceUpdateToByName = (GenericBeanDefinition) registry.getBeanDefinition("autoModeServiceUpdateToByName");
		GenericBeanDefinition autoModeServiceUpdateToByConstor = (GenericBeanDefinition) registry.getBeanDefinition("autoModeServiceUpdateToByConstor");


		autoModeServiceUpdateToByType.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
		autoModeServiceUpdateToByName.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME);
		autoModeServiceUpdateToByConstor.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);
	}


}
