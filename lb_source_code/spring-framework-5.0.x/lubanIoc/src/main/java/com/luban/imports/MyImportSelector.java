package com.luban.imports;

import com.luban.anno.EanbleLuabn;
import com.luban.app.Appconfig;
import com.luban.dao.IndexDao3;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.util.MultiValueMap;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Set;

/**
 * ---相当于我在另一个mybatis-log中写的 LubanREgistatrar 类--类似作用：---不同之处是xxstartar 是可以直接拿到注册器，xxx---
 * 在源码解析4 中涉及--想要被调用起作用--还必须在配置类上：@Import({MyImportSelector.class})
 *
 * MyImportSelector 是不需要放入spring(相当于一个中间起作用的插件而已，主要是为了想导入其他第三方的bean的bd到 spring 的) 中，只需要在appconfig 中 @import(MyImportSelector.calss),
 * 之后就可以在test 中从spring 中拿到它了：ioc.getBean(IndexDao3.class).query();
 * IndexDao3 其实不需要任何额外的xx,只是自己实现了xxx
 * -- 这种写法看似繁重 没必要，但是 当我们需要动态的想要向spring 容器中加入对象的时候，就可以考虑采用这种方式，、
 * --- spring-aop, mybtis 的实现就是xxxx
 *
 * --能够被spring 用这种实现 了 ImportSelector 的方式 做xxx的 !!!!!!!!根本原因是：
 * -- 在解析扫描时，ConfigurationClassParser 中， for 循环中，此时的candidate 就是对应当前 appconfig  类,因为他的上面标注@Import(MyImportSelector.class)(即使是用的另一方式@EanbleLuban也是间接的xx)
 * 有句 if (candidate.isAsasignabel(ImportSelectror.class))
 * --> 就执行 Class<?> candidateClass = candidate.loadClass(); --> 反射 生成对象 ImportSerlectr selector,
 * --》 后 通过这个 selector 直接 执行他的方法 即（selectImports）对应调用的源码位置可以打断点在当前selectImports 上，然后观察调用栈，发现
 * 所调用的 方法时  selector.selectImports(xxx)
 * 就可以拿到它所返回的字符串即：new String[]{IndexDao3.class.getName()};
 * --> 后，再有此字符串来 生成 bd.....
 *
 * 此类被调用的位置是：ConfigurationClassParser 的 processImports(ConfigurationClass configClass, SourceClass currentSourceClass,
 * 			Collection<SourceClass> importCandidates, boolean checkForCircularImports) 方法
 * **/
public class MyImportSelector implements ImportSelector {
	private Annotation at;

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		System.out.println("++++++++++ MyImportSelector-->ImportSelector  ++++++++ 被调用");

		/***这里返回的是类名，提供参数列表是数组（new String[]{IndexDao3.class.getName()};）：即可以把自己想要代理等操作的bean 类 都写在这里！！！！！
		 *
		 * 这么做的原因：因为我们最终IndexDao3 是可以从spring 拿到，即会完成从 生成 bd对象-->单例池map
		 * 所以，这里就是要返回一个 name,-->让后面可以通过 name 类名去 生成 bd-->到xxx
		 *
		 * 因为spring 要生成bd对象的方法是：new beandifitionxx(类名)
		 * */

		/** --如何动态IndexDao3随时可配--实现开关闭某些功能？--参照解析四--1：28，
		* 1. 先自己定义一个注解如 @EanbleProxy 并在这个注解的上面在标注@Import(MyImportSelector.class) ，再设定一个boolean值方法valeu
		* 2. 然后，在appconfig上面就可以不要直接配置@import
		 * 3. 在当前此种，通过 importingClassMetadata 获取到 appconifig 中的 @EanbleProxy 中自己的接口属性的的注解值，
		 * 这样我就能动态的切换了：--- 如果是true才返回动态代理即 IndexDao3 才生效，false啥都不做
		 * */
		StandardAnnotationMetadata imd = (StandardAnnotationMetadata) importingClassMetadata;
		Set<String> annotationTypes = imd.getAnnotationTypes();

		Annotation[] annotations = imd.getAnnotations();// 这个方法是我加到spring的！！！！！！！
		// 判断一个注解属性哪种类型？--spring 好像由工具类--
//		AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EanbleLuabn.class.getName()));
//		annoAttrs.getStringArray("basePackages")
		for (Annotation at : annotations) {
			if(at.annotationType().equals(EanbleLuabn.class)){
				// 如何获得Appconfig的class??
				Class<?> appconfig = ((StandardAnnotationMetadata) importingClassMetadata).getIntrospectedClass();
				EanbleLuabn e = appconfig.getAnnotation(EanbleLuabn.class);

				boolean value1 = e.value();
				if(e.value()){
					// 返回想要操作被后面增强代理的类名：IndexDao3.class.getName()
					/**这里如果真的想实现spring-aop类似，IndexDao也不要写死，传来个包名，解析包名满足条件的bean类然后再传到这里来！！！*/
					return new String[]{IndexDao3.class.getName()};
				}
			}
		}


//		return new String[]{IndexDao3.class.getName()};
//		return null;
		return new String[0];// 直接返回null 要报错！！！！！
	}
}
