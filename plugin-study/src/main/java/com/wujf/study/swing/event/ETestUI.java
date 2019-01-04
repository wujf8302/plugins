package com.wujf.study.swing.event;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

public class ETestUI {

	public ETestUI() {
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JFrame frame = new JFrame();

		JPanel pa1 = new JPanel();
		frame.add(pa1, BorderLayout.CENTER);

		JButton button1 = new JButton("按钮");

		JLabel ll = new JLabel("");
		ImageIcon ii2 = new ImageIcon("./images/qqbanner3.jpg");
		ll.setIcon(ii2);
		pa1.add(ll);

		Border border1 = BorderFactory.createLineBorder(Color.RED);
		Border border2 = BorderFactory.createTitledBorder("在线用线");
		Border border3 = BorderFactory.createCompoundBorder(border1, border2);
		// ll.setBorder(border1);
		//ll.setBorder(border2);
		ll.setBorder(border3);

		ImageIcon ii1 = new ImageIcon("./images/collapsed.gif");
		button1.setIcon(ii1);
		pa1.add(button1);

		frame.setSize(400, 300);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Image img = frame.getToolkit().getImage("./images/qqicon.gif");
		frame.setIconImage(img);

		frame.setVisible(true);

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ETestUI();
	}

}
