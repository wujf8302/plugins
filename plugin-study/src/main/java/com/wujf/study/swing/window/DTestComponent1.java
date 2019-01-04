package com.wujf.study.swing.window;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class DTestComponent1 {

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
		
		JLabel lll=new JLabel("输入号码:");
		JTextField tt=new JTextField(15);
		JPasswordField pf=new JPasswordField(15);
		JButton button=new JButton("确定");
		
		p1.add(lll);
		p1.add(tt);
		p1.add(button);
		p1.add(pf);
		
		firstFrame.setVisible(true);
	}
}
