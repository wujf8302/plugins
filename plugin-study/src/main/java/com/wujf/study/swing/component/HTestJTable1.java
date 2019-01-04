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

public class HTestJTable1 extends JFrame {

	private JTable table1 = null;

	public HTestJTable1() {
		init();
	}

	private void init() {

		table1 = new JTable();
		JScrollPane scrop1 = new JScrollPane(table1);

		Vector title = new Vector();
		title.add("编号");
		title.add("姓名");
		title.add("年龄");

		Vector data = new Vector();

		Vector line1 = new Vector();
		line1.add("00001");
		line1.add("小林");
		line1.add("25");
		data.add(line1);

		Vector line2 = new Vector();
		line2.add("00002");
		line2.add("小张");
		line2.add("24");
		data.add(line2);

		Vector line3 = new Vector();
		line3.add("00003");
		line3.add("小杨");
		line3.add("26");
		data.add(line3);

		DefaultTableModel dtm = new DefaultTableModel(data, title) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}

		};

		table1.setModel(dtm);
		
		table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
		LL ll=new LL();
		table1.getSelectionModel().addListSelectionListener(ll);

		this.add(scrop1, BorderLayout.CENTER);
		
	}
	
	
	class LL implements ListSelectionListener{

		public void valueChanged(ListSelectionEvent e) {
			
			if(e.getValueIsAdjusting()){
				
				//System.out.println(123);
				int row=table1.getSelectedRow();
				
				for(int i=0;i<3 ;i++){
					System.out.print("  "+table1.getValueAt(row, i));
				} 
				System.out.println();

				
			}
			
		}
		
	}

}
