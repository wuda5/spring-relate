package com.luban.util;

import com.luban.dao.CoustomInvocationHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *2. CoustomInvocationHandler接口时子路模拟jdk中接口：InvocationHandler,的实现
 *
 * TestCustomHandler内部包含了 target目标对象！！，调用invoke时，会有 method.invoke(target);
 * method如何产生呢？：
 * 1.就在自己的工具类proxyUtil中循环接口产生methods中一个method传入，（不行，直接使用method生产java文件运行不行应该用下面！）
 *
 * 2.methodName=所具体代理类中 LubanDaoImpl中的所要代理接口方法proxy();
 *    Method method = Class.forName("com.luban.dao.LubanDao").getDeclaredMethod("proxy");
 *     return (String)h.invoke(method);
 *
 *     1）先通过Method对象拿到所代理接口的方法名称：String methodName =method.getName();，
 *     2）. Class.forName(包名).getDeclaredMethod(方法名)得到 所要method!!
 *
 *    +tab+tab+"Method method = Class.forName(\""+targetInf.getName()+"\").getDeclaredMethod(\""+methodName+"\");"+line
 *      +tab+tab+"return ("+returnTypeName+")h.invoke(method);"+line;
 *
 *执行调用的 method.invoke(target);
 * 所生产的.java文件如下（没有了直接聚合代理对象，而是用TestCustomHandler代替了）：
 * public class $Proxy implements LubanDao{
 * 	private TestCustomHandler h;
 * 	public void query() {
 * 		System.out.println("log");
 * 		h.invoke();
 *    }
 * }
 * */
public class  TestCustomHandler implements CoustomInvocationHandler {
    Object target;
    public TestCustomHandler(Object target){
        this.target=target;
    }

    @Override
    public Object invoke(Method method) {
        try {
            System.out.println("----------------");
            return  method.invoke(target);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
