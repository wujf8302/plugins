package com.wujf.study.timer;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;

public class TimerTest {

	public static void main(String[] args) {
		boolean task = true;
		Timer timer = new Timer();
		//timer.schedule(new MyTask(), 1000, 2000);// 在1秒后执行此任务,每次间隔2秒,如果传递一个Data参数,就可以在某个固定的时间执行这个任务.
		
		timer.schedule(new MyTask(), new Date());
		while (task) {
			try {
				int ch = System.in.read();
				if (ch== 99) {//'c'
					timer.cancel();// 使用这个方法退出任务
					task = false;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	static class MyTask extends java.util.TimerTask {
		public void run() {
			System.out.println("________");
		}
	}
}
