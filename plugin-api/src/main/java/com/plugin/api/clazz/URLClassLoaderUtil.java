package com.plugin.api.clazz;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.plugin.api.beans.invoke.HomeBean;

/**
 * 动态加载class
 * 动态加载jar
 * 动态执行方法
 * 如果动态加载的类中有使用了log4j,可将log4j配置文件和jar打包在一起(注意要把classes/lib下的log4j包及配置文件去掉)
 * 
 * 动态编译class
 * 解压打包的jar包
 * @author wujf
 */
public class URLClassLoaderUtil {
    
    private URLClassLoader classLoader;
    private String jarFile;
    private List jars = new ArrayList(0);
    private Map classMap = new HashMap();
    
    public URLClassLoaderUtil(String jarFileName) {
        this.jarFile = jarFileName;
        this.init(new File(jarFileName));
    }
    
    public static void main(String[] args) {
        //operateFile();
        operateDirectory();
    }
    
    public static void operateDirectory() {
        String jarFileName = "E:/wujf/ffcs/oss/axis2client/WebContent/WEB-INF/lib/";
        URLClassLoaderUtil util = new URLClassLoaderUtil(jarFileName);//载入jar
        
        Class clazz = util.getClass("utils.jar", "com.plugin.PrintUtil");
        //Class clazz = util.getClass("com.plugin.PrintUtil");
        
        try {
            //新建类实例
            Object o = InvokeUtil.newInstance(clazz, new Object[]{});
            System.out.println(o);
            
            //调用成员方法
            InvokeUtil.invokeMethod(o,"println", new Object[]{"调用成员方法"});
            
            //调用静态方法
            InvokeUtil.invokeStaticMethod(clazz, "println", new Object[]{"调用静态方法"});
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        /**
        import com.thoughtworks.xstream.XStream;
        import com.thoughtworks.xstream.io.xml.DomDriver;
        import com.plugin.PrintUtil;
        
        public class FFCSUtil {
            
             public static void printInfo(String msg){
                 PrintUtil.println(msg);
             }
             
             public Object alias(Class clazz,String xml){
                XStream xstream = new XStream(new DomDriver());
                xstream.alias("homes",clazz);
                return xstream.fromXML(xml);
            }
        }
        */
        clazz = util.getClass("ffcsutils.jar", "com.ffcs.utils.FFCSUtil");
        
        try {
            //新建类实例
            Object o = InvokeUtil.newInstance(clazz, new Object[]{});
            System.out.println(o);
            
            InvokeUtil.invokeMethod(o,"printInfo", new Object[]{"调用其他jar包中类的方法"});

            String xml = "<homes><hid>1</hid><hmeasurement>5*6</hmeasurement><hname>房间1-93</hname><hsize>30</hsize></homes>";
            Object o2 = InvokeUtil.invokeMethod(o,"alias", new Object[]{HomeBean.class,xml});
            
            HomeBean h = (HomeBean)o2;
            
            System.out.println("hid：" + h.getHid());
            System.out.println("hname：" + h.getHname());
            System.out.println("hsize：" + h.getHsize());
            System.out.println("hmeasurement：" + h.getHmeasurement());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void operateFile() {
        String jarFileName = "E:/wujf/ffcs/oss/axis2client/WebContent/WEB-INF/lib/utils.jar";
        URLClassLoaderUtil util = new URLClassLoaderUtil(jarFileName);//载入jar
        
        Class clazz = util.getClass("utils.jar", "com.plugin.PrintUtil");
        
        try {
            //新建类实例
            Object o = InvokeUtil.newInstance(clazz, new Object[]{});
            System.out.println(o);
            
            //调用成员方法
            InvokeUtil.invokeMethod(o,"println", new Object[]{"调用成员方法"});
            
            //调用静态方法
            InvokeUtil.invokeStaticMethod(clazz, "println", new Object[]{"调用静态方法"});
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 在限定的jar中找
     */
    public Class getClass(String jarName,String classPath){
        Map map = getClassMap();
        JARBean jar = (JARBean)map.get(jarName);
        if(jar != null){
            Class clazz = (Class)jar.getClassMap().get(classPath);
            if(clazz != null){
                return clazz;
            }
        }
        return null;
    }
    
    /**
     * 在所有已加载的类中找
     */
    public Class getClass(String classPath){
        Map map = getClassMap();
        Set set = map.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            String jarName = (String) iterator.next();
            JARBean jar = (JARBean)map.get(jarName);
            if(jar != null){
                Class clazz = (Class)jar.getClassMap().get(classPath);
                if(clazz != null){
                    return clazz;
                }
            }
            
        }
        
        return null;
    }

    /**
     * 将jar文件路径加入到classpath
     */
    private void init(File file) {
        if (file.isFile()) {
            
            this.addClassPath(file.toURI().toString());
            this.jars.add(file.getAbsolutePath());
            
        }else if(file.isDirectory()){
            
            String[] suffixs = new String[]{"jar","zip" };
            
            List files = new ArrayList();
            List filesURL = new ArrayList();
            //---
            File[] fs = file.listFiles();
            for (int i = 0; i < fs.length; i++) {
                
                String path = fs[i].getAbsolutePath();
                
                //System.out.println(path);
                
                for (int j = 0; j < suffixs.length; j++) {
                    if(path.endsWith(suffixs[j])){
                        files.add(fs[i].getAbsolutePath());
                        filesURL.add(fs[i].toURI().toString());
                        break;
                    }   
                }
            }
            //---
            Object[] o = filesURL.toArray();
            this.addClassPath(o);
            this.jars.addAll(files);
        }
        
        if(this.jars != null && this.jars.size()>0){
            for (int i = 0; i < jars.size(); i++) {
                String s = (String)jars.get(i);
                System.out.println(s);
                JARBean jar = this.loadClass(s);
                if(jar != null){
                    classMap.put(jar.getJarName(), jar);
                }
            }
        }else{
            System.out.println("jars is null or empty");
        }
    }
    
    /**
     * 添加jar路径到classpath
     */
    private void addClassPath(Object o) {
        if(o != null){
            if(o instanceof String){
                try {
                    String jarURL = (String)o;
                    this.classLoader = new URLClassLoader(new URL[] { new URL(jarURL)});//URLClassLoader类载入器
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }else if(o instanceof Object[]){
                Object[] jarURLs = (Object[])o;
                URL[] urls = new URL[jarURLs.length];
                for (int i = 0; i < jarURLs.length; i++) {
                    try {
                        urls[i] = new URL(jarURLs[i].toString());
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
                classLoader = new URLClassLoader(urls);
            }
        }
    }
    
    /**
     * 动态载入class
     */
    private JARBean loadClass(String jarFileName) {

        JarFile jarFile = null;
        try {
            jarFile = new JarFile(jarFileName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        JARBean jar = new JARBean(jarFile);
        
        Enumeration<JarEntry> en = jarFile.entries();
        while (en.hasMoreElements()) {
            JarEntry je = en.nextElement();
            String name = je.getName();
            String s5 = name.replace('/', '.');
            if (s5.lastIndexOf(".class") > 0) {
                String className = je.getName().substring(0,je.getName().length() - ".class".length()).replace('/','.');
                Class c = null;
                try {
                    c = this.classLoader.loadClass(className); //使用指定的二进制名称来加载类
                    
                    jar.getClassMap().put(c.getName(),c);
                    
                    System.out.println(className);
                } catch (ClassNotFoundException e) {
                    System.out.println("NO CLASS: " + className);
                } catch (NoClassDefFoundError e) {
                    System.out.println("NO CLASS: " + className);
                }
            }
        }
        return jar;
    }

    public String getJarFileName() {
        return jarFile;
    }

    public void setJarFileName(String jarFileName) {
        this.jarFile = jarFileName;
    }

    public URLClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(URLClassLoader classLoader) {
        this.classLoader = classLoader;
    }
    
    public List getJars() {
        return jars;
    }

    public void setJars(List jars) {
        this.jars = jars;
    }

    public Map getClassMap() {
        return classMap;
    }

    public void setClassMap(Map classMap) {
        this.classMap = classMap;
    }

    public class JARBean{
        
        private String jarName;
        private JarFile jarFile;
        private Map classMap = new HashMap();
        
        public JARBean(JarFile jarFile){
            this.jarFile = jarFile;
        }
        
        public String getJarName() {
            if(jarFile != null){
                jarName = jarFile.getName();
                jarName = jarName.substring(jarName.lastIndexOf(File.separator)+1, jarName.length());
            }
            return jarName;
        }
        public Map getClassMap() {
            return classMap;
        }
        public void setClassMap(Map classMap) {
            this.classMap = classMap;
        }
        public JarFile getJarFile() {
            return jarFile;
        }
        public void setJarFile(JarFile jarFile) {
            this.jarFile = jarFile;
        }
    }
}
