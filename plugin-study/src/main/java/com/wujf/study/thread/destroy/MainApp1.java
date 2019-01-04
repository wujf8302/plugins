package com.wujf.study.thread.destroy;

public class MainApp1 {
	
	public static void main(String[] args) {

		SubThread1 st1 = new SubThread1();
		SubThread2 st2 = new SubThread2();

		st1.start();
		st2.start();

		try {
			Thread.sleep(3000);
		} catch (Exception e) {
			
		}

		st1.setTag(true); // 友好关闭.
		st2.interrupt();// 有条件中断.
		st2.destroy();// st2.stop() 强制中断.
	}
}
