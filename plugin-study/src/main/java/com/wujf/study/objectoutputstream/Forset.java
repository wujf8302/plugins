package com.wujf.study.objectoutputstream;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Forset implements Serializable {
	private Tree tree = new Tree();

	public static void main(String[] args) {
		Forset f = new Forset();
		try {
			FileOutputStream fs = new FileOutputStream("Forset.ser");
			ObjectOutputStream os = new ObjectOutputStream(fs);
			os.writeObject(f);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
