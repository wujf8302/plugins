package com.wujf.study.thread.create;

public class SubThread2 extends Thread {

	@Override
	public void run() {

		for (int i = 97; i < 107; i++) {
			System.out.println( (char)i );
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
