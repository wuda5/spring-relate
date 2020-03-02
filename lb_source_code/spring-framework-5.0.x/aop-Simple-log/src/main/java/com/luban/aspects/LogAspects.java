package com.luban.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Aspect 此注解告诉spring这是切面
 *
 *
 * 3】、定义一个日志切面类LogAspects.java
 * 切面类里面的方法需要动态感知MathCalculator.div运行到哪里，然后执行相应的通知方法
 * 给切面类的目标方法标注何时何地运行(即以下通知注解)
 * LogAspects 里面有通知方法：
 * 1）前置通知：(@Before) logStart:在目标方法div运行之前
 * 2）后置通知：(@Aftre) logEnd:在目标方法div运行结束之后 (无论方法正常结束还是异常结束)
 * 3）返回通知：(@AfterReturning) logReturn:在目标方法div正常返回之后运行
 * 4）异常通知：(@AfterThrowing) logException:在目标方法div出现异常之后运行
 * 5）环绕通知：(@Around) 动态代理，手动推进目标方法运行（joinPoint.proceed()）
 *
 */
@Aspect
@Component
public class LogAspects {

	/**
	 * 抽取公共的切入点表达式-- 切点名字：commonPointCut
	 * 1、如果在本类引用     直接在注解加方法名  如@Before(commonPointCut())
	 * 2、如果不是在本类引用 （比如其他的切面要引用这个切入点）  则要用全类名
	 */
	@Pointcut("execution(public int com.luban.service.MathCalculator.div(int, int))")
	public void commonPointCut() {

	}

	//@Before在目标方法之前切入；切入点表达式（指定在哪个方法切入）
	//@Before("public int aop.annotation.service.MathCalculator.div(int, int)")
	/**
	 * @param joinPoint 可以通过此参数获取方法相关信息 如方法名和参数信息，此参数必须放在第一个参数
	 */
	@Before("commonPointCut()")
	public void logStart(JoinPoint joinPoint) {
		System.out.println("【1.】" + joinPoint.getSignature().getName() + "除法运行，@Before,参数列表是：{" + Arrays.asList(joinPoint.getArgs()) + "}");
	}


	@After("commonPointCut()")
	public void logEnd(JoinPoint joinPoint) {
		System.out.println("【3.】除法结束,@After");
	}

	/**
	 * 用returning来接收返回值
	 *
	 * @param resultTest
	 */
	@AfterReturning(value = "commonPointCut()", returning = "resultTest")
	public void logReturn(Object resultTest) {
		System.out.println("【4.】除法正常返回，@AfterReturning 运行结果：{" + resultTest + "}");
	}

	/**
	 * 用throwing来接收异常
	 *
	 * @param joinPoint 此参数必须放在第一位
	 * @param exception
	 */
	@AfterThrowing(value = "commonPointCut()", throwing = "exception")
	public void logException(JoinPoint joinPoint, Exception exception) {
		System.out.println("【5.】" + joinPoint.getSignature().getName() + "除法异常，@AfterException 异常信息：{" + exception.getStackTrace() + "}");
	}
}
