package com.plugin.api.socket.udb.demo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import org.apache.log4j.Logger;
/**
 * 
 * @author wujf
 */
public class UDPServer extends Thread{

	private static Logger log = Logger.getLogger(UDPServer.class);
	
    private byte buf[];
    private boolean isRuning;
    private DatagramPacket dp;
    private DatagramSocket socket;
    private UDPprocess udpProcess;

    public UDPServer(int port, UDPprocess udpProcess)throws Exception{
    	this.buf = new byte[8192];
        this.isRuning = true;
        this.dp = new DatagramPacket(buf, buf.length);
        this.udpProcess = udpProcess;
        this.socket = new DatagramSocket(port);
        this.socket.setReceiveBufferSize(0x100000);
        super.setDaemon(true);//守护线程
    }

    public void run(){
    	
        while(isRuning){
            try{
                socket.receive(dp);
            } catch(SocketException e){
            	log.error(e.getMessage());
            }catch(IOException e){
            	log.error(e.getMessage());
            }
            
            try{
                udpProcess.process(UDPUtil.clone(dp));
            }catch(Throwable e) {
            	log.error(e.getMessage());
            }
        }
        destroyUDP();
    }

    public void stopUDP() {
        isRuning = false;
    }

    private void destroyUDP(){
        if(socket != null){
            socket.close();
        }
        if(udpProcess != null){
            udpProcess.destory();
        }
    }
}
