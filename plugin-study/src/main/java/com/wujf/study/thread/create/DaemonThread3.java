package com.wujf.study.thread.create;

public class DaemonThread3 extends Thread {

	@Override
	public void run() {

		for (int i = 1; i < 10000; i++) {
			System.out.println( i );
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
