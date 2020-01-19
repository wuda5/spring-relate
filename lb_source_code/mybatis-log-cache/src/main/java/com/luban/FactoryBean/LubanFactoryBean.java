package com.luban.FactoryBean;

import com.luban.invocationHandler.MyMapperProxy;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

/**主要完成mapper的代理
 *
 * 为啥不用继承BeanPostProcessor 后置处理器 去搞呢？？
 * --难道是因为用后置处理器是会作用到所有的bean上面，必须要进行选择判断所需要的bean来进行处理？
 * 而用 实现 FactoryBean的用法，直接在 LubanRegistart 中，就表明了xxx*/
@Component("luban")
public class LubanFactoryBean implements FactoryBean {


    private static MyMapperProxy handler = new MyMapperProxy();
    /**给 factoryBean 中 的一个属性 注入 对应后面所要代理的 mapper 接口对象！！【非常重要】,
     *  在 实现了xx的 LubanRegistart 中bd.getConstructorArgumentValues().addGenericArgumentValue(clazz.getName())
     * -->添加类名，内部会自动完成-->"com.luban.dao.CityMapper"*/
    private Class mapperInterface;
    /**mapperInterface 这个Class 到底能如何注入？？它是一个class不是在spring容器的，
     * --> 在xxregistrar 中bd.getCOnstructorArgumentValuesl()*/
    public LubanFactoryBean(Class mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public LubanFactoryBean() {
        /**不重要*/
    }

    @Override
    public Object getObject() throws Exception {
        //得到代理对象---注意此方法在容器完成阶段不会被调用，只有当执行到ioc.getBean到这里
        //ioc
        Class[] clazzs=  new Class[]{mapperInterface};
//        CityMapper o = (CityMapper) Proxy.newProxyInstance(this.getClass().getClassLoader(),clazzs,handler);
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),clazzs,handler);
    }



 /**这里不用漏了**/
    @Override
    public Class<?> getObjectType() {
        return mapperInterface;
//        return SpringBean.class;
    }

    @Override
    public boolean isSingleton(){
        return true;
    }
}
