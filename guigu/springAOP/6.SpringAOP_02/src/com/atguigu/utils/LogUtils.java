package com.atguigu.utils;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 如何将这个类（切面类）中的这些方法（通知方法）动态的在目标方法运行的各个位置切入
 * @author lfy
 *
 */
@Aspect
@Component
public class LogUtils {
	
	/**
	 * 告诉Spring每个方法都什么时候运行；
	 * try{
	 * 		@Before
	 * 		method.invoke(obj,args);
	 * 		@AfterReturning
	 * }catch(e){
	 * 		@AfterThrowing
	 * }finally{
	 * 		@After
	 * }
	 * 	
	 * 5个通知注解
	 * @Before：在目标方法之前运行；  					 前置通知
	 * @After：在目标方法结束之后						后置通知
	 * @AfterReturning：在目标方法正常返回之后			返回通知
	 * @AfterThrowing：在目标方法抛出异常之后运行			异常通知
	 * @Around：环绕								环绕通知
	 * 
	 */
	
	//想在执行目标方法之前运行；写切入点表达式
	//execution(访问权限符  返回值类型  方法签名)
	@Before("execution(public int com.atguigu.impl.MyMathCalculator.*(..))")
	public static void logStart(JoinPoint joinPoint){
		//获取到目标方法运行是使用的参数
		Object[] args = joinPoint.getArgs();
		//获取到方法签名
		Signature signature = joinPoint.getSignature();
		String name = signature.getName();
		System.out.println("【"+name+"】方法开始执行，用的参数列表【"+Arrays.asList(args)+"】");
	}
	
	/**
	 * 切入点表达式的写法；
	 * 固定格式： execution(访问权限符  返回值类型  方法全类名(参数表))
	 *   
	 * 通配符：
	 * 		*：
	 * 			1）匹配一个或者多个字符:execution(public int com.atguigu.impl.MyMath*r.*(int, int))
	 * 			2）匹配任意一个参数：第一个是int类型，第二个参数任意类型；（匹配两个参数）
	 * 				execution(public int com.atguigu.impl.MyMath*.*(int, *))
	 * 			3）只能匹配一层路径
	 * 			4）权限位置*不能；权限位置不写就行；public【可选的】
	 * 		..：
	 * 			1）匹配任意多个参数，任意类型参数
	 * 			2）匹配任意多层路径:
	 * 				execution(public int com.atguigu..MyMath*.*(..));
	 * 
	 * 记住两种；
	 * 最精确的：execution(public int com.atguigu.impl.MyMathCalculator.add(int,int))
	 * 最模糊的：execution(* *.*(..))：千万别写；
	 * 
	 * &&”、“||”、“!
	 * 
	 * &&：我们要切入的位置满足这两个表达式
	 * 	MyMathCalculator.add(int,double)
	 * execution(public int com.atguigu..MyMath*.*(..))&&execution(* *.*(int,int))
	 * 
	 * 
	 * ||:满足任意一个表达式即可
	 * execution(public int com.atguigu..MyMath*.*(..))&&execution(* *.*(int,int))
	 * 
	 * !：只要不是这个位置都切入
	 * !execution(public int com.atguigu..MyMath*.*(..))
	 * 
	 * 告诉Spring这个result用来接收返回值：
	 * 	returning="result"；
	 */
	//想在目标方法正常执行完成之后执行
	@AfterReturning(value="execution(public int com.atguigu..MyMath*.*(..))",returning="result")
	public static void logReturn(JoinPoint joinPoint,Object result){
		Signature signature = joinPoint.getSignature();
		String name = signature.getName();
		System.out.println("【"+name+"】方法正常执行完成，计算结果是："+result);
	}

	
	/**
	 * 细节四：我们可以在通知方法运行的时候，拿到目标方法的详细信息；
	 * 1）只需要为通知方法的参数列表上写一个参数：
	 * 		JoinPoint joinPoint：封装了当前目标方法的详细信息
	 * 2）、告诉Spring哪个参数是用来接收异常
	 * 		throwing="exception"：告诉Spring哪个参数是用来接收异常
	 * 3）、Exception exception:指定通知方法可以接收哪些异常
	 * 
	 * ajax接受服务器数据
	 * 	$.post(url,function(abc){
	 * 		alert(abc)
	 * 	})
	 */
	//想在目标方法出现异常的时候执行
	@AfterThrowing(value="execution(public int com.atguigu.impl.MyMathCalculator.*(..))",throwing="exception")
	public static void logException(JoinPoint joinPoint,Exception exception) {
		System.out.println("【"+joinPoint.getSignature().getName()+"】方法执行出现异常了，异常信息是【"+exception+"】：；这个异常已经通知测试小组进行排查");
	}

	//想在目标方法结束的时候执行
	/**
	 * Spring对通知方法的要求不严格；
	 * 唯一要求的就是方法的参数列表一定不能乱写？
	 * 	通知方法是Spring利用反射调用的，每次方法调用得确定这个方法的参数表的值；
	 * 	参数表上的每一个参数，Spring都得知道是什么？
	 * 	JoinPoint:认识
	 * 	不知道的参数一定告诉Spring这是什么？
	 * 
	 * @param joinPoint
	 */
	@After("execution(public int com.atguigu.impl.MyMathCalculator.*(..))")
	private int logEnd(JoinPoint joinPoint) {
		System.out.println("【"+joinPoint.getSignature().getName()+"】方法最终结束了");
		return 0;
	}

}
