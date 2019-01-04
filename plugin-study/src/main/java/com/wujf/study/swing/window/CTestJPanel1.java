package com.wujf.study.swing.window;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CTestJPanel1 {
	
	public static void main(String[] args) {

		JFrame firstFrame=null;
		//firstFrame=new JFrame();
		firstFrame=new JFrame("这是我的第一个窗口");
		firstFrame.setSize(330, 238);
		firstFrame.setLocation(100, 100);
		//firstFrame.setTitle("这是我的第一个窗口");
		
		JPanel p1=new JPanel();
		p1.setSize(300, 200);
		p1.setBackground( Color.RED );
		
		firstFrame.add(p1,BorderLayout.NORTH);
		
		firstFrame.setVisible(true);
	}
}
