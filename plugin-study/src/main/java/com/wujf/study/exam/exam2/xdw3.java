package com.wujf.study.exam.exam2;

public class xdw3 {

	public static void main(String[] args) {

		xdw3 x = new xdw3();
		System.out.println(x instanceof xdw3);
		short s1 = 1;
		short s2 = 1;
		// s1= s1+2;//编译出错 高数据类型转低数据类型 需要强转
		s1 = (short) (s1 + 2);
		System.out.println(s1);
		s2 += 1; // 表达式编译成功，隐式转换
		System.out.println(s2);

		try {
			exce1();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public static void exce1() throws Throwable {
		try {
			String[] a = new String[5];
			System.out.println(a[5]);
		} catch (Exception e) {
			throw new Throwable("数组越界");
		}
	}

	public static void exce2() {
		try {
			String[] a = new String[5];
			System.out.println(a[5]);
		} catch (Exception e) {
			try {
				throw new Throwable("数组越界");
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
		}
	}
}
