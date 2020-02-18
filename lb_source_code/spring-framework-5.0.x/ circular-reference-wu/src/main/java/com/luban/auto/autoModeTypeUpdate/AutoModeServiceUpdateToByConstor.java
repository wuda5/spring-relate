package com.luban.auto.autoModeTypeUpdate;

import com.luban.service.LuBanService;
import org.springframework.stereotype.Component;

/**
 * 参考--https://juejin.im/post/5d99fcc7e51d457822796ea1
 *
 * 要说明的是，通过类型转配【将默认NO--> 改为了 AUTOWIRE_CONSTRUCTOR】，--> 这样其实就能达到用xxx注解方式开发替换 xml中单个xxxs设置
 * 和提供的set方法名字没有关系，和属性名字也不会有关系
 *
 * 即 经过扩展改变了此bean的 装配类型模式后，就可以把此类的注入 当作完全的自动装配是 byName 方式一样看待，
 * 即它需要提供：
 * 1. 想装配属性类型 LuBanService（属性名可以随意）
 *    -- 通过名称装配，需要根据setter的名字来查找对应的类，所有需要修改UserService中的set方法名
 * 2. 提供settXXX 方法，(方法名只能是set+属性类名，否则无法注入！！)
 *     方法参数必须只能一个且参数类型 必须是 LuBanService（参数名随意），
 *
 *
 * */
@Component
public class AutoModeServiceUpdateToByConstor {

	private LuBanService LuBanService111;

	AutoModeServiceUpdateToByConstor(LuBanService orderService434){
		this.LuBanService111 = orderService434;
	}

////	public void setOrderService(LuBanService orderService434) { --- 名字写成其他，注入不进去！！
//	public void setLuBanService(LuBanService orderService434) {
//		this.LuBanService111 = orderService434;
//	}
//
	public void test() {
		System.out.println("【 Auto.3.[byConstor]--update 】--test--取值"+LuBanService111);
	}
}
