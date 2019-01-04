package com.wujf.study.thread;

public class ATestThread1 {

	public static void main(String[] args) {

		// ATest1 at=new ATest1();
		// at.print();

		for (int i = 0; i < 10; i++) {
			System.out.println(i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (int i = 97; i < 107; i++) {
			System.out.println((char) i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
