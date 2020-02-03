package com.luban.app;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AspectLuban {

	/**注意 * 符号后面要必须有一个空格，否则报错 **/
	@Pointcut("execution(* com.luban.dao..*.*(..))")
	public void pointCut(){
		System.out.println("^^^^^^^^^^^^^^^^^ aspect aop pointCut ^^^^^^^^^^^^^^^^^^^^^");
	}

	@Before("pointCut()")
	public void before(){

		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^aspectj proxy before  通知^^^^^^^^^^^^^^^^^^^^^^^^^");
	}
}
