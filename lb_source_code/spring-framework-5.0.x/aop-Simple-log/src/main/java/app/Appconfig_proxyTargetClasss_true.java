package app;


//import com.luban.imports.MyImportSelector;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 4】、将切面类和业务逻辑类加入到容器中 MainConfigOfAOP.java
 * 告诉spring哪个是切面类，(在切面类LogAspects加一个注解@Aspect)
 * 在配置类中加 @EnableAspectJAutoProxy 开启基于注解的aop模式
 * */
@ComponentScan("com.luban")
@Configuration
//proxyTargetClass = true -- 代表使用 cglib 代理，--
// 默认采用的是jdk 代理，但也只会代理是接口的，普通的类有内部判断非接口时会设置 proxyFactory.setProxyTargetClass(true);后使用 cglib代理！！
@EnableAspectJAutoProxy(proxyTargetClass = true)
//@EnableAspectJAutoProxy(proxyTargetClass = false)
public class Appconfig_proxyTargetClasss_true {
	public Appconfig_proxyTargetClasss_true() {

		System.out.println("+++++++ [4.] Appconfig 特殊的配置类@Configuration（用包扫描）的构造器 ++++++");
	}


}
