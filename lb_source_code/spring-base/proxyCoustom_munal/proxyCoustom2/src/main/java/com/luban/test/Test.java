package com.luban.test;

import com.luban.dao.LubanDao;
import com.luban.dao.LubanDaoImpl;
import com.luban.proxy.ProxyUtil;
import com.luban.util.LubanInvocationHandler;
import com.luban.util.TestCustomHandler;
import sun.misc.ProxyGenerator;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicInteger;


public class Test {
    public static void main(String[] args) {
//          //自定义
        LubanDao proxy = (LubanDao) ProxyUtil.newInstance(LubanDao.class,new TestCustomHandler(new LubanDaoImpl()));
        try {
            proxy.proxy();
        } catch (Exception e) {
            e.printStackTrace();
        }

//      拿出jdk动态代理中反射得到字节码对象对比
        System.out.println("+++++++++jdk+++++++++++");
        byte[] bytes=ProxyGenerator.generateProxyClass("$Proxy18",new Class[]{LubanDao.class});

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("d:\\$Proxy18.class");
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//                System.out.println(proxy.proxy());

        //--jdk-----
//        testJdkProxy();
    }

    /**
    * jdk动态代理测试
     * Proxy.newProxyInstance中参数newProxyInstance(ClassLoader loader,
     *                                           Class<?>[] interfaces,
     *                                           InvocationHandler h)说明：
     * 1.ClassLoader loader类加载器，
     * 2.Class<?>[] interfaces,所要代理的接口target，
     *
     * 3.InvocationHandler --接口中定义方法：public Object invoke(Object proxy, Method method, Object[] args)
     * 其中第三个参数args即为：所指明的目标对象具体实现target （即想要代理的），此参数数组原因是：一次可以实现对多个对象的代理！（只是自己只代理了一个）
     *
     * 下面是对自己传入上面的第三个参数new LubanInvocationHandler(new LubanDaoImpl()) （InvocationHandler的实现）--的说明：
     * new LubanInvocationHandler(new LubanDaoImpl())指明所要代理具体的类，其具体代理的方法实现在LubanInvocationHandler中
     *  通过 method.invoke(target,args)执行目标对象的自身方法逻辑，
     *  在此方法的--前后--执行完成所想要代理的逻辑--即本例中， System.out.println("LubanInvocationHandler jdk");
     *     其中method看作为目标对象（LubanDaoImpl方法对象）
     *
    * */
    public static void testJdkProxy() {
        LubanDao jdkproxy = (LubanDao) Proxy.newProxyInstance(
                Test.class.getClassLoader(),
                new Class[]{LubanDao.class},
                new LubanInvocationHandler(new LubanDaoImpl()));

        //jdkproxy.query();
//        new AtomicInteger(3).addAndGet(1);
        try {
            jdkproxy.proxy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
