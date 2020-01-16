package com.luban.dao;

//import com.luban.imports.MyInvocationHandler;
import com.luban.imports.MyInvocationHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

/*** 把indexDao3 看作是 【AopBeanPostProcessor】！！！！！！！！！！！！！！！！！！---
 * 注意：他此时不需要加@component，他是被@import 的MyImportSelector 所通过返回的类名反射先生成了对象后，再生成bd,
 * 再再后面经过特殊注册到bdMap中，--再ConfigurationCLassPostProcessor工厂后置处理器processConfigBeanDefinitions方法中调用 this.reader.loadBeanDefinitions(configClasses);
 * 再在后面生成bean 的！！
 *
 * --又由于 IndexDao3 他是一个实现了 BeanPostProcessor后置处理器，他的从bd到完整bean 发生时机是再第一类的BeanFacoryPostProcessor后，但早于其他普通xx（包括FactoryBean类）,
 * 而这里所想要代理的对象就恰好是那个FactoryBean的IndexDao类 对它增强生成代理对象proxy, 但是bdName 还是以前的 indexDao 哦，
 * 即总结：
 * 0.在扫描过程，发生上面写的注意哪些东西，将这个 特殊的后置处理器（这里是来做 对特定bean（实现接口的） 做jdk的 动态代理处理的）先是并未交给spring的，给他 动态的交给 spring了，
 *   所以，我可以叫此 IndexDao3是 自己模拟的一个 spring 的动态代理--可以做aop 类似的功能
 * 1.当此IndexDao3 后置处理器被从bd 到bean后，（它自身一般不会从容器取来用的，只是相当一个中间插手spring中其他bean类生成代理对象工具！！！）
 * 2. 那个目标对象 bean :IndexDao 后也从bd 到beran的工程中具体是 init 前操作时候，会循环执行所有的后置处理器（IndexDao3就是在其中之一），
 * 3.当IndexDao 执行到 IndexDao3 这个处理器的时候，就会到if 的判断成立，然后 生成 代理对象 发生！！，完成对 indexDao 的代理了
 * 4.外部现在从容器去取的对象就只能通过 ：Dao = ioc.getBean("indexDao") 名字取，且必须用代理的接口Dao接收 否则都会报错
 *
*/
public class IndexDao3 implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (beanName.equals("indexDao")){
			System.out.println("############# IndexDao3 -> BeanPostProcessor 且 indexDao3 的bd 生成是由扫描配置类时发现上面的@imoportSelcter（xx） 找到xx上面的返回xx到这里来的，" +
					"最后调用又是在后面目标对象的bean实例化完成，init前 回调所有的后置处理器到这的（目标对象--bean: indexDaobe自己是实现Dao, 后来又是先要实例化此IndexDao3后置处理器" );

			Class x = bean.getClass().getInterfaces()[0];// 这一步也很重要，这样就可不写死了
			bean = Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[]{x},new MyInvocationHandler(bean));
//这里可以学习下jdk 动态代理的 源码实现
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return null;
	}


	public IndexDao3() {
		System.out.println("######## 构造 ###### IndexDao3 后置处理器（被@import动态生成的），作用：来做 指定bean 的代理增强用这里，如：对indexDao 增强");
	}
}
