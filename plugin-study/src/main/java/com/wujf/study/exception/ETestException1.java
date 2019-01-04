package com.wujf.study.exception;

public class ETestException1 {

	public static int fun1(int a,int b) throws Exception{
		return a/b;
	}
	
	public static void main(String[] args) {
		
		System.out.println("1");
		
		try {
			fun1(1,0);
		} catch (Exception e) {
			System.out.println("Error!");
		}
		
		System.out.println("2");
	}
}
