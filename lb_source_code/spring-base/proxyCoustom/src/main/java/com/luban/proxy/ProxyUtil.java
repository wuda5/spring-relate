package com.luban.proxy;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
/**
* 此proxyUtiil中作用：可以完全不用像静态代理一样需要写很多的代理类
 * 2.注意：此工
 * 此工具暂时实现了对任意接口即其方法的动态代理，但是具中代理的逻辑是暂时写死的sout打印，其实逻辑也可以写动态，只是更加麻烦
 * 2）还有就是暂时对方法带有return返回值的未作处理，其实也简单
*
 * */
public class ProxyUtil {

    /**
     *  content --->string
     *  .java  io
     * .class
     *
     *
     *
     * .new   反射----》class
     *  @param  target 目标对象接口的具体实现类实例对象
     * @return
     */
    public static Object newInstance(Object target){
        Object proxy=null;
        // 注意：这里得到的targetInf是简化了的，是志当前目标对象所对应的接口的第一个class类对象接口（可能有多个接口）
        Class targetInf = target.getClass().getInterfaces()[0] ;
        Method methods[] =targetInf.getDeclaredMethods();
        String line="\n";
        String tab ="\t";
        String infName = targetInf.getSimpleName();
        String content ="";
        String packageContent = "package com.google;"+line;
        String importContent = "import "+targetInf.getName()+";"+line;
        String clazzFirstLineContent = "public class $Proxy implements "+infName+"{"+line;
        String filedContent  =tab+"private "+infName+" target;"+line;
        String constructorContent =tab+"public $Proxy ("+infName+" target){" +line
                                  +tab+tab+"this.target =target;"
                                  +line+tab+"}"+line;
        String methodContent = "";
        for (Method method : methods) {
            String returnTypeName = method.getReturnType().getSimpleName();
            String methodName =method.getName();
            // Sting.class String.class
            Class args[] = method.getParameterTypes();
            String argsContent = "";
            String paramsContent="";
            int flag =0;
            for (Class arg : args) {
                String temp = arg.getSimpleName();
                //String
                //String p0,Sting p1,
                argsContent+=temp+" p"+flag+",";
                paramsContent+="p"+flag+",";
                flag++;
            }
            if (argsContent.length()>0){
                argsContent=argsContent.substring(0,argsContent.lastIndexOf(",")-1);
                paramsContent=paramsContent.substring(0,paramsContent.lastIndexOf(",")-1);
            }

            methodContent+=tab+"public "+returnTypeName+" "+methodName+"("+argsContent+") {"+line
                    //下面的sysout 打印是代理逻辑--暂时写死的！！
                    +tab+tab+"System.out.println(\"log\");"+line
                    +tab+tab+"target."+methodName+"("+paramsContent+");"+line
                    +tab+"}"+line;

        }

        content=packageContent+importContent+clazzFirstLineContent+filedContent+constructorContent+methodContent+"}";

        File file =new File("d:\\com\\google\\$Proxy.java");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            fw.write(content);
            fw.flush();
            fw.close();


            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

            StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
            Iterable units = fileMgr.getJavaFileObjects(file);

            JavaCompiler.CompilationTask t = compiler.getTask(null, fileMgr, null, null, null, units);
            t.call();
            fileMgr.close();

            //--上面已经在本地得到自己编译好的.class文件了，下面借助classLoader （URLClassLoader）加载外部的。class文件
            //得到其对应具体Class类对象，后再反射初实例对象
            URL[] urls = new URL[]{new URL("file:D:\\\\")};
            URLClassLoader urlClassLoader = new URLClassLoader(urls);
            Class clazz = urlClassLoader.loadClass("com.google.$Proxy");

            //反射
            Constructor constructor = clazz.getConstructor(targetInf);

            proxy = constructor.newInstance(target);
            //clazz.newInstance();
            //Class.forName()
        }catch (Exception e){
            e.printStackTrace();
        }





        /**
         * public UserDaoLog(UserDao target){
         * 		this.target =target;
         *
         *        }
         */
        return proxy;
    }
}
