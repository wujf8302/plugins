package com.wujf.study.inner;

public class Outside {

	public class inner {
		public void say() {
			System.out.println("inner");
		}
	}

	public void say() {
		System.out.println("outside");
	}
}
