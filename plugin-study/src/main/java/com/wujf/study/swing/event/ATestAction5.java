package com.wujf.study.swing.event;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ATestAction5 {

	JTextField tf1 = null;
	JTextField tf2 = null;
	JTextField tf3 = null;
	JButton button1 = null;

	public ATestAction5() {
		JFrame frame = new JFrame();

		JPanel pa1 = new JPanel();
		frame.add(pa1, BorderLayout.CENTER);

		tf1 = new JTextField(5);
		tf2 = new JTextField(5);
		tf3 = new JTextField(5);
		button1 = new JButton("+"); // 事件源
		button1.setActionCommand("+");
		
		ActionListener myAction1 = new MyAction1();
		ActionListener myAction2 = new MyAction1();
		//ActionListener myAction2 = new MyAction2();
		
		button1.addActionListener(myAction1);
		button1.addActionListener(myAction1);
		
		pa1.add(tf1);
		pa1.add(tf2);
		pa1.add(button1);
		pa1.add(tf3);

		frame.setSize(300, 300);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new ATestAction5();
	}

	class MyAction1 implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			 System.out.println(123);
		}
	}
	
	class MyAction2 implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			 System.out.println(456);
		}
	}
}
