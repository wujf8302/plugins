package com.wujf.study.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ITestBufferedReader1 {
	
	public static void main(String[] args) {

		File my1 = new File("c:/log.log");

		BufferedReader br = null;

		try {
			FileReader fr = new FileReader(my1);
			br = new BufferedReader(fr);

			String tmpStr = null;

			while ((tmpStr = br.readLine()) != null) {
				System.out.println(tmpStr);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
