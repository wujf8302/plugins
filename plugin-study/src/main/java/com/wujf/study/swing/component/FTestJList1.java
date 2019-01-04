package com.wujf.study.swing.component;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FTestJList1 extends JFrame {

	private JList list1 = null;

	public FTestJList1() {
		init();
	}

	private void init() {

		list1 = new JList();
		JScrollPane scrop1 = new JScrollPane(list1);

		DefaultListModel dm = new DefaultListModel();
		dm.addElement("大学");
		dm.addElement("高中");
		dm.addElement("初中");
		dm.addElement("小学");

		list1.setModel(dm);
		list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		
		list1.setSelectedIndex(2);
		
		LL ll=new LL();
		//list1.addListSelectionListener(ll);

		this.add(scrop1, BorderLayout.CENTER);
	}
	
	
	class LL implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent e) {
			if(e.getValueIsAdjusting()){
				
				
				System.out.println(list1.getSelectedValue());
				
				DefaultListModel dm=
					(DefaultListModel)list1.getModel();
				
				DefaultListModel dm2=new DefaultListModel();
				for(int i=0;i<dm.size();i++){
					if(i!=list1.getSelectedIndex()){
						dm2.addElement(dm.getElementAt(i));
					}
				}
				
				list1.setModel(dm2);
				
				
				
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
