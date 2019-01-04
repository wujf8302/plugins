package com.wujf.study.thread.syn;

/**
 * 用户类.测试方法块同步.
 * @author fj
 */
public class StudentThread extends Thread {

	// 重写run方法.
	public void run() {

		synchronized (MobifComp.电话卡) {

			if (Integer.parseInt(MobifComp.电话卡) > 0) {
				int tempInt = Integer.parseInt(MobifComp.电话卡);

				try {
					sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				System.out.println("本次充值成功!本次充值为:" + tempInt);
				MobifComp.电话卡 = String.valueOf(Integer.parseInt(MobifComp.电话卡)
						- tempInt);
			} else {
				System.out.println("充值失败!余额不足!");
			}
		}
	}
}
