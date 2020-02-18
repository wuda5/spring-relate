https://juejin.im/post/5d99fcc7e51d457822796ea1

spring种5中装配模型

1、AUTOWIRE_NO = 0；（这个NO咋说呢，理解为不使用自动装配吧，spring默认的值）--默认NO【0】
   这里 NO, 一般使用的 @autoWired ,@Resource 都是属于 默认 No 模式

2、AUTOWIRE_BY_NAME = 1；（通过名字自动装配）        --byName【1】
3、AUTOWIRE_BY_TYPE = 2；（通过类型自动装配）        --byType【2】-- 例子：Mybtis-spring
4、AUTOWIRE_CONSTRUCTOR = 3；（通过构造函数自动装配）--byConstor【3】
5、AUTOWIRE_AUTODETECT = 4；（已经被标注过时，本文不再讨论）--xxx



通过类型装配的案例——Mybatis-spring
上面演示了通过更改装配类型使得不同添加@Autowired注解就可以完成属性的装配，其实在Mybatis-spring中正是这样做的。通过阅读源码，经过以下调用链可以看到Mybatis-spring的做法：
MapperScannerRegistrar#registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry)
    -->MapperScannerRegistrar#registerBeanDefinitions(AnnotationAttributes annoAttrs, BeanDefinitionRegistry registry)
        -->ClassPathMapperScanner#doScan(String... basePackages)
             -->ClassPathMapperScanner#processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions)
processBeanDefinitions(Set beanDefinitions)方法中有如下代码：
if (!explicitFactoryUsed) {
    LOGGER.debug(() -> "Enabling autowire by type for MapperFactoryBean with name '" + holder.getBeanName() + "'.");
    definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

   注意：
    Mybatis-spring通过这种方式，避免了使用@Autowired注解完成自动装配，减少了对spring注解的依赖。
    即--是少使用了spring中注解的作用？？如：@Autowired 整个注解是在 spring-bean 中的，即避免maybits xxxxx不用依赖此项目
    -- 感觉道理精髓了！！！！！！，没有此功能，其他外部框架就无法结合spring??因为它们自身是没有也不想用spring的项目代码
    解耦合！！！
}

