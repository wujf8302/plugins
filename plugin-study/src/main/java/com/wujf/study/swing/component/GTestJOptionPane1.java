package com.wujf.study.swing.component;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GTestJOptionPane1 extends JFrame {

	private JButton but1 = null;

	private JButton but2 = null;

	public GTestJOptionPane1() {
		init();
	}

	private void init() {
		but1 = new JButton("提示框");
		but2 = new JButton("确认框");

		AL al = new AL();

		but1.addActionListener(al);
		but2.addActionListener(al);

		JPanel p = new JPanel();
		p.add(but1);
		p.add(but2);

		this.setLayout(new BorderLayout());
		this.add(p, BorderLayout.CENTER);
	}

	class AL implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == but1) {

				JOptionPane.showMessageDialog(GTestJOptionPane1.this, "删除成功",
						"删除提示", JOptionPane.INFORMATION_MESSAGE);

				// JOptionPane.showMessageDialog(GTestJTable1.this, "删除成功",
				// "删除提示",
				// JOptionPane.ERROR_MESSAGE);

				// JOptionPane.showMessageDialog(GTestJTable1.this, "删除成功",
				// "删除提示",
				// JOptionPane.WARNING_MESSAGE);

			} else {

				if( JOptionPane.showConfirmDialog(null, "您真要这样做吗?", "确认框",
						JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION){
					
					JOptionPane.showMessageDialog(GTestJOptionPane1.this, "您已经确定了",
							"确定提示",
							 JOptionPane.WARNING_MESSAGE);
					
				}

			}
		}

	}

	public static void main(String[] args) {
		GTestJOptionPane1 gj = new GTestJOptionPane1();

		gj.setSize(330, 238);
		gj.setLocationRelativeTo(null);
		gj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gj.setVisible(true);
	}

}
