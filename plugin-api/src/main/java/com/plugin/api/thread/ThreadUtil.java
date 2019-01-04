package com.plugin.api.thread;

/**
 * 线程工具类
 */
public class ThreadUtil {

    
    private int num = 100;
    private String s = new String();
    
    public static void main(String[] args) {
        try {
            Thread.sleep(1000); //主线程睡眠1秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        ThreadUtil util = new ThreadUtil();
        util.test();
    }
    
    public void test() {
        ThreadUseExtends thread1 = new ThreadUseExtends(); 
        thread1.start();
        Thread thread2 = new Thread(new ThreadUseRunnable());
        thread2.start();
        
        Thread thread3 = new Thread(new Runnable(){
            public void run() {
                System.out.println("使用匿名类生成一个给程");
            }
        });
    }
    
    public class  ThreadUseRunnable implements Runnable{
        public void run() {
            while (true) {
                synchronized (s) { //当有一个明确的对象作为锁时 同步块
                    if (num > 0) {
                        try {
                            Thread.sleep(10);
                        } catch (Exception e) {
                            e.getMessage();
                        }
                        System.out.println(Thread.currentThread().getName()+ " " + num--);
                    }
                }
            }
        }
    }

    public class ThreadUseExtends extends Thread{
        boolean b = true;
        public void run() {
            while (true) {
                synchronized (this) {//用this作为锁时 同步块
                    if (num > 0) {
                        try {
                            Thread.sleep(10);
                        } catch (Exception e) {
                            e.getMessage();
                        }
                        System.out.println(Thread.currentThread().getName()+ " " + num--);
                    }else{
                        b = false;
                    }
                }
            }
        }
        
        /**
         * 同步方法
         */
        public synchronized void synchronizedMethod(){
            boolean b = true;
            while (b) {
                if (num > 0) {
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.getMessage();
                    }
                    System.out.println(Thread.currentThread().getName()+ " " + num--);
                }else{
                    b = false;
                }
            }
        }
    }

}

