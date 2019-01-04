package com.wujf.study.exam.exam2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *写一个方法，返回包括字符串中所有奇数位字符的字符串（要求字符串从键盘输入，写完整函数）。
 */
public class xdw1 {
	    public static String getOddNumber(String str){
	        StringBuffer sb = new StringBuffer();
	        for(int i=0;i<str.length();i+=2){
	            sb.append(str.substring(i, i+1));
	        }
	        return sb.toString();
	    }
	    
	    public static void main(String[] args){
	        System.out.print("请输入一个字符串：");
	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        try{
	            String str = br.readLine();
	            System.out.println("结果： " + xdw1.getOddNumber(str));
	        }catch(IOException e){
	            e.printStackTrace();
	        }
	    }

}
