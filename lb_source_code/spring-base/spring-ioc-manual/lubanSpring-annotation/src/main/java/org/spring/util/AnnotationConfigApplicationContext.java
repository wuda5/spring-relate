package org.spring.util;

import com.luban.anno.Luban;

import java.io.File;

public class AnnotationConfigApplicationContext {

    public void scan(String basePackage){
        // 如何得到calss.forName("com.luban.service.UserserviceImpl")
        // 这里是得到target下面的路径
        String rootPath = this.getClass().getResource("/").getPath();
        String  basePackagePath =basePackage.replaceAll("\\.","\\\\");
        File file = new File(rootPath+"//"+basePackagePath);
        // 其实这里应该用递归，这里简化 了，
        // 返回了文件名的数组
        String names[]=file.list();
        for (String name : names) {
            name=name.replaceAll(".class","");
            try {

               Class clazz =  Class.forName(basePackage+"."+name);
                //判斷是否是屬於@servi@xxxx
                if(clazz.isAnnotationPresent(Luban.class)){
                    Luban luban = (Luban) clazz.getAnnotation(Luban.class);
                    System.out.println(luban.value());
                    System.out.println(clazz.newInstance());

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
