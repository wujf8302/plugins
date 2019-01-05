package com.plugin.api.clazz;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;

import com.plugin.api.beans.invoke.UserBean;

// commons-logging.jar commons-beanutils.jar

/**
 * Java反射机制主要提供了以下功能：
 * 在运行时判断任意一个对象所属的类；
 * 在运行时构造任意一个类的对象；
 * 在运行时判断任意一个类所具有的成员变量和方法；
 * 在运行时调用任意一个对象的方法；生成动态代理。
 * @author wujf
 */
public class InvokeUtil {
    
    public static void main(String[] args) {
    	UserBean u1 = new UserBean();
    	u1.setUserid("10001");
    	UserBean u2 = (UserBean)beanToBean(u1,new UserBean());
    	System.out.println("------------------");
    	System.out.println("---"+u2.getUserid());
    	
    	/*
        try {
            Class userBean = Class.forName("com.wjf.common.invoke.UserBean");
            String username = BeanUtils.getProperty(userBean.newInstance(),
                "username");
            
            System.out.println(username);
            System.out.println("-------------");
            
            Method[] methods = userBean.getMethods();
            
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                System.out.println(method.getName());
                if (method.getName().equals("writeOut")) {
                    Object result = method.invoke(userBean.newInstance(),
                        new Object[] {10, "wujianfei" });// 反调
                    System.out.println(result);
                    System.out.println("-------------");
                }
            }
            System.out.println("================");
            test();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }
    
    public static void test() {
        UserBean u = new UserBean();
        try {
            String username = BeanUtils.getProperty(u.getClass().newInstance(),
                "username");
            System.out.println(username);
            
            Method[] methods = u.getClass().getMethods();
            
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];
                System.out.println(method.getName());
                if (method.getName().equals("writeOut")) {
                    Object result = method.invoke(u.getClass().newInstance(),
                        new Object[] {10, "wujianfei" });// 反调
                    System.out.println(result);
                    System.out.println("-------------");
                }
            }
            System.out.println("================");
            
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 得到某个对象的公共属性
     * 
     * @param owner
     * @param fieldName
     * @return 该属性对象
     */
    public Object getProperty(Object owner, String fieldName) throws Exception {
        Class ownerClass = owner.getClass();
        Field field = ownerClass.getField(fieldName);
        Object property = field.get(owner);
        return property;
    }
    
    /**
     * 得到某类的静态公共属性
     * 
     * @param className类名
     * @param fieldName
     *            属性名
     * @return 该属性对象
     */
    public Object getStaticProperty(String className, String fieldName)
        throws Exception {
        Class ownerClass = Class.forName(className);
        Field field = ownerClass.getField(fieldName);
        Object property = field.get(ownerClass);
        return property;
    }
    
    /**
     * 执行某对象方法
     * 
     * @param owner对象
     * @param methodName方法名
     * @param args参数
     * @return 方法返回值
     */
    public static Object invokeMethod(Object owner, String methodName,
        Object[] args) throws Exception {
        Class ownerClass = owner.getClass();
        Class[] argsClass = new Class[args.length];
        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        Method method = ownerClass.getMethod(methodName, argsClass);
        return method.invoke(owner, args);
    }
    
    /**
     * 执行某类的静态方法
     * 
     * @param className
     *            类名
     * @param methodName方法名
     * @param args参数数组
     * @return 执行方法返回的结果
     */
    public static Object invokeStaticMethod(Class ownerClass,
        String methodName, Object[] args) throws Exception {
        Class[] argsClass = new Class[args.length];
        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        Method method = ownerClass.getMethod(methodName, argsClass);
        return method.invoke(null, args);
    }
    
    public Object invokeStaticMethod(String className, String methodName,
        Object[] args) throws Exception {
        return invokeStaticMethod(Class.forName(className), methodName, args);
    }
    
    /**
     * 新建实例
     * 
     * @param className
     *            类名
     * @param args
     *            构造函数的参数
     * @return 新建的实例
     */
    public static Object newInstance(Class newoneClass, Object[] args)
        throws Exception {
        Class[] argsClass = new Class[args.length];
        for (int i = 0, j = args.length; i < j; i++) {
            argsClass[i] = args[i].getClass();
        }
        Constructor cons = newoneClass.getConstructor(argsClass);
        return cons.newInstance(args);
    }
    
    public static Object newInstance(String className, Object[] args)
        throws Exception {
        return newInstance(Class.forName(className), args);
    }
    
    /**
     * 是不是某个类的实例
     * 
     * @param obj
     *            实例
     * @param cls类
     * @return 如果 obj 是此类的实例，则返回 true
     */
    public boolean isInstance(Object obj, Class cls) {
        return cls.isInstance(obj);
    }
    
    /**
     * 得到数组中的某个元素
     * 
     * @param array数组
     * @param index索引
     * @return 返回指定数组对象中索引组件的值
     */
    public Object getByArray(Object array, int index) {
        return Array.get(array, index);
    }
    
    /**
     * 将Bean属性转化成Map
     */
    public static Map beanToMap(Object obj) {
        Map map = new HashMap();
        try {
            Class c = obj.getClass();
            Method m[] = c.getDeclaredMethods();
            for (int i = 0; i < m.length; i++) {
                if (m[i].getName().indexOf("get") == 0) {
                	String key = m[i].getName();
                	key = firstToLower(key.substring("get".length(), key.length()));
                	Object val = m[i].invoke(obj, new Object[0]);
                	
                    map.put(key, val);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * 将Bean属性转化成Map(obj1和obj2有一样的属性).
     */
    public static Object beanToBean(Object obj1,Object obj2) {
        try {
            Class c1 = obj1.getClass();
            Method[] m1 = c1.getDeclaredMethods();
            for (int i = 0; i < m1.length; i++) {
            	String methodName = m1[i].getName();
                if (methodName.indexOf("get") == 0) {
                	String key = m1[i].getName();
                	key = key.substring("get".length(), key.length());
                	Object val = m1[i].invoke(obj1, new Object[0]);
                	//System.out.println(firstToLower(key) + " - " + val);
                	try {
                		invokeMethod(obj2, "set"+key,new Object[]{val});  
					} catch (Exception e) {
						System.err.println(e.getMessage());
					}
                }
            }
        } catch (Throwable e) {
           System.err.println(e.getMessage());
        }
        return obj2;
    }
    
    /**
     * 首字母变成小写
     */
    public static String firstToLower(String fieldName) {
        String s = "";
        if (fieldName != null && fieldName.length() >= 1) {
            String tmp1 = fieldName.substring(0, 1);
            String tmp2 = fieldName.substring(1);
            s = tmp1.toLowerCase() + tmp2;
        }
        
        return s;
    }
    
    /**
     * 首字母变成大写
     */
    public static String firstToUpperCase(String fieldName) {
        String s = "";
        if (fieldName != null && fieldName.length() >= 1) {
            String tmp1 = fieldName.substring(0, 1);
            String tmp2 = fieldName.substring(1);
            s = tmp1.toUpperCase() + tmp2;
        }
        
        return s;
    }  
}
