package com.wujf.study.thread.syn;

/**
 * 员工类.
 * @author fj
 */
public class PeopleSynThread extends Thread {

	private CompMoneyTable myTableTable = null;

	public PeopleSynThread(CompMoneyTable myTableTable, String name) {
		super(name);
		this.myTableTable = myTableTable;
	}

	// 重写run方法.
	public void run() {
		myTableTable.调发工资();
	}	
}
