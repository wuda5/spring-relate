package com.luban.test;

import com.luban.dao.CityMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.InputStream;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class Test_mybatis_noSpring {

//	static final Logger log = Logger.getLogger(Test_mybatis_noSpring.class);
    public static void main(String[] args) {



		try {
			String resource = "mybatis-config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);

			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			SqlSession openSession = sqlSessionFactory.openSession();

//			openSession.getConfiguration().addMapper(CityMapper.class);

			// 3、获取接口的实现类对象
			//会为接口自动的创建一个代理对象，代理对象去执行增删改查方法
			CityMapper mapper = openSession.getMapper(CityMapper.class);

			// ---要想打印sql日志--需要设定日志级别为debug
			System.out.println(mapper.list());
			System.out.println(mapper.list());
			System.out.println(mapper.getClass());
		}
		catch (Exception x){}



	}
}
