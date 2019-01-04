package com.plugin.api.socket.udb;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/*
 TCP与UDP比较 
 TCP       
 是否连接    面向连接的
 传输可靠性  可靠的
 应用场合
 1、数据完整性要求高的传输
 2、传输大量的数据（大小没有限制）
 如：安装文件下载等
 速度 慢

 UDP
 是否连接    面向非连接的
 传输可靠性  不可靠的
 应用场合
 1、实时性要求高，丢失部分数据也无关紧要的传输
 2、少量数据（有限制，每次不超过64K）
 如：视频会议等
 速度 快
 */
public class SocketUDP {

	public static void main(String[] args) {
		if (args.length > 0) {
			recv(6000);
		} else {
			send();
		}
	}

	/**
	 * 接收
	 */
	public static void recv(int port) {
		try {
			DatagramSocket ds = new DatagramSocket(port);

			byte[] buf = new byte[100];
			DatagramPacket dp = new DatagramPacket(buf, 100);
			// accept data
			ds.receive(dp);
			System.out.println(new String(buf, 0, 100));

			// send data
			String str = "Welcome you!";
			DatagramPacket dpSend = new DatagramPacket(str.getBytes(), str
					.length(), dp.getAddress(), dp.getPort());
			ds.send(dpSend);

			ds.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送
	 */
	public static void send() {
		try {
			DatagramSocket ds = new DatagramSocket();

			// send data
			String str = "Hello, this is zhangsan!";
			DatagramPacket dp = new DatagramPacket(str.getBytes(),
					str.length(), InetAddress.getLocalHost(), 6000);
			ds.send(dp);

			// accept data
			byte[] buf = new byte[100];
			DatagramPacket dbRecv = new DatagramPacket(buf, 100);
			ds.receive(dbRecv);
			System.out.println(new String(buf, 0, 100));

			ds.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
