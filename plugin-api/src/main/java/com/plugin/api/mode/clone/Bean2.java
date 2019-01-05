package com.plugin.api.mode.clone;

import java.io.Serializable;
/**
 * 
 * @author wujf
 */
public class Bean2 implements Cloneable,Serializable  {
	
    public String usreName;
    public static int age;
    
    public Bean2(String usreName,int age){
    	this.usreName = usreName;
    	this.age = age;
    }
    
    public Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}
}
