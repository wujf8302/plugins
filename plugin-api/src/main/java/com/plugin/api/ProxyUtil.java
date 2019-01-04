package com.plugin.api;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.log4j.Logger;
/**
 * 代理类
 */
public class ProxyUtil {
	
    private static Logger log = Logger.getLogger(ProxyUtil.class);

	 public static Object proxy(Class Iimpl,Object impl,InvocationHandler invocationHandler){
		 return Proxy.newProxyInstance(impl.getClass().getClassLoader(),new Class[]{Iimpl}, new DynamicProxy(invocationHandler));
	 }
	
     private static class DynamicProxy implements InvocationHandler {
    	 
    	private InvocationHandler invocationHandler;

    	public DynamicProxy(InvocationHandler invocationHandler){
            this.invocationHandler = invocationHandler;
    	}
    	
		public Object invoke(Object proxy, Method method, Object[] args)throws Throwable {
			return this.invocationHandler.invoke(proxy, method, args);
		}
     }
}
