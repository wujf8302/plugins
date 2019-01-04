package com.wujf.study.swing.component;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class TestJList1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		JFrame firstFrame = null;
		// firstFrame=new JFrame();
		firstFrame = new JFrame("这是我的第一个窗口");
		firstFrame.setSize(330, 238);
		firstFrame.setLocation(100, 100);
		// firstFrame.setTitle("这是我的第一个窗口");

		JPanel p1 = new JPanel();
		p1.setSize(300, 200);
		// p1.setBackground(Color.RED);
		firstFrame.add(p1, BorderLayout.CENTER);

		JList list = new JList();
		// setListData();
		// setModel();

		DefaultListModel dlm = new DefaultListModel();
		// dlm.add(index, element);
		// dlm.addElement(obj);
		// dlm.get(index);
		// dlm.getElementAt(index);

		dlm.addElement("a");
		dlm.addElement("b");
		dlm.addElement("c");
		dlm.addElement("d");
		dlm.addElement("e");

		list.setModel(dlm);
		p1.add(list);

		list.setSelectedIndex(0);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
			}
		});

		// list.setModel(list.getModel());

		// firstFrame.add(p1);

		firstFrame.setVisible(true);
		firstFrame.validate();

	}

}
