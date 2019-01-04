package com.wujf.study.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BTestInputStream1 {
	
	public static void main(String[] args) {

		File my1 = new File("c:/t1.txt");
		System.out.println(my1.length());
		
		FileInputStream is=null;
		try {

			is=new FileInputStream(my1);
			
			int data;
			while( (data=is.read()) !=-1  ){
				System.out.print( (char)data );
			}

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

