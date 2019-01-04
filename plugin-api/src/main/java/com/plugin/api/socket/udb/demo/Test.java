package com.plugin.api.socket.udb.demo;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class Test {
	
	public static class MyWord extends UDPWork{
		
		public void receive(DatagramPacket datagrampacket) {
			int len = datagrampacket.getLength();
			byte[] bytes = datagrampacket.getData();
			
			System.out.println(new String(bytes,0,len));
		}
		
	}
	
	public static void server(int port) {
		try {
			UDPServer server = new UDPServer(port,new UDPprocess(new Test.MyWord(),3));
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void client(int port,String host) {
		try {
			UDPClient client = new UDPClient(host,port);
			client.sendMsg("wujf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		int port = 6000;
		server(port);
		try {
			client(port,InetAddress.getLocalHost().getHostAddress());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
