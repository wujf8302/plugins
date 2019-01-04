package com.wujf.study.collection.test3;

/**
 * 测试枚举型. 枚举型可以看成一个量有有限的状态，并把这些状态表先出来，就是列举。
 */
public class CTestEnum1 {

	// enum 类型名{值1,值2,值3}
	public static void main(String[] args) {
		// MyType mt=MyType.Sun;
		MyType aa;
		aa = MyType.Sun;
		// System.out.println(mt);
	}
}

enum MyType {
	Sun, Mon, Tue, Wed, Thu, Fri, Sat
}
