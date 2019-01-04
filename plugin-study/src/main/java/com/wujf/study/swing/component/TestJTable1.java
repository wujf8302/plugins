package com.wujf.study.swing.component;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class TestJTable1 {

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

		JTable tab = new JTable();
		JScrollPane scrop = new JScrollPane(tab);

		Vector<String> title = new Vector<String>();
		title.add("编号");
		title.add("姓名");
		title.add("成绩");

		Vector<Vector> data = new Vector<Vector>();
		Vector<String> line1 = new Vector<String>();
		line1.add("00001");
		line1.add("小张");
		line1.add("89");
		Vector<String> line2 = new Vector<String>();
		line2.add("00002");
		line2.add("小李");
		line2.add("80");
		Vector<String> line3 = new Vector<String>();
		line3.add("00003");
		line3.add("小王");
		line3.add("100");
		data.add(line1);
		data.add(line2);
		data.add(line3);

		DefaultTableModel dtm = new DefaultTableModel(data, title);
		tab.setModel(dtm);

		tab.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		tab.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						if (e.getValueIsAdjusting()) {

						}
					}
				});

		tab.getSelectedRow();
		tab.getSelectedRows();

		firstFrame.add(scrop, BorderLayout.CENTER);

		firstFrame.setVisible(true);
		firstFrame.validate();

	}

}
