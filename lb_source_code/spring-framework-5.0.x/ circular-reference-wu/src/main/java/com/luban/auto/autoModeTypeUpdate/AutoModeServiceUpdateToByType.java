package com.luban.auto.autoModeTypeUpdate;

import com.luban.service.LuBanService;
import org.springframework.stereotype.Component;

/**
 * 参考--https://juejin.im/post/5d99fcc7e51d457822796ea1
 *
 * 注意：这样用自动装配的好处--> 可以不用spring 的提供注解 @Autowired, 减少了于spring的耦合，为外部xxx 扩展带来可能！
 * 如：mybis-spring结合是就算通过 bytype,--参考read.me
 *
 * 要说明的是，通过类型转配【将默认NO--> 改为了 AUTOWIRE_BY_TYPE 】，--> 这样其实就能达到用xxx注解方式开发替换 xml中单个xxxs设置
 * 和提供的set方法名字没有关系，和属性名字也不会有关系
 *
 * 即 经过扩展改变了此bean的 装配类型模式后，就可以把此类的注入 当作完全的自动装配是 byTpye 方式一样看待，
 * 即它需要提供：
 * 1. 想装配属性类型 LuBanService（属性名可以随意）
 * 2. 提供settXXX （不能是set名，其他任意名字都可以）方法，
 *     方法参数必须只能一个且参数类型 必须是 LuBanService（参数名随意），
 *
 * */
@Component
public class AutoModeServiceUpdateToByType {

	private LuBanService orderService1111;

	public void setXxxx(LuBanService orderService434) {
		this.orderService1111 = orderService434;
	}


	public void test() {
		System.out.println("【 Auto.2.[byType]--update 】--test--取值"+orderService1111);
	}
}
