package com.luban.test;

import com.luban.dao.LubanDao;
import com.luban.dao.LubanDaoImpl;
import com.luban.proxy.ProxyUtil;


public class Test {
    public static void main(String[] args) {

        LubanDao proxy = (LubanDao) ProxyUtil.newInstance(new LubanDaoImpl());
        proxy.query();

    }
}
