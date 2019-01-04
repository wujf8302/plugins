package com.wujf.study.date;

import java.util.Date;
import java.util.Calendar;

/**
 * 日历类(java.util.Calendar)的使用.
 * @author fj
 */
public class TestCalendar {

	public static void main(String[] args) {

		// ------ 生成日历对象 ------
		Calendar cl = Calendar.getInstance();

		// 日历对象转成日期对象
		Date myDate = cl.getTime();
		System.out.println(myDate);
		System.out.println();// 输出换行

		// 获取年月日
		int year = cl.get(Calendar.YEAR);
		int month = cl.get(Calendar.MONTH) + 1;// 月索引从0开始
		int date = cl.get(Calendar.DAY_OF_MONTH);
		int hour = cl.get(Calendar.HOUR_OF_DAY);
		int minute = cl.get(Calendar.MINUTE);
		int second = cl.get(Calendar.SECOND);
		int week = cl.get(Calendar.DAY_OF_WEEK) - 1;// 星期日为一个星期的第一天,索引从1开始

		System.out.println("年: " + year);
		System.out.println("月: " + month);
		System.out.println("日: " + date);
		System.out.println("时: " + hour);
		System.out.println("分: " + minute);
		System.out.println("秒: " + second);
		System.out.println("星期: " + week);
		System.out.println();// 输出换行

		// 设置日历 (查看2008.10.1是星期几)
		cl.set(Calendar.YEAR, 2008);
		cl.set(Calendar.MONTH, 9);// 注月索引从0开始
		cl.set(Calendar.DAY_OF_MONTH, 1);

		int wdInt = cl.get(Calendar.DAY_OF_WEEK) - 1;
		String[] wdStr = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		System.out.println("2008.10.1是: " + wdStr[wdInt]);

	}
}
