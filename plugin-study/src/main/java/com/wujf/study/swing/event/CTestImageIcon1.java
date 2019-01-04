package com.wujf.study.swing.event;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CTestImageIcon1 {

	public CTestImageIcon1() {
		JFrame frame = new JFrame();

		JPanel pa1 = new JPanel();
		frame.add(pa1, BorderLayout.CENTER);

		JButton button1 = new JButton("按钮");

		JLabel ll = new JLabel("");
		ImageIcon ii2 = new ImageIcon("./images/qqbanner3.jpg");
		ll.setIcon(ii2);
		pa1.add(ll);

		ImageIcon ii1 = new ImageIcon("./images/collapsed.gif");
		button1.setIcon(ii1);
		pa1.add(button1);

		frame.setSize(300, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Image img=frame.getToolkit().getImage("./images/qqicon.gif");
		frame.setIconImage(img);
		
		frame.setVisible(true);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new CTestImageIcon1();
	}

}
