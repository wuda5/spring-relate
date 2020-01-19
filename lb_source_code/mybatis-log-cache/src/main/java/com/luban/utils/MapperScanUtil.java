package com.luban.utils;

//import com.luban.anno.Luban;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MapperScanUtil {

    //做个单例
  private static MapperScanUtil scanUtil = new MapperScanUtil();

    private MapperScanUtil() {
    }



    public static List <Class<?>> scanMappers (String basePackage){
       return scanUtil.scan(basePackage);
    }


    /**扫描返回集合class--接口mapper的类class们
     * 应该做递归扫描---这里暂时灭有做---
     * */
    public  List <Class<?>> scan(String basePackage){
       List<Class<?>> result = new ArrayList<Class<?>>();

        // 如何得到calss.forName("com.luban.service.UserserviceImpl")
        // 这里是得到target下面的路径
        String rootPath = this.getClass().getResource("/").getPath();
//        String rootPath = this.getClass().getResource("/").getPath();
        String basePackagePath =basePackage.replaceAll("\\.","\\\\");
        File file = new File(rootPath+"//"+basePackagePath);
        // 其实这里应该用递归，这里简化 了，
        // 返回了文件名的数组
        String names[]=file.list();
        for (String name : names) {
            name=name.replaceAll(".class","");
            try {

               Class clazz =  Class.forName(basePackage+"."+name);

                if ( !clazz.isInterface())
                {
                    continue;
                }
                result.add(clazz);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
