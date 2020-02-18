package com.luban.DI;

import com.luban.service.LuBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * 构造 + setter 混合使用
 * setter会覆盖构造
 * 理解：大胆猜测下，Spring虽然能在构造函数里完成属性注入，但是这属于实例化对象阶段做的事情，
 * 那么在后面真正进行属性注入的时候，肯定会将其覆盖掉
 * */
@Component
public class ConstorAndSetterService {

	private LuBanService luBanService;

	//--1.先构造 赋值luBanService
	public ConstorAndSetterService(LuBanService luBanService) {
		System.out.println("【 CS.1 】注入luBanService by constructor with arg--1--ON--");
		this.luBanService = luBanService;
		System.out.println("【 CS.1 】service create by constructor with arg--2 --OK--【完成了实例化！】此时属性 luBanService为 【"+luBanService+"】");
	}
    // 2.后setter覆盖属性luBanService
	@Autowired
	public void setLuBanService(LuBanService luBanService) {
		System.out.println("【 CS.2 】注入luBanService by setter---覆盖--constor--1.ON--此时属性 luBanService为 【"+luBanService+"】");
		this.luBanService = null;
		System.out.println("【 CS.2 】注入luBanService by setter---覆盖--constor--2.OVER【 属性注入时将实例化时注入的属性进行了覆盖】--此时属性 luBanService为 【"+this.luBanService+"】");
	}

	public void test(){
		System.out.println(luBanService);
	}

}
