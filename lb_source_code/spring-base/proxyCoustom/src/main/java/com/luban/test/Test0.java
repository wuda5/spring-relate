package com.luban.test;

import com.luban.dao.UserDao;
import com.luban.dao.UserDaoImpl;
import com.luban.proxy.proxy2.UserDaoLog;
import com.luban.proxy.proxy2.UserDaoTIme;

//静态代理2--聚合方式
//-目标对象和代理对象实现同一个接口，代理对象当中要包含目标对象。
//        缺点：也会产生类爆炸，只不过比继承少一点点
public class Test0 {
    public static void main(String[] args) {

        UserDao target = new UserDaoImpl();
        // 将目标对象target = new UserDaoImpl()传入聚合得到代理对象proxy--实现代理日志log（log+logic）
        UserDao logProxy = new UserDaoLog(target);
        logProxy.query();

        System.out.println("-------------------------------");
        // 将目标对象target = logProxy 传入聚合得到代理对象tim_log_Proxy--实现代理日志log+代理时间
        // （ time + logic(log+logic） )
        UserDao tim_log_Proxy = new UserDaoTIme(logProxy);
        tim_log_Proxy.query();

    }
}
