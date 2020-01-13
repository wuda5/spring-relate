package com.luban.test;

import com.luban.app.Appconfig;
import com.luban.dao.IndexDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.File;

public class testWu {
    public static void main(String[] args) {

//		AnnotationConfigApplicationContext oc = new AnnotationConfigApplicationContext(Appconfig.class);

		try {
			// 找到包下面所有的 满足要求的--文件
			Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:com/luban/**/*.class");
			System.out.println(resources);

			for (Resource re : resources) {

				final File file = re.getFile();
				//--file后,得到.class，-->找到对应上面的注解？
				System.out.println(re);
			}
		}
		catch (Exception e)
		{

		}


	}
}
