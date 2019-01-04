package com.wujf.study.swing.component;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CTestJScrollPane1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		JFrame firstFrame = null;
		// firstFrame=new JFrame();
		firstFrame = new JFrame("这是我的第一个窗口");
		firstFrame.setSize(330, 238);
		firstFrame.setLocation(100, 100);
		firstFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// firstFrame.setTitle("这是我的第一个窗口");

		JPanel p1 = new JPanel();
		p1.setSize(300, 200);
		// p1.setBackground(Color.RED);
		firstFrame.add(p1, BorderLayout.CENTER);

		JTextArea ta = new JTextArea(3, 10);
		// p1.add(ta);

		// JScrollPane scrop=new JScrollPane(ta);
		// JScrollPane scrop=new JScrollPane();
		// scrop.setViewportView(ta);

		JScrollPane scrop = new JScrollPane(ta,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		p1.add(scrop);

		firstFrame.setVisible(true);
		firstFrame.validate();

	}

}
