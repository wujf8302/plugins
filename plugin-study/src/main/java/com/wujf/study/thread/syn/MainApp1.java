package com.wujf.study.thread.syn;

public class MainApp1 {

	public static void main(String[] args) {

		CompMoneyTable cmt = new CompMoneyTable();

		PeopleSynThread pst1 = new PeopleSynThread(cmt, "会计");
		PeopleSynThread pst2 = new PeopleSynThread(cmt, "出纳");
		
		pst1.start();
		pst2.start();

	}
}
