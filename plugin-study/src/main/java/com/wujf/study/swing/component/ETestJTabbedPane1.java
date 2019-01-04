package com.wujf.study.swing.component;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class ETestJTabbedPane1 {

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

		// p1.add(ta);

		// JScrollPane scrop=new JScrollPane(ta);
		// JScrollPane scrop=new JScrollPane();
		// scrop.setViewportView(ta);
		JTextArea ta1 = new JTextArea("123");
		JScrollPane scrop1 = new JScrollPane(ta1);

		JTextArea ta2 = new JTextArea("abc");
		JScrollPane scrop2 = new JScrollPane(ta2);

		
		JTabbedPane tab=new JTabbedPane();
		tab.addTab("页签1", scrop1);
		tab.addTab("页签2", scrop2);
		
		
		firstFrame.add(tab);

		firstFrame.setVisible(true);
		firstFrame.validate();

	}

}
