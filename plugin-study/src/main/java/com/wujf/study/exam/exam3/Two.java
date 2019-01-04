package com.wujf.study.exam.exam3;

public class Two extends One{
	public void say(){
		  System.out.println("two");
		  look();//成员方法可调用静态变量和方法
	  }
	
	public static void look(){
		System.out.println("look");
		
	}
	//传递多个同类型的参数
	public static  void see(String... s){
		System.out.println(s.length);
		
	}
	
	public static void main(String[] args) {

		 see("wwww");
		 see("wwww","ssss");

		 
		 
		 
		
		((One)new Two()).say();
		Two t = new Two();
		One o = (One)t;
		o.say();
		
		//String s = "";//不报错
//		String s = null;//报错
//		if(s==null|s.length()==0){//将|改为||
//			System.out.println("empty");
//		}else{
//			System.out.println(s);
//		}
		
		
		String s2 = "wujianfei";
		System.out.println(s2.charAt(2));
		int check = 4;
		if(check == s2.length()){//将=改为==
			
		}
		
		System.out.println(Boolean.valueOf("true"));
		System.out.println(new Boolean("true"));
		
		System.out.println(Integer.valueOf("1"));
		System.out.println(new Integer("1"));
		
		System.out.println(String.valueOf("1"));
		System.out.println(String.valueOf(new Integer(1)));
		Float f = new Float(3.14f);
		if(f>3){
			System.out.println(f);
		}
		//try finally
		//try catch finally
		//try catch catch finally
	}
}
