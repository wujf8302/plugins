package com.wujf.study.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期类(Date)的使用及格式日期.
 * @author fj
 */
public class TestDate {
	
	public static void main(String[] args) {

		// ------ 当前时间毫秒数------
		long startInt = System.currentTimeMillis();

		// ------ 生成日期对象.------
		java.util.Date myDate = new java.util.Date();

		// 系统默认格式显示.
		System.out.println(myDate);

		// 格式化日期显示.
		//java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
		//Sun Jun 07 10:47:53 CST 2009
		//2009-06-07 10:47:53 星期日
		//上面程序执行所用时间(毫秒):266
		
		
		java.text.DateFormat df = java.text.DateFormat.getDateInstance();
		//Sun Jun 07 10:48:22 CST 2009
		//2009-6-7
		//上面程序执行所用时间(毫秒):47

		String str11 = df.format(myDate);
		System.out.println(str11);

		// ------ 当前时间毫秒数------
		long endInt = System.currentTimeMillis();
		long difInt = endInt - startInt;
		System.out.println("上面程序执行所用时间(毫秒):" + difInt);
		
		System.out.println(new Date(0));
		SimpleDateFormat  sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss E");
		System.out.println(sd.format(new Date(0)));
	}
}
