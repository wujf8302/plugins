package com.wujf.study.collection.old2;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 用 Properties类 的对象载入外部配置文件中key-value.
 * @author fj
 */
public class ETestProperties2 {

	// 入口方法; 注"//"开始的注释不是文档化的注释,但也是必须,可增加程序的可读性;
	public static void main(String args[]) {

		Properties props = new Properties();

		try {
			props.load(new FileInputStream("./config/config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 遍历.
		Enumeration keys = props.propertyNames();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();// 遍历key
			String property = props.getProperty(key); // 获取props中与key对应的值
			System.out.println(key + " : " + property); // 输出key与value对
		}
	}
}
