package com.plugin.api.mode.single;
/**
 * 单例模式
 */
public class SingletonDemo1 {
	private static SingletonDemo1 instance = null;
	public static SingletonDemo1 getInstance(){
		if(instance==null){
			instance = new SingletonDemo1();
	    }
		return instance; 
	}
}
