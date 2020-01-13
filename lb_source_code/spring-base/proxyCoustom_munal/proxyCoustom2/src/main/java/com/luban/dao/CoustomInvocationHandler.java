package com.luban.dao;

import java.lang.reflect.Method;
//模拟jdk,只是简化，接口方法中jdk定义的proxy对象
//jdk:  public Object invoke(Object proxy, Method method, Object[] args)
// throws Throwable {
public interface CoustomInvocationHandler {
    public Object invoke(Method method);
}
