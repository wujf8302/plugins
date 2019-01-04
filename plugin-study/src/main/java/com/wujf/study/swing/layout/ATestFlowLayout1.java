package com.wujf.study.swing.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ATestFlowLayout1 {
	
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
		
		JButton[] but=new JButton[20];
		for(int i=0;i<20;i++){
			but[i]=new JButton( String.valueOf(i) );
		}
		
		FlowLayout fl=new FlowLayout();
		p1.setLayout(fl);
		
		for(int i=0;i<20;i++){
			p1.add(but[i]);
		}
		
		firstFrame.setVisible(true);
	}
}
