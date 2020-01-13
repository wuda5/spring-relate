package com.luban.proxy.proxy2;

import com.luban.dao.UserDao;

//静态代理--第二类：聚合---目标对象和代理对象实现同一接口，代理对象当中要包含目标对象target
//比继承稍好，可以减少代理类的创建！
//2.UserDaoLog为代理对象--对应测试类test0
public class UserDaoTIme implements UserDao {


    //dao为目标对象接口，采用聚合，构造方法传入dao---(装饰子)
    UserDao dao;

    public UserDaoTIme(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public void query() {
        //静态代理2采用聚合方式--完成dao的代理
        System.out.println("time+++静态代理2采用聚合方式--完成dao的代理");

        dao.query();

    }

    @Override
    public void query(String p) {

    }
}
