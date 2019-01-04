package com.wujf.study.thread.destroy;

public class SubThread1 extends Thread {

	private boolean tag = false;

	@Override
	public void run() {

		for (int i = 0; i < 10; i++) {
			System.out.println(i);

			try {
				sleep(1000);
			} catch (InterruptedException e) {
				return;
			}

			if (tag == true) {
				return;
			}
		}
	}

	public boolean isTag() {
		return tag;
	}

	public void setTag(boolean tag) {
		this.tag = tag;
	}
}
