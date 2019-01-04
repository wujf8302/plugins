package com.wujf.study.io;

import java.io.File;
import java.io.IOException;

public class ATestFile1 {
	
	public static void main(String[] args) {
		
		File myFile1=new File("c:/abc.txt");
		
		System.out.println( myFile1.exists() );
		System.out.println( myFile1.getName() );
		System.out.println( myFile1.getAbsolutePath() );
		System.out.println( myFile1.getParent() );
		
		myFile1.delete();
		try {
			myFile1.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
