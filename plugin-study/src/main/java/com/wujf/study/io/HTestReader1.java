package com.wujf.study.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class HTestReader1 {
	
	public static void main(String[] args) {

		File my1=new File("c:/t5.txt");

		InputStreamReader isr=null;
		
		try {

			FileInputStream fis=new FileInputStream(my1);
			isr=new InputStreamReader(fis);
			
			int data;
			while( (data=isr.read()) !=-1 ){
				System.out.print( (char)data );
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				isr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
