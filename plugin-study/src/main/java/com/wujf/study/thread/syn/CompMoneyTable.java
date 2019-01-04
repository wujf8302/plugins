package com.wujf.study.thread.syn;

import java.util.ArrayList;

/**
 * 工资处理类. 测式方法同步.
 * @author fj
 */
public class CompMoneyTable {

	private ArrayList 工资表 = null;

	// 构造方法.
	public CompMoneyTable() {
		init();
	}

	// 会计与出纳调用的方法;
	public synchronized void 调发工资() {

		if (Thread.currentThread().getName().equals("会计")) {
			// 会计调整工资.
			// 获取员工人数.
			int renCount = 工资表.size();

			for (int i = 0; i < renCount; i++) {

				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				ArrayList tempA = (ArrayList) 工资表.get(i);

				String name = (String) tempA.get(0);
				int 工资 = Integer.parseInt(tempA.get(1).toString());

				if (name.equals("张明")) {
					tempA.set(1, new Integer(工资 - 500));
				} else if (name.equals("王五")) {
					tempA.set(1, new Integer(工资 + 1500));
				} else if (name.equals("李四")) {
					tempA.set(1, new Integer(工资 + 2000));
				}

				工资表.set(i, tempA);
				System.out.println(name + "调整完毕");
			}
			System.out.println("0-----全部调整完毕!");

		} else if (Thread.currentThread().getName().equals("出纳")) {

			// 出纳发放工资.
			int renCount = 工资表.size();
			for (int i = 0; i < renCount; i++) {

				ArrayList tempA = (ArrayList) 工资表.get(i);
				String name = (String) tempA.get(0);
				String money = tempA.get(1).toString();

				System.out.println("" + name + " 实发工资为: " + money);
			}
		}
	}

	// 初始工资表.
	private void init() {
		工资表 = new ArrayList();

		ArrayList user1 = new ArrayList();
		user1.add("张明");
		user1.add(new Integer(6000));
		工资表.add(user1);

		ArrayList user2 = new ArrayList();
		user2.add("王五");
		user2.add(new Integer(8000));
		工资表.add(user2);

		ArrayList user3 = new ArrayList();
		user3.add("李四");
		user3.add(new Integer(6500));
		工资表.add(user3);
	}
}
