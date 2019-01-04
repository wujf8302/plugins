package com.wujf.study.collection.old2;

import java.io.FileOutputStream;
import java.util.Properties;

/**
 * 用 Properties类 的对象载入外部配置文件中key-value.
 * @author fj
 */
public class ETestProperties3 {

	// 入口方法; 注"//"开始的注释不是文档化的注释,但也是必须,可增加程序的可读性;
	public static void main(String args[]) {

		Properties props = new Properties();
		props.setProperty("sid", "oracle");
		props.setProperty("user", "qq");
		props.setProperty("pwd", "abc");

		try {
			props.store(new FileOutputStream("./config/cf.ini"), "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
