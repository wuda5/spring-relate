package com.luban.test;

import com.luban.dao.UserDao;
import com.luban.service.UserService;
import com.luban.service.UserServiceImpl;
import org.spring.util.BeanFactory;

public class Test {
    public static void main(String[] args) {
        BeanFactory beanFactory = new BeanFactory("spring.xml");

        UserService service = (UserService) beanFactory.getBean("service");

        service.find();
    }
}
