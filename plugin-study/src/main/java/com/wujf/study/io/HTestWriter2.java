package com.wujf.study.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;


public class HTestWriter2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		File my1=new File("c:/t6.txt");

		FileWriter fw=null;
		
		try {

			fw=new FileWriter(my1);
			
			//osw.write('我要TM的激情!!!');
			fw.write("我要TM的激情!!!");
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
