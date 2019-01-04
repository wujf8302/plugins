package com.plugin.api.thread;


/**
 * 守护线程
 */
public class DaemonThread extends Thread{

    /**
     * 守护线程是一类特殊的线程，
     * 它从属于创建它的线程，
     * 它和普通线程的区别在于它并不是应用程序的核心部分
     * 
     * 当一个应用程序的所有非守护线程终止运行时，应用程序也将终止，
     * 反之，只要有一个非守护线程在运行，应用程序就不会终止
     * 
     * 守护线程一般被用于在后台为其它线程提供服务
     */
    public DaemonThread(){
        setDaemon(true);  //守护线程
    }
    public void run(){
        try{
            System.out.println("1.-----------");
            Thread.sleep(200);
            System.out.println("2.-----------");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        DaemonThread thread = new DaemonThread();
        thread.start();
        System.out.println("3.-----------");
        try {
            //Thread.sleep(3000);//应用程序一旦结束，守护线程也随即结束
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
