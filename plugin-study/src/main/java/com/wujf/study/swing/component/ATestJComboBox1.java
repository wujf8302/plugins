package com.wujf.study.swing.component;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ATestJComboBox1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		JFrame firstFrame = null;
		// firstFrame=new JFrame();
		firstFrame = new JFrame("这是我的第一个窗口");
		firstFrame.setSize(330, 238);
		firstFrame.setLocation(100, 100);
		// firstFrame.setTitle("这是我的第一个窗口");

		JPanel p1 = new JPanel();
		p1.setSize(300, 200);
		// p1.setBackground(Color.RED);
		firstFrame.add(p1, BorderLayout.CENTER);

		
		JComboBox cb=new JComboBox();
		cb.addItem("大学");
		cb.addItem("高中");
		cb.addItem("初中");
		cb.addItem("小学");
		cb.setEditable(true);
		
		Object obj=cb.getSelectedItem();
		System.out.println(obj);
		
		
		
		p1.add(cb);
		
		
		
		
		
		firstFrame.setVisible(true);
		firstFrame.validate();

	}

}
