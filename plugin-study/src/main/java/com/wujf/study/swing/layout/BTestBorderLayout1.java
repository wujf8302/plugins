package com.wujf.study.swing.layout;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BTestBorderLayout1 {
	
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
		
		JButton bu1=new JButton("按钮1");
		JButton bu2=new JButton("按钮2");
		JButton bu3=new JButton("按钮3");
		JButton bu4=new JButton("按钮4");
		JButton bu5=new JButton("按钮5");
		
		BorderLayout bl=new BorderLayout();
		
		p1.setLayout(bl);
		
		p1.add(bu1,BorderLayout.NORTH);
		p1.add(bu2,BorderLayout.SOUTH);
		p1.add(bu3,BorderLayout.CENTER);
		p1.add(bu4,BorderLayout.WEST);
		p1.add(bu5,BorderLayout.EAST);
		
		firstFrame.setVisible(true);
	}
}
