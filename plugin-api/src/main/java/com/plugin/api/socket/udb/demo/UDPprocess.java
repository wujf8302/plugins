package com.plugin.api.socket.udb.demo;

import java.net.DatagramPacket;

import org.apache.log4j.Logger;
/**
 * 
 * @author wujf
 */
public class UDPprocess {

	private static Logger log = Logger.getLogger(UDPprocess.class);
	
    private WorkQueue workQueue;
    private UDPWork udpWork;

    public UDPprocess(UDPWork udpWork){
        this(udpWork, 2);
    }

    public UDPprocess(UDPWork udpWork, int threads){
    	this.workQueue = null;
        this.udpWork = null;
        this.udpWork = udpWork;
        this.workQueue = new WorkQueue(threads);
    }

    public void process(DatagramPacket dataPcck){
    	this.udpWork.addData(dataPcck);
    	this.workQueue.execute(this.udpWork);
    }

    public void destory(){
    	this.workQueue.destory();
        if(this.udpWork != null){
        	this.udpWork.destory();
        }
    }
}
