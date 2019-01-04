package com.wujf.study.io;

public class GTestSTDIN {
	
	public static void main(String[] args) {
		try {
			int a;
			while( ( a=System.in.read() ) != -1 )	{
				System.out.print( (char)a );
			}
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
