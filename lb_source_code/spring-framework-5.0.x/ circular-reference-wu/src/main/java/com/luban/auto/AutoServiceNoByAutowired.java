package com.luban.auto;

import com.luban.service.LuBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * auto --no --注入--@autoWired--其实和 @Resouce 有点相反的感觉
 * 1、@Autowired 可以通过 类型来找对应的类，如果通过类型找不到就通过
 * 2.名字来找，如果还是找不到就会排除异常。
 *
 * 3、虽然使用了@Autowired注解，但装配模型依然还是AUTOWIRE_NO，并未看到有--改变装配模型的源码。这也是spring--默认的装配模型--NO
 * 网上看到说使用 @Autowired 就是通过类型来装配的, 在我理解看来，这种说法是不对的--
 *  *
 * 在开发中使用最常见的应该就是通过@Autowired注解来完成注入的。这里有个常见的误区，网上看到说使用@Autowired就是通过类型来装配的
 * 在我理解看来，这种说法是不对的。原因有两点：
 *
 * **/
@Component
public class AutoServiceNoByAutowired {

	@Autowired//无法加入属性--说明默认no ????
	private LuBanService lubanService;

	public void test(){
		System.out.println("【 Auto.no 】--test--取值"+lubanService);
	}

}