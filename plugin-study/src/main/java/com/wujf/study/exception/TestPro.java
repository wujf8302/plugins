package com.wujf.study.exception;

/**
 * 异常处理经常用在验证中.
 * @author fj
 */
public class TestPro {
	
	public static void main(String[] args) {

		String ageStr="123";
		
		try {
			Integer.parseInt(ageStr);
			System.out.println("正确");
		} catch (Exception e) {
			System.out.println("年龄包括非数字");
		}
	}
}
