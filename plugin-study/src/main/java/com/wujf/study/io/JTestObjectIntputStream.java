package com.wujf.study.io;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class JTestObjectIntputStream {
	
	public static void main(String[] args) {

		People p1=null;
		ObjectInputStream ois=null;
		try {
			
			FileInputStream fis=new FileInputStream("tobj.ser");
			ois=new ObjectInputStream(fis);
			p1=(People)ois.readObject();
			
			System.out.println(p1.name);
			System.out.println(p1.age);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
	}
}
