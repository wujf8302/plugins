package com.wujf.study.io;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class JTestObjectOutputStream1 implements Serializable {
	public static void main(String[] args) {

		People p1 = new People();
		p1.name = "小张";
		p1.age = 20;

		ObjectOutputStream oos = null;
		try {
			// FileOutputStream fos=new FileOutputStream("./tmp/tobj.txt");
			oos = new ObjectOutputStream(new FileOutputStream("tobj.ser"));
			oos.writeObject(p1);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}
}
