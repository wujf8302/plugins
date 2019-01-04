package com.wujf.study.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class ETestPrintStream1 {
	
	public static void main(String[] args) {

		File my1 = new File("c:/t4.txt");
		//System.out.println(my1.length());
		
		PrintStream ps=null;
		
		try {

			FileOutputStream fos=new FileOutputStream(my1);
			
			ps=new PrintStream(fos);
			
			int i=123;
			ps.println(i);
			
			String str=new String("中国共产党万岁!!!");
			ps.println(str);
			//ps.print(str.toString());
			
			javax.swing.JButton butt=new javax.swing.JButton("按钮");
			ps.println(butt);
			//ps.print(butt.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				ps.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}