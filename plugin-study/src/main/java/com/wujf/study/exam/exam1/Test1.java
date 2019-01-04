package com.wujf.study.exam.exam1;

public class Test1 {
	public static void main(String[] args) {
		//try catch finally可以没有catch
		try {
			test();
		} catch (Exception e) {
			System.out.println("exception");
		}
		/*
		 * int x = 10; do{ System.out.println(x--); }while(x<10);
		 */// 死循环
	}

	static void test() {
		try {
			String x = null;
			System.out.println(x.toString());
		} finally {
			System.out.println("finally");
		}
	}
}
