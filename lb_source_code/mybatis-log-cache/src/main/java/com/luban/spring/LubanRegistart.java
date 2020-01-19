package com.luban.spring;

import com.luban.FactoryBean.LubanFactoryBean;
import com.luban.ann.MineMapperScan;
import com.luban.utils.MapperScanUtil;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

public class LubanRegistart implements ImportBeanDefinitionRegistrar {

    public LubanRegistart() {
        System.out.println("++++ implements ImportBeanDefinitionRegistrar ++++ 执行构造+++");
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        /***通过spring自己的方法，获取到
         * appconfig上想要的注解信息(即EnableLuban)，后面再拿此注解的所给定的属性(属性value)获取到其值如：“com.luban.dao”，后通过拿到的值扫描 mapper ,
         * */
		AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(MineMapperScan.class.getName()));
        String[] basePackages = annoAttrs.getStringArray("value");//--应该用xx获取到xx com.luban.dao

        Class[] classes = MapperScanUtil.scanMappers(basePackages[0]).toArray(new Class[]{});
        for (Class clazz : classes) {
            
            /**构建生产一个bd原料 bd */
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            GenericBeanDefinition bd = (GenericBeanDefinition) builder.getBeanDefinition();
            /**构建bd的type类型 factoryBean--> 指向后面可以代理处理接口生产mapper接口代理对象  */
            bd.setBeanClass(LubanFactoryBean.class);
            String beanClassName = bd.getBeanClassName();
//            bd.getConstructorArgumentValues().addGenericArgumentValue("com.luban.dao.CityMapper");
            /**给 factoryBean 中 的一个属性 注入 对应后面所要代理的 mapper 接口对象！！【非常重要】,-->"com.luban.dao.CityMapper"*/
            bd.getConstructorArgumentValues().addGenericArgumentValue(clazz.getName());
            /**注册bd*/
            registry.registerBeanDefinition(clazz.getSimpleName(),bd);//--
            
        }


    }


//    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

//        /**构建生产一个bd原料 bd */
//        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(CityMapper.class);
//
////      GenericBeanDefinition bd = builder.getBeanDefinition();
//        GenericBeanDefinition bd = (GenericBeanDefinition) builder.getBeanDefinition();
//        bd.setBeanClass(LubanFactoryBean.class);/**让其beanType是xx**/
//        /**给factoryBean中 的一个属性 注入值！！,这个值就该是扫描出的不写死-->
//         * 可以通过 bd.getBeanClassName()
//         * */
//        bd.getConstructorArgumentValues().addGenericArgumentValue("com.luban.dao.CityMapper");
//        //注册bd
//        registry.registerBeanDefinition("cityMapper",bd);
//        registry.registerBeanDefinition("cityMapper2",bd);
//
//    }
}
