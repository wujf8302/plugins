package com.wujf.study.exam.exam1;

public class TestObject2 {
	private static int a;// static不能与fianl一起用

	protected static int ww(int a, int b) {
		return b;
	}

	public static void main(String[] args) {
		System.out.println(a);
		Runnable r = new Runnable() {
			public void run() {// run方法被实现
				System.out.println("Cat");
			}
		};
		Thread t = new Thread(r) {
			public void run() {// run方法被重写
				System.out.println("Dog");
			}
		};
		t.start();
		System.out.println("-------------");
		// Thread t2 = new Thread(r);
		// t2.start();
	}

	public static void sy() {
		System.out.println(a);
	}
}

