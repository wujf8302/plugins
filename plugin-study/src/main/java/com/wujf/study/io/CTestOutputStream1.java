package com.wujf.study.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CTestOutputStream1 {
	
	public static void main(String[] args) {

		File my1 = new File("c:/t2.txt");
		//System.out.println(my1.length());
		
		OutputStream os=null;
		
		try {

			os=new FileOutputStream(my1);
			//os=new FileOutputStream(my1,true);//
			
			//os.write('!');
			//os.write('b');
			//os.write('c');
			
			//byte[] b={'!','@','?'};
			//os.write(b);
			
			byte[] b={'!','@','?'};
			os.write(b,1,2);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

