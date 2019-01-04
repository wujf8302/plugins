package com.wujf.study.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class HTestWriter1 {
	
	public static void main(String[] args) {

		File my1=new File("c:/t6.txt");

		OutputStreamWriter osw=null;
		
		try {

			FileOutputStream fos=new FileOutputStream(my1);
			osw=new OutputStreamWriter(fos);
			
			//osw.write('我要TM的激情!!!');
			osw.write("我要TM的激情!!!");
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				osw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
