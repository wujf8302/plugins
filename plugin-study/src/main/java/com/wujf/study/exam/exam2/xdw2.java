package com.wujf.study.exam.exam2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
/**
用输入/输出，用键盘输入姓名、电话，将写入文件，
当输入done时表示输入结束。输入结束后创输出文件，并显示所有文件内容。
如：（KaKa   1234567
     Kyo    7654321
     Archer 1315179）
 */
public class xdw2 {
	public static void main(String[] args){
        BufferedWriter bw = null;
        BufferedReader br = null;
        try {  
            bw = new BufferedWriter(new FileWriter("d:/test3.txt",true));
            while(true){
                System.out.print("请输入姓名电话(如：姓名  电话)：");
                br = new BufferedReader(new InputStreamReader(System.in));
                String str = br.readLine();
                if("done".equals(str)){
                    break;
                }
                bw.write(str);
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try{
            	if(br!=null){
                 	br.close();
                 }
                if(bw!=null){
                	bw.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }    
    }
}
