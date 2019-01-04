package com.wujf.study.collection.old2;

import java.util.Enumeration;
import java.util.Properties;

/**
 * 一个测试 Properties类 用法的例子. setProperty() , getProperty()
 * @author fj
 */
public class DTestProperties1 {

	// 入口方法; 注"//"开始的注释不是文档化的注释,但也是必须,可增加程序的可读性;
	public static void main(String args[]) {

		Properties props = new Properties();

		props.setProperty("a", "1");
		props.setProperty("b", "2");
		props.setProperty("c", "3");

		// System.out.println(prop.getProperty("a"));
		
		// 遍历
		Enumeration keys = props.propertyNames();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();// 遍历key
			String property = props.getProperty(key); // 获取props中与key对应的值
			System.out.println(key + " : " + property); // 输出key与value对
		}
	}
}
