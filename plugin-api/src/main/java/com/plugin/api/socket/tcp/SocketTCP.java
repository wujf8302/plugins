package com.plugin.api.socket.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * 
 * @author wujf
 */
public class SocketTCP extends Thread {
	
	private Socket s;

	public SocketTCP(Socket s) {
		this.s = s;
	}
	
	public void run() {
		try {
			OutputStream os = s.getOutputStream();
			InputStream is = s.getInputStream();
			os.write("Hello,welcome you!".getBytes());
			byte[] buf = new byte[100];
			int len = is.read(buf);
			System.out.println(new String(buf, 0, len));
			os.close();
			is.close();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length > 0){
			server();
	    }else{
			client();
	    }
	}

	public static void server() {
		try {
			ServerSocket ss = new ServerSocket(6000);
			while (true) {
				Socket s = ss.accept();
				new SocketTCP(s).start();
			}
			// ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void client() {
		try {
			Socket s = new Socket(InetAddress.getLocalHost(),6000);
			
			OutputStream os = s.getOutputStream();
			InputStream is = s.getInputStream();
			byte[] buf = new byte[100];
			int len = is.read(buf);
			System.out.println(new String(buf, 0, len));
			os.write("Hello,this is zhangsan!".getBytes());
			os.close();
			is.close();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
