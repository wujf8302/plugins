package com.wujf.study.io;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DTestDataOutputStream1 {
	
	public static void main(String[] args) {

		File my1 = new File("c:/t3.txt");
		//System.out.println(my1.length());
		
		DataOutputStream dos=null;
		try {
			FileOutputStream fos=new FileOutputStream(my1);
			dos=new DataOutputStream(fos);
			
			dos.writeInt(1);
			dos.writeByte(2);
			dos.writeUTF("我要TM的激情!!!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				dos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

