package com.wujf.study.inner;

public class Outside2 {
	
    public static class inner{
    	public void say(){
    		System.out.println("inner");
    	}
    }
    public void say(){
		System.out.println("outside");
	}
}
