package com.plugin.api;

import java.util.LinkedList;

import org.apache.log4j.Logger;

public class WorkUtil {

	private static Logger log = Logger.getLogger(WorkUtil.class);
	
	private PoolWorker[] threads = null;// 用数组实现线程池
	private LinkedList queue = null;    // 任务队列
	private boolean isQueueEmptyDestroyWorkQueue = false;

	public WorkUtil(int nThreads) {
		queue = new LinkedList();
		threads = new PoolWorker[nThreads];

		for (int i = 0; i < nThreads; i++) {
			threads[i] = new PoolWorker("TaskThread-" + (i+1));
			threads[i].start();         // 启动所有工作线程
		}
	}

	public void execute(Runnable r) {   // 执行任务
		synchronized (queue) {
			queue.addLast(r);
			queue.notify();
		}
	}

	/**
	 * 所有线程是否都处于等待状态
	 * @return false不是 true是
	 */
	public boolean isAllThreadWait(){
		
		if(threads != null && threads.length > 0){
			
			for (int i = 0; i < threads.length; i++) {
				if(threads[i] != null){
					if(!threads[i].isWait()){//false运行 true等待
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public void destroy(){
				
		if(threads != null && threads.length > 0){
			
			//下一轮不再进入循环
			for (int i = 0; i < threads.length; i++) {
				if(threads[i] != null){
					threads[i].destroy();
				}	
			}
			
			//唤醒等待的线程
			for (int i = 0; i < threads.length; i++) {
				final String name = threads[i].getThreadName();
				
				execute(new Runnable(){
					public void run() {
						log.info("销毁" + name + "线程");
					}
				});
				
				threads[i] = null;
			}
		}

		threads = null;

	}

	public class PoolWorker extends Thread {// 工作线程类
	
		private String threadName;
		private boolean run = true;  //true 运行 false停止
		private boolean wait = false;//false运行 true等待
		
		public PoolWorker(String threadName){
			this.threadName = threadName;
		}
		
		public void destroy(){
			run = false;
		}

		public void run() {
			Runnable r;
			while(run) {
				
				wait = false;
				
				synchronized (queue) {
					while (queue.isEmpty()) {          // 如果任务队列中没有任务，等待
						try {
							wait = true;
							queue.wait();
						} catch (InterruptedException ignored) {
						}
					}
					r = (Runnable)queue.removeFirst(); // 有任务时，取出任务
				}
				
				try {
					r.run();// 执行任务
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
			}
		}

		public boolean isWait() {
			return wait;
		}

		public void setWait(boolean wait) {
			this.wait = wait;
		}

		public String getThreadName() {
			return threadName;
		}

		public void setThreadName(String threadName) {
			this.threadName = threadName;
		}
	}

	public static void main(String args[]) {
		WorkUtil wq = new WorkUtil(10);// 10个工作线程     

		for (int i = 0; i < 20; i++) {// 20个任务
			//r[i] = new Runnable();
			wq.execute(new Runnable(){

				public void run() {
					
					String name = Thread.currentThread().getName();
					try {
						Thread.sleep(100);// 模拟任务执行的时间
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(name + " executed OK");
				}
				
			});
		}
	}

	public boolean isQueueEmpty() {
		return queue.isEmpty();
	}

	public boolean isQueueEmptyDestroyWorkQueue() {
		return isQueueEmptyDestroyWorkQueue;
	}

	public void setQueueEmptyDestroyWorkQueue(boolean isQueueEmptyDestroyWorkQueue) {
		this.isQueueEmptyDestroyWorkQueue = isQueueEmptyDestroyWorkQueue;
	}
}
