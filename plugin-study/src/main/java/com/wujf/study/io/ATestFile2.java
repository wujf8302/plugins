package com.wujf.study.io;

import java.io.File;

public class ATestFile2 {
	
	public static void main(String[] args) {
		
		//File myFolder1=new File("c:/WINDOWS");
		//System.out.println(myFolder1.isDirectory());
		
		//File myFolder1=new File("c:/abc");
		//File myFolder1=new File("c:/abc/abc.txt");
		//File myFolder2=new File(myFolder1,"abc.txt");
		

		// File myFolder1=new File("c:/abc");
		// if( !myFolder1.exists() ){
		// myFolder1.mkdir();
		//		}
		
		File myFolder1=new File("c:/a/b/c");
		//System.out.println( myFolder1.mkdir() );
		myFolder1.mkdirs();
	}
}
