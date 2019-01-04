package com.wujf.study.thread.create;

public class MyRunnable2 implements Runnable {

	public void run() {
		
		for (int i = 97; i < 107; i++) {
			System.out.println( (char)i );
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("____MyRunnable2 123");
			}
		}
	}
}
