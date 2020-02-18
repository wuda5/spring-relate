package com.luban.auto;

import com.luban.service.LuBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * auto --No--注入--	@Resource --也是 no--其实感觉和 @autoWIred有点相反
 * 1、@Autowired 可以通过 1.类型来找对应的类，如果通过类型找不到就通过 2.名字来找，如果还是找不到就会排除异常。
 * 2、虽然使用了@Autowired注解，但装配模型依然还是AUTOWIRE_NO，并未看到有--改变装配模型的源码。这也是spring--默认的装配模型--NO
 * 网上看到说使用 @Autowired 就是通过类型来装配的, 在我理解看来，这种说法是不对的--
 *  *
 * 在开发中使用最常见的应该就是通过@Autowired注解来完成注入的。这里有个常见的误区，网上看到说使用@Autowired就是通过类型来装配的
 * 在我理解看来，这种说法是不对的。原因有两点：
 *
 * @Resource用法与@Autowired 用法 用法相似，也是做依赖注入的，从容器中自动获取bean。但还是有一定的区别。
 * 启动spring容器时，会默认寻找容器扫描范围内的可加载bean，然后查找哪些bean上的属性和方法上有@Resource注解；
 * 找到@Resource注解后，.判断@Resource注解括号中的name属性是否为空，
 * 1..如果为空：看spring容器中的bean的id与@Resource要注解的那个【变量属性名】是否相同，如相同，匹配成功；
 * 		如果不相同，看spring容器中bean的id【对应的类型】是否与@Resource要注解的那个变量属性对应的类型是否相等，若相等，匹配成功，若不相等，匹配失败。
 * 2.如果@Resource注解括号中的name属性不为空，看name的属性值和容器中的bean的id名是否相等，如相等，则匹配成功；如不相等，则匹配失败。

 *
 * **/
@Component
public class AutoServiceNoByResouce {

	/*从容器中取id名字为 luBanService 的bean，如果找不到该bean，spring启动过程中就会报错，
	表示把Man类型的bean注入到容器中不成功，因为 autoServiceByResouce 的属性依赖注入的时候就出错了，
	所以创建Man的bean的时候肯定不成功。*/
	@Resource(name="luBanService")// 可以不加属性-->
	private LuBanService lubanService;

	public void test(){
		System.out.println();
		System.out.println();
		System.out.println("【 Auto.@Resouce 】--test--取值"+lubanService);
	}

}