package com.luban.proxy;

import com.luban.dao.CoustomInvocationHandler;
import com.sun.jndi.toolkit.url.UrlUtil;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ProxyUtil {


    /**
     *  content --->string
     *  .java  io
     * .class
     *
     * @param  targetInf 目标对象接口的class类对象
     * @param h CoustomInvocationHandler
     *
     * .new   反射----》class
     * @return
     */
    public static Object newInstance(Class targetInf, CoustomInvocationHandler h){
        Object proxy=null;
        //String handlerName = CoustomInvocationHandler.class.
        Method methods[] =targetInf.getDeclaredMethods();
        String line="\n";
        String tab ="\t";
        String infName = targetInf.getSimpleName();
        String content ="";
        String packageContent = "package com.google;"+line;
        String importContent = "import "+targetInf.getName()+";"+line
                             +"import com.luban.dao.CoustomInvocationHandler;"+line
                             +"import java.lang.Exception;"
                             +"import java.lang.reflect.Method;"+line;
        String clazzFirstLineContent = "public class $Proxy implements "+infName+"{"+line;
        String filedContent  =tab+"private CoustomInvocationHandler h;"+line;
        String constructorContent =tab+"public $Proxy (CoustomInvocationHandler h){" +line
                                  +tab+tab+"this.h =h;"
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
            //注意：此处是代理逻辑的重点！！
            //Method method = Class.forName(\""+targetInf.getName()+"\").getDeclaredMethod(\""+methodName+
            //等价于Method method = Class.forName("com.luban.dao.LubanDao").getDeclaredMethod("proxy");
            //  return (String)h.invoke(method);
            /**
             * public class $Proxy implements LubanDao{
             *
             * 	private CoustomInvocationHandler h;
             * 	public $Proxy (CoustomInvocationHandler h){
             * 		this.h =h;
             *        }
             * 	public String proxy()throws Exception {
             * 	   //methodName=所具体代理类中 LubanDaoImpl中的所要代理接口方法proxy();
             * 		Method method = Class.forName("com.luban.dao.LubanDao").getDeclaredMethod("proxy");
             * 		return (String)h.invoke(method);
             *    }}
             * */
            methodContent+=tab+"public "+returnTypeName+" "+methodName+"("+argsContent+")throws Exception {"+line
                           +tab+tab+"Method method = Class.forName(\""+targetInf.getName()+"\").getDeclaredMethod(\""+methodName+"\");"+line
                            +tab+tab+"return ("+returnTypeName+")h.invoke(method);"+line;
            methodContent+=tab+"}"+line;
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

            URL[] urls = new URL[]{new URL("file:D:\\\\")};
            URLClassLoader urlClassLoader = new URLClassLoader(urls);
            Class clazz = urlClassLoader.loadClass("com.google.$Proxy");

            Constructor constructor = clazz.getConstructor(CoustomInvocationHandler.class);
            proxy = constructor.newInstance(h);
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
