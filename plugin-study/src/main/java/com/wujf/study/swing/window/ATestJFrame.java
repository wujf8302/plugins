package com.wujf.study.swing.window;

import javax.swing.JFrame;

public class ATestJFrame {
	
	public static void main(String[] args) {

		JFrame firstFrame=null;
		
		//firstFrame=new JFrame();
		firstFrame=new JFrame("这是我的第一个窗口");
		
		firstFrame.setSize(330, 238);
		
		firstFrame.setLocation(100, 100);
		
		//firstFrame.setTitle("这是我的第一个窗口");
		
		firstFrame.setVisible(true);

	}
}
