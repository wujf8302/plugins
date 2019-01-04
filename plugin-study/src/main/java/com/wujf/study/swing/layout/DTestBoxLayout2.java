package com.wujf.study.swing.layout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;

public class DTestBoxLayout2 {
	
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
		
		Box hBox1=Box.createHorizontalBox();
		
		JButton[] but=new JButton[16];
		for(int i=0;i<16;i++){
			but[i]=new JButton( String.valueOf(i) );
		}
		
		hBox1.add(but[0]);
		hBox1.add( Box.createHorizontalStrut(13) );
		hBox1.add(but[1]);
		hBox1.add( Box.createHorizontalStrut(23) );
		hBox1.add(but[2]);
		hBox1.add(but[3]);
		hBox1.add(but[4]);
		hBox1.add(but[5]);
		hBox1.add(but[6]);
		hBox1.add(but[7]);
		hBox1.add(but[8]);
		hBox1.add(but[9]);
		hBox1.add(but[10]);
		hBox1.add(but[11]);
		hBox1.add(but[12]);
		hBox1.add(but[13]);
		hBox1.add(but[14]);
		hBox1.add(but[15]);
		
		firstFrame.add(hBox1);
		firstFrame.add(hBox1);
		firstFrame.setResizable(false);
		firstFrame.setVisible(true);
	}
}
