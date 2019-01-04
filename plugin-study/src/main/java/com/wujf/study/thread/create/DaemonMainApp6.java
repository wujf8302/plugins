package com.wujf.study.thread.create;

public class DaemonMainApp6 {

	public static void main(String[] args) {
		
		DaemonThread3 dt1=new DaemonThread3();
		dt1.setDaemon(true);
		
		SubThread1 dt2=new SubThread1();
		SubThread2 dt3=new SubThread2();
		
		dt1.start();
		dt2.start();
		dt3.start();
	}
}
