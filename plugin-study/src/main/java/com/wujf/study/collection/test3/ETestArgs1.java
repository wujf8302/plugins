package com.wujf.study.collection.test3;

/**
 * 可变参数
 * @author fj
 */
public class ETestArgs1 {
	
	public static void main(String[] args) {
		fun("",1,2,3);
	}

	public static void fun(String str,int... ag) {

		for(int i=0;i<ag.length;i++){
			System.out.println(ag[i]);
		}
	}	
}
