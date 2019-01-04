package com.wujf.study.inner;

public class Test2 {
	
	public static void main(String[] args) {
		Outside2  o = new Outside2();
		o.say();
		
		Outside2.inner i = new Outside2.inner();//静态内部类
		i.say();
	}
}
