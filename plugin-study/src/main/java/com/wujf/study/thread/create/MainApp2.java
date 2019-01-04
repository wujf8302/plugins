package com.wujf.study.thread.create;

public class MainApp2 {
	
	public static void main(String[] args) {
		
		MyRunnable1 mr1=new MyRunnable1();
		Thread th1=new Thread(mr1,"线程1");
		
		MyRunnable2 mr2=new MyRunnable2();
		Thread th2=new Thread(mr2,"线程2");
		
		th1.start();
		th2.start();
	}
}
