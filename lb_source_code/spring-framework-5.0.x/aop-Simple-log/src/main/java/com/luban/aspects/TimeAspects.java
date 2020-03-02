package com.luban.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Aspect 此注解告诉spring这是切面
 *
 *采用注解来确定切点！
 */
@Aspect
@Component
public class TimeAspects {

//	@Autowired
//	private HttpServletRequest httpServletRequest; //还可以注入一些bean,方便xxx

	/**
	 * 抽取公共的切入点表达式-- 切点名字：commonPointCut
	 * 1、如果在本类引用     直接在注解加方法名  如@Before(commonPointCut())
	 * 2、如果不是在本类引用 （比如其他的切面要引用这个切入点）  则要用全类名
	 * @annotation 表示拦截含有这个【注解】的方法
	 */
	@Pointcut("@annotation(com.luban.aspects.Annotation.TimeAccord)")
	public void timeCommonPointCut() {

	}

	//@Before在目标方法之前切入；切入点表达式（指定在哪个方法切入）
	//@Before("public int aop.annotation.service.MathCalculator.div(int, int)")
	/**
	 * @param joinPoint 可以通过此参数获取方法相关信息 如方法名和参数信息，此参数必须放在第一个参数
	 */
	@Before("timeCommonPointCut()")
	public void timeStart(JoinPoint joinPoint) {
		System.out.println("【1.TimeAspects】" + joinPoint.getSignature().getName() + "除法运行，@Before,参数列表是：{" + Arrays.asList(joinPoint.getArgs()) + "}");
	}


	@After("timeCommonPointCut()")
	public void timeEnd(JoinPoint joinPoint) {
		System.out.println("【3.TimeAspects】除法结束,@After");
	}

	/**
	 * 用returning来接收返回值
	 *
	 * @param resultTest
	 */
	@AfterReturning(value = "timeCommonPointCut()", returning = "resultTest")
	public void timeReturn(Object resultTest) {
		System.out.println("【4.TimeAspects】除法正常返回，@AfterReturning 运行结果：{" + resultTest + "}");
	}

	/**
	 * 用throwing来接收异常
	 *
	 * @param joinPoint 此参数必须放在第一位
	 * @param exception
	 */
	@AfterThrowing(value = "timeCommonPointCut()", throwing = "exception")
	public void timeException(JoinPoint joinPoint, Exception exception) {
		System.out.println("【5.TimeAspects】" + joinPoint.getSignature().getName() + "除法异常，@AfterException 异常信息：{" + exception.getStackTrace() + "}");
	}
}
