package com.wujf.study.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BTestInputStream3 {
	
	public static void main(String[] args) {

		File my1 = new File("c:/t1.txt");
		//System.out.println(my1.length());
		
		FileInputStream is=null;
		try {
			is=new FileInputStream(my1);
			
			byte[] b=new byte[20];
			int count=is.read(b,5,8);
			
			String str=new String(b,5,count);
			
			System.out.println(str);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

