package com.wujf.study.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class ITestBufferedWriter1 {
	
	public static void main(String[] args) {

		File my1=new File("c:/log2.log");

		BufferedWriter bw=null;
		
		try {
			FileWriter fw=new FileWriter(my1);
			bw=new BufferedWriter(fw);
			
			bw.write("[2008-11-05] 测试1");
			bw.newLine();
			
			bw.write("[2008-11-05] 测试2");
			bw.newLine();
			
			bw.write("[2008-11-05] 测试3");
			bw.newLine();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
