package com.luban.app;

import com.luban.service.IndexService;
import com.luban.service.OrderService;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
//@ComponentScan("com.luban")
@EnableAspectJAutoProxy
/** 加了他，会利用import 注册一个aop 的后置处理器 AnnotationAutoProxyCreator，
 * 再完成所有的注册xx后，在注册后置处理器过程完成此 后置处理器的生命，后
 * 在对其他所有的bean 3类，的实例化生命时，都会作用调用到此后置处理器的方法 afterInitxxx **/
//@ImportResource("classpath:spring.xml")
public class Appconfig {



}
