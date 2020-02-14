package com.luban.byResouce;

import com.luban.dao.CityMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TestResouceService {

//    @Autowired
//    @Resource(name="daoResouce2")
    @Resource
    DaoResouceInterface daoResouce1;
//    DaoResouceInterface daoResouce2;
//    DaoResouceInterface xx;



    public void testxx() {
        boolean present = ClassUtils.isPresent("javax.annotation.Resource", AnnotationConfigUtils.class.getClassLoader());

        System.out.println("属性 testResouceJdk8 观察是否null值:["+ daoResouce1 +"],boolean:"+present);
//        System.out.println("属性 testResouceJdk8 观察是否null值:["+ DaoResouce1 +"],boolean:"+present);
//        System.out.println("属性 testResouceJdk8 观察是否null值:["+ DaoResouce2 +"],boolean:"+present);
//        System.out.println("属性 testResouceJdk8(无此对象) 观察是否null值:["+ xx +"],boolean:"+present);
    }
}
