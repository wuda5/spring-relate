package com.luban.DI;

import com.luban.service.LuBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 构造器注入
 * 对于一些强制的依赖，我们最好使用构造函数注入，对于一些可选依赖我们可以采用setter方法注入
 * Spring团队推荐使用构造函数的方式完成注入。但是对于一些参数过长的构造函数，Spring是不推荐的
 * */
@Component
public class ConstorService {

//	@Autowired // --加他都可以不用加setLuban方法-->那是xml时候写法且是在xml中配置了自动专配的方式的？，这个就直接含xxx
	private LuBanService lubanService;

	public ConstorService() {
		System.out.println("【 C 】+++++++++ ConstorService 构造器++++ create by no args constructor ++++");
	}
	// 通过Autowired指定使用这个构造函数，否则默认会使用--上面的无参构造，注意：要是没有显示有无参构造化，则不加@autowired 那么也会执行下面有参构造
	/** * 1、@Autowired 可以通过 1.类型来找对应的类，如果通过类型找不到就通过 2.名字来找，如果还是找不到就会排除异常。
	 * 2、虽然使用了@Autowired注解，但装配模型依然还是AUTOWIRE_NO，并未看到有--改变装配模型的源码。这也是spring--默认的装配模型--NO
	 * 网上看到说使用 @Autowired 就是通过类型来装配的, 在我理解看来，这种说法是不对的--
	 *
	 * --所以当前的这种使用也是 NO 模型
	 * */
	@Autowired
	public ConstorService(LuBanService lubanService) {
		this.lubanService = lubanService;/** 不加她，并且属性也米有加 @Autowired 那么此属性值会是空null ,加了她--> 其实就是为 4种自动装配之 构造器装配*/
		System.out.println("【 C 】+++++++++ ConstorService 有参数的 构造器++++ indexservice +++参数："+lubanService);
	}

	public void testxx() {
		System.out.println("【 C 】--- 测试--ConstorService 内部属性对象luban:["+lubanService);
	}


}
