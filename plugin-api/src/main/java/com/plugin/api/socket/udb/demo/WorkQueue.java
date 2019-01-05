package com.plugin.api.socket.udb.demo;

import java.util.LinkedList;
/***
 * 线程池.
 * @author wujf
 */
public class WorkQueue {
	
	private final int nThreads;
	private final PoolWorker threads[];
	private final LinkedList queue = new LinkedList();
	static int count = 0;
	
	public WorkQueue(int nThreads) {
		this.nThreads = nThreads;
		threads = new PoolWorker[nThreads];
		for (int i = 0; i < nThreads; i++) {
			threads[i] = new PoolWorker("workThread-" + i);
			threads[i].setDaemon(true);//将该线程标记为守护线程
			threads[i].start();
		}
	}
	
	public static void main(String args[]) throws Exception {
		WorkQueue workQueue = new WorkQueue(5);
		for (int i = 0; i < 1000; i++)
			workQueue.execute(new Runnable() {

				public void run() {
					System.out.println("workQueue test " + WorkQueue.count++);
				}

			});

		Thread.sleep(1000L);
		workQueue.destory();
	}

	public void execute(Runnable task) {
		synchronized (queue) {
			queue.addLast(task);
			queue.notify();
		}
	}
	
	public void destory() {
		for (int i = 0; i < nThreads; i++){
			if (threads[i] != null) {
				threads[i].stopWork();
				threads[i] = null;
			}
		}
		if (queue != null){
			queue.clear();
		}
	}
	
	private class PoolWorker extends Thread {
		
		private boolean running;

		public PoolWorker(String workName) {
			super(workName);
			this.running = true;
		}

		public void stopWork() {
			this.running = false;
			if (isAlive()) {
				interrupt();
				try {
					join(100L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void run() {
			Runnable task = null;
			while (running) {
				synchronized (queue) {
					while (queue.isEmpty() && running){
						try {
							queue.wait();
						} catch (InterruptedException ignored) {
							System.out.println("\u7EBF\u7A0B\u4E2D\u65AD");
						}
					}
					if (!queue.isEmpty()){
						task = (Runnable) queue.removeFirst();// 取出第一个任务并移除
					}
				}
				try {
					if (task != null) {
						task.run();
						task = null;
					}
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}

		public boolean isRunning() {
			return running;
		}
	}
}
