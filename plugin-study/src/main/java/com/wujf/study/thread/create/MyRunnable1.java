package com.wujf.study.thread.create;

public class MyRunnable1 implements Runnable {

	public void run() {
		
		for (int i = 0; i < 10; i++) {
			System.out.println(i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//System.out.println(Thread.currentThread().getName());
		}
	}
}
