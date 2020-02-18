package com.luban.DI;

import com.luban.service.LuBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * setter 注入
 * **/
@Component
public class SetterService {

	private LuBanService lubanService;

	public SetterService() {
		System.out.println("service create");
	}

	public void test(){
		System.out.println("【 Sertter.3 】--test--取值"+lubanService);
	}
	/**通过autowired指定使用set方法完成注入，如果此方法不标注 @Autowired 是不会执行的！！！！
	 * 2. 或者直接不写此方法，直接在自段属性上面 标注注解 @autowired--这样会自动完成装配--它是利用【自动装配--模型 NO不是byType?? ！】技术，有它就不用写sertter 方法,--》最好是 Field.setxx
	 *
	 *  * 1、@Autowired 可以通过 1.类型来找对应的类，如果通过类型找不到就通过 2.名字来找，如果还是找不到就会排除异常。
	 *  * 2、虽然使用了@Autowired注解，但装配模型依然还是AUTOWIRE_NO，并未看到有--改变装配模型的源码。这也是spring--默认的装配模型--NO
	 *  * 网上看到说使用 @Autowired 就是通过类型来装配的, 在我理解看来，这种说法是不对的--
	 *
	 *  --所以当前的这种使用也是 NO 模型
	 * **/
	@Autowired
	public void setLuBanServicexx(LuBanService luBanService) {
		System.out.println("【 Setter.3--@Autowired所标注的sett方法执行-- 】注入luBanService by setter, 此时拿到的 lubanService 值是 【"+luBanService+"】");
		this.lubanService = luBanService;
	}
}