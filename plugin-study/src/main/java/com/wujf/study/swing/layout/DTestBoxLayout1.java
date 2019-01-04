package com.wujf.study.swing.layout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;

public class DTestBoxLayout1 {
	
	public static void main(String[] args) {

		JFrame firstFrame=null;
		//firstFrame=new JFrame();
		firstFrame=new JFrame("这是我的第一个窗口");
		firstFrame.setSize(330, 238);
		firstFrame.setLocation(100, 100);
		//firstFrame.setTitle("这是我的第一个窗口");
		
		/*
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
		*/
		
		Box vBox1=Box.createVerticalBox();
		
		JButton[] but=new JButton[16];
		for(int i=0;i<16;i++){
			but[i]=new JButton( String.valueOf(i) );
		}
		
		vBox1.add(but[0]);
		vBox1.add( Box.createVerticalStrut(13) );
		vBox1.add(but[1]);
		vBox1.add( Box.createVerticalStrut(23) );
		vBox1.add(but[2]);
		vBox1.add(but[3]);
		vBox1.add(but[4]);
		vBox1.add(but[5]);
		vBox1.add(but[6]);
		vBox1.add(but[7]);
		vBox1.add(but[8]);
		vBox1.add(but[9]);
		vBox1.add(but[10]);
		vBox1.add(but[11]);
		vBox1.add(but[12]);
		vBox1.add(but[13]);
		vBox1.add(but[14]);
		vBox1.add(but[15]);
		
		firstFrame.add(vBox1);
		firstFrame.setResizable(false);
		
		firstFrame.setVisible(true);
	}
}
