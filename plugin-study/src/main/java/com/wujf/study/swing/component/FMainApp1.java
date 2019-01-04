package com.wujf.study.swing.component;

import javax.swing.JFrame;

public class FMainApp1 {

	private FTestJList1 ftj1 = null;

	public FMainApp1() {
		ftj1 = new FTestJList1();

		ftj1.setSize(400, 400);
		ftj1.setLocationRelativeTo(null);
		ftj1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ftj1.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new FMainApp1();
	}

}
