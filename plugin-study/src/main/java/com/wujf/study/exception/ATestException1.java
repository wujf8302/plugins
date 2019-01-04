package com.wujf.study.exception;

/**
 * 异常会 打断程序的正常流程.
 * @author fj
 */
public class ATestException1 {
	
	public static void main(String[] args) {
		
		//1.
		int a=2,b=0;
		
		//2.
		System.out.println("www.umlsoft.net");
		
		//3.
		System.out.println("原来生活可以这样方便!");
		
		//4. 出错.下面语句不在执行!
		int c=a/b;
		
		//5. 
		System.out.println("www.3wwl.com");
		
		//6.
		System.out.println("好网络，贵而不贵!");
	}
}
