package com.wujf.study.thread.syn;

/**
 * 方法块同步 示例.
 * @author fj
 */
public class MainApp2 {
	
	public static void main(String args[]) {
		
		StudentThread student1=new StudentThread();
		StudentThread student2=new StudentThread();
		StudentThread student3=new StudentThread();
		StudentThread student4=new StudentThread();
		StudentThread student5=new StudentThread();
		
		student1.start();
		student2.start();
		student3.start();
		student4.start();
		student5.start();
	}
}
