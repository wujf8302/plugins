package com.wujf.study.io;

import java.io.File;
import java.io.FileFilter;

public class ATestFile3 {
	
	public static void main(String[] args) {

		File myFolder1 = new File("c:/");

		// 1.
		// File[] files=myFolder1.listFiles();
		// for(int i=0;i<files.length;i++){
		// System.out.println( files[i].getName() );
		// }

		// 2.
		// String[] names = myFolder1.list();
		// for (int i = 0; i < names.length; i++) {
		// System.out.println(names[i]);
		// }

		// 3.
		// String[] names = myFolder1.list();
		// for (int i = 0; i < names.length; i++) {
		// if ( names[i].endsWith(".log") ) {
		// System.out.println(names[i]);
		// }
		// }

		// 4.
		File[] files = myFolder1.listFiles(new Ab());

		for (int i = 0; i < files.length; i++) {
			System.out.println(files[i].getName());
		}
	}
}

class Ab implements FileFilter {
	public boolean accept(File pathname) {
		// if (pathname.getName().endsWith(".log")) {
		if (pathname.isDirectory()) {
			return true;
		} else {
			return false;
		}
	}
}
