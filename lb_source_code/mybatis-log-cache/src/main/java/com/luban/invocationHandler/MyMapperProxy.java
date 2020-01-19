package com.luban.invocationHandler;

import org.apache.ibatis.annotations.Select;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MyMapperProxy implements InvocationHandler {

//    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("++++ zhi xi sql +++++");
        String select =  method.getDeclaredAnnotation(Select.class).value()[0];

        System.out.println("执行代理对象--解析【接口方法】上面的注解sql信息："+select);
        //--对拿到的sql--进行数据库操作，返回数据---

        return new ArrayList<Object>();
//        return method.invoke(proxy，args);
    }
}
