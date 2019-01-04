package com.wujf.study.exception;

/**
 * 异常会 打断程序的正常流程.
 * @author fj
 */
public class DTestException1 {

	public static void main(String[] args) {

		// 1.
		int a = 2, b = 0;

		// 2.
		System.out.println("www.umlsoft.net");

		// 3.
		System.out.println("原来生活可以这样方便!");

		int c = 0;
		
		try {
			// 4. 出错.下面语句不在执行!
			c = a / 2;
			Object ojb = new String("abc");
			Integer iObj = (Integer) ojb;
			
			System.out.println("right");
			
		} catch (Exception ex) {
			System.out.println("Error");
		}
		finally{
			System.out.println("finally");
		}
		
		// catch(ArithmeticException ae){
		// c=1000;
		// }
		// catch(ClassCastException cce){
		// c=2000;
		// }
		// System.out.println(c);

		// 5.
		System.out.println("www.3wwl.com");

		// 6.
		System.out.println("好网络，贵而不贵!");

	}
}
