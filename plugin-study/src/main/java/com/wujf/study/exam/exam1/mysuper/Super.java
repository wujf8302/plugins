package com.wujf.study.exam.exam1.mysuper;


public class Super extends SuperCalc{
	public static int ww(int a,int b){
		return b;
		//int c = super.ww(a,b);//不能在静态方法中使用super
	}
}
