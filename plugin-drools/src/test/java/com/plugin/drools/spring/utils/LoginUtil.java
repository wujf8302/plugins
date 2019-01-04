package com.plugin.drools.spring.utils;

public class LoginUtil {
	
	public static boolean checkDB(String name, String password) {
		return "jack".equals(name.trim()) && "123".equals(password.trim());
	}
}
