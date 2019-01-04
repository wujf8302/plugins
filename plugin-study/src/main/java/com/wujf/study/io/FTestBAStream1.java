package com.wujf.study.io;

import java.io.ByteArrayInputStream;

public class FTestBAStream1 {
	
	public static void main(String[] args) {

		byte[] b={'a','b','c'};
		
		ByteArrayInputStream bais = null;

		try {
			bais = new ByteArrayInputStream(b);
			
			System.out.println( (char)bais.read());
			System.out.println( (char)bais.read());
			System.out.println( (char)bais.read());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
