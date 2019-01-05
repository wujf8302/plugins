package com.plugin.api.socket.udb.demo;

import java.net.DatagramPacket;
import java.util.LinkedList;

import org.apache.log4j.Logger;
/**
 * 
 * @author wujf
 */
public abstract class UDPWork implements Runnable{

	private static Logger log = Logger.getLogger(UDPWork.class);
	protected LinkedList dataQueue;
	
	public UDPWork(){
	    this.dataQueue = new LinkedList();
	}
	
	public abstract void receive(DatagramPacket datagrampacket);//可供继承的方法
	
	public void run(){
	    DatagramPacket dp = null;
        synchronized(this.dataQueue){
            if(!this.dataQueue.isEmpty()){
                dp = (DatagramPacket)this.dataQueue.removeFirst();
            }
        }
	    receive(dp);
	}
	
	public void addData(DatagramPacket data){
	    synchronized(this.dataQueue){
	    	this.dataQueue.add(data);
	    }
	}
	
	public void destory(){
	    if(this.dataQueue != null){
	    	this.dataQueue.clear();
	    	this.dataQueue = null;
	    }
	}
}
