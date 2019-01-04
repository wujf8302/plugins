package com.wujf.study.io;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DTestDataInputStream1 {
	
	public static void main(String[] args) {

		File my1 = new File("c:/t3.txt");
		//System.out.println(my1.length());
		
		DataInputStream dis=null;
		
		try {
			FileInputStream fis=new FileInputStream(my1);
			dis=new DataInputStream(fis);
			System.out.println(dis.readInt());
			System.out.println(dis.readByte());
			System.out.println(dis.readUTF());
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

