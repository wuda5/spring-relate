package com.luban.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LubanInvocationHandler implements InvocationHandler {
    Object target;
    //这个proxy是自己加的，其实可以不要（因为其实感觉是没有意义的，代理对象本来就是要生产出来的，在初始化LubanInvocationHandler的时候
    // 根本就还不存在proxy或为null，无法初始赋值的，？不明白jdk中要传入proxy干嘛？）
    Object proxy;
    public LubanInvocationHandler(Object target){
        this.target=target;
    }

    // 这个是自己加的，其实可以不要
    public LubanInvocationHandler(Object proxy,Object target){
        this.proxy =proxy;
        this.target=target;
    }
    /**
     *
     * @param proxy 代理对象
     * @param method 目标对象
     * @param args    目标方法的参数
     * @return
     * @throws Throwable
     *
     *
     * proxy logic execute
     * target logic execute
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("LubanInvocationHandler jdk");
        return method.invoke(target,args);
    }
}
