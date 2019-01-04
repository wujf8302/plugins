package com.wujf.study.thread.create;

public class MainApp1 {
	
	public static void main(String[] args) {
		
		SubThread1 st1=new SubThread1();
		SubThread2 st2=new SubThread2();
		
		st1.start();
		st2.start();

		System.out.println("主线程结束了!");
	}
}
