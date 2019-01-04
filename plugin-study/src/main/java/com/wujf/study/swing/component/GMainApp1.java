package com.wujf.study.swing.component;

import javax.swing.JFrame;

public class GMainApp1 {

	private GTestJOptionPane1 ftj1 = null;

	public GMainApp1() {
		ftj1 = new GTestJOptionPane1();

		ftj1.setSize(400, 400);
		ftj1.setLocationRelativeTo(null);
		ftj1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ftj1.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new GMainApp1();
	}

}
