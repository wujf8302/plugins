package com.plugin.api.mode.single;
/**
 * 单例模式.
 * @author wujf
 */
public class Singleton {
   private static Singleton instance = new Singleton(); 
   private Singleton() {
	
   }  
   public static Singleton getInstance() {
	  return instance; 
   } 
}
