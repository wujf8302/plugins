package com.wujf.study.swing.window;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class BTestJDialog1 {
	
	public static void main(String[] args) {

		JFrame firstFrame=null;
		//firstFrame=new JFrame();
		firstFrame=new JFrame("这是我的第一个窗口");
		firstFrame.setSize(330, 238);
		firstFrame.setLocation(100, 100);
		//firstFrame.setTitle("这是我的第一个窗口");
		firstFrame.setVisible(true);
		
		JDialog d1=new JDialog(firstFrame);
		d1.setSize(100,100);
		d1.setTitle("对话框1");
		//d1.setModal(true);
		d1.setVisible(true);
		
		System.out.println(123);
	}
}
