package com.wujf.study.inner;

public class Test {
	
	public static void main(String[] args) {
		Outside  o = new Outside();
		o.say();
		
		Outside.inner i = o.new inner();//成员内部类
		i.say();
	}
}
