package com.wujf.study.base;

public class TestObject {

	public static void main(String[] args) {

		Object obj1 = new Object();
		Object obj2 = new Object();

		// toString()
		System.out.println(obj1);
		// System.out.println(obj1.toString());

		// hashCode()
		System.out.println(obj1.hashCode());

		// equals()
		System.out.println(obj1.equals(obj2));

	}
}
