package com.wujf.study.swing.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CTestGridLayout1 {
	
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
		firstFrame.add(p1,BorderLayout.CENTER);
		
		JButton[] but=new JButton[16];
		for(int i=0;i<16;i++){
			but[i]=new JButton( String.valueOf(i) );
		}
		
		GridLayout gl=new GridLayout(4,4);
		gl.setHgap(5);
		gl.setVgap(10);
		p1.setLayout(gl);
		
		for(int i=0;i<16;i++){
			p1.add(but[i]);
		}
		
		firstFrame.setVisible(true);
	}
}
