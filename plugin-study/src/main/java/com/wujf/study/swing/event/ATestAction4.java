package com.wujf.study.swing.event;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ATestAction4 {

	JTextField tf1 = null;
	JTextField tf2 = null;
	JTextField tf3 = null;
	JButton button1 = null;
	JButton button2 = null;
	JButton button3 = null;

	public ATestAction4() {
		JFrame frame = new JFrame();

		JPanel pa1 = new JPanel();
		frame.add(pa1, BorderLayout.CENTER);

		tf1 = new JTextField(5);
		tf2 = new JTextField(5);
		tf3 = new JTextField(5);
		button1 = new JButton("+"); // 事件源
		button1.setActionCommand("+");
		
		button2 = new JButton("-"); // 事件源
		button2.setActionCommand("-");
		
		button3 = new JButton("*"); // 事件源
		button3.setActionCommand("*");
		
		ActionListener myAction1 = new MyAction();
		
		button1.addActionListener(myAction1);
		button2.addActionListener(myAction1);
		button3.addActionListener(myAction1);

		pa1.add(tf1);
		pa1.add(tf2);
		pa1.add(button1);
		pa1.add(button2);
		pa1.add(button3);
		pa1.add(tf3);

		frame.setSize(300, 300);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		new ATestAction4();
	}

	class MyAction implements ActionListener {

		public void actionPerformed(ActionEvent ae) {
			
			 String st1=tf1.getText();
			 String st2=tf2.getText();
			 String equStr="";
			 
			 
			 if(ae.getActionCommand().equals("+")){
				 int sum=Integer.parseInt(st1)+Integer.parseInt(st2);
				 equStr=String.valueOf(sum);
			 }
			 else if(ae.getActionCommand().equals("-")){
				 int sum=Integer.parseInt(st1)-Integer.parseInt(st2);
				 equStr=String.valueOf(sum);
			 }
			 else if(ae.getActionCommand().equals("*")){
				 int sum=Integer.parseInt(st1)*Integer.parseInt(st2);
				 equStr=String.valueOf(sum);
			 }
			 tf3.setText( equStr );
		}
	}
}
