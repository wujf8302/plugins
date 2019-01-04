package com.wujf.study.io;

import java.io.File;
import java.io.FileReader;

public class HTestReader2 {
	
	public static void main(String[] args) {

		File my1=new File("c:/t5.txt");

		FileReader fr=null;
		
		try {
			fr=new FileReader(my1);
			
			int data;
			while( (data=fr.read()) !=-1 ){
				System.out.print( (char)data );
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
