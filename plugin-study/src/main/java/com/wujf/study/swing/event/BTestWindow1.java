package com.wujf.study.swing.event;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BTestWindow1 {

	JTextField tf1 = null;
	JTextField tf2 = null;
	JTextField tf3 = null;
	JButton button1 = null;

	public BTestWindow1() {
		JFrame frame = new JFrame();

		JPanel pa1 = new JPanel();
		frame.add(pa1, BorderLayout.CENTER);

		tf1 = new JTextField(5);
		tf2 = new JTextField(5);
		tf3 = new JTextField(5);
		button1 = new JButton(" = "); // 事件源
		ActionListener myAction1 = new MyAction();
		button1.addActionListener(myAction1);

		pa1.add(tf1);
		pa1.add(tf2);
		pa1.add(button1);
		pa1.add(tf3);

		frame.setSize(300, 300);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		frame.addWindowListener(new MyWindow());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new BTestWindow1();
	}

	class MyAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String st1=tf1.getText();
			String st2=tf2.getText();
			int sum=Integer.parseInt(st1)+Integer.parseInt(st2);
			tf3.setText( String.valueOf(sum) );
		}

	}
	
	
	class MyWindow implements WindowListener{

		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			System.out.println("windowActivated");
		}

		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			System.out.println("windowClosed");
		}

		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			System.out.println("windowClosing");
			
			System.exit(0);
			
		}

		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			System.out.println("windowDeactivated");
		}

		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			System.out.println("windowDeiconified");
		}

		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			System.out.println("windowIconified");
		}

		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			System.out.println("windowOpened");
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
