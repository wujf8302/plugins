package com.wujf.study.inner;

public class OuterClass {
	private String username = "wujianfei";
	private static String password = "123456";

	public OuterClass() {
		InterClass ic = new InterClass();
		ic.read();
		System.out.println("OuterClass Create");
	}

	// 如果内部类是静态的那么它只能访问外部类的静态变量或静态方法
	// 如果内部类是成员的那么它可以访问静态或成员的变量和方法
	public class InterClass {
		public InterClass() {
			System.out.println("InterClass Create");
		}

		public void read() {
			System.out.println(username);
			System.out.println(password);
		}
	}

	// 静态内部类只能访问外部类的静态方法和变量
	public static class Inter1 {
		public void read() {
			System.out.println(password);
		}

		public static void readstatic() {
			System.out.println(password);
		}
	}

	public class Inter2 {
		public void read1() {
			System.out.println(password);
		}
		// 如果内部类中在静态变量或方法那么这个内部类一定时静态的
		// public static void read2(){
		//
		// }
	}

	// 内部类可以是抽象类
	abstract class InnerOne {
		public abstract double methoda();
	}

	public static void main(String[] args) {
		OuterClass oc = new OuterClass();
	}
}
