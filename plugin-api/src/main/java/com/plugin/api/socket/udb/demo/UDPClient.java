package com.plugin.api.socket.udb.demo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;
/**
 * 
 * @author wujf
 */
public class UDPClient {
	
	private static Logger log = Logger.getLogger(UDPClient.class);
	
	private DatagramSocket ds;
	private SocketAddress socketAddress;
	private InetAddress inetAddress;
	private String host;
	private int port;
	
	public UDPClient(SocketAddress socketAddress) throws SocketException {
		this.ds = new DatagramSocket();
		this.socketAddress = socketAddress;
	}
	
	public UDPClient(InetAddress inetAddress) throws SocketException {
		this.ds = new DatagramSocket();
		this.inetAddress = inetAddress;
	}

	public UDPClient(String host,int port) throws SocketException,UnknownHostException {
		this.host = host;
		this.port = port;
		this.ds = new DatagramSocket();
		this.inetAddress = InetAddress.getByName(this.host);
	}

	public void sendMsg(ByteBuffer data) throws IOException {
		if (data == null || data.remaining() < 1) {
			return;
		} else {
			sendMsg(data.array());
		}
	}
	
	public void sendMsg(String s) throws IOException {
		if(s == null || "".equals(s)){
			return;
		}
		sendMsg(this.inetAddress,this.port,s.getBytes());
	}

	public void sendMsg(byte[] data) throws IOException {
		if(data == null || data.length==0){
			return;
		}
		if(socketAddress != null){
			ds.send(new DatagramPacket(data,data.length,socketAddress));
		}else{
			sendMsg(this.inetAddress,this.port, data);
		}
	}

	public void close() {
		if (ds != null) {
			ds.close();
		}
	}

	public void sendMsg(InetAddress inetAddress,int port, byte data[]) throws IOException {
		if (this.ds == null) {
			this.ds = new DatagramSocket();
		}
		ds.send(new DatagramPacket(data, data.length, InetAddress.getByName(host), port));
	}
}
