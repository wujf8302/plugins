package com.plugin.api.socket.udb.demo;

import java.net.DatagramPacket;
import java.net.InetAddress;

import org.apache.log4j.Logger;

public class UDPUtil {

	private static Logger log = Logger.getLogger(UDPUtil.class);
	
	public static DatagramPacket toDatagram(String s, InetAddress destIA,int destPort) {
		byte buf[] = s.getBytes();
		return new DatagramPacket(buf, buf.length, destIA, destPort);
	}

	public static DatagramPacket clone(DatagramPacket dp) {
		byte data[] = new byte[dp.getLength()];
		System.arraycopy(dp.getData(), 0, data, 0, data.length);
		DatagramPacket pack = new DatagramPacket(data, data.length, dp.getAddress(), dp.getPort());
		return pack;
	}

	public static String toString(DatagramPacket p) {
		return new String(p.getData(), 0, p.getLength());
	}

}
