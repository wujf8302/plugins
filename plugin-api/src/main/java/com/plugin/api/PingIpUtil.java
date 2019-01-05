package com.plugin.api;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.sql.ResultSet;
import java.util.Hashtable;
/**
 * 
 * @author wujf
 */
public class PingIpUtil implements Runnable {

	public String ip;         // IP, 用户名, 主机名
	ResultSet list;           // 分页显示的记录集
	public PingIpUtil cur;    // 分页显示的当前记录
	static public Hashtable ping = new Hashtable(); // ping 后的结果集
	static int threadCount = 0; // 当前线程的数量, 防止过多线程摧毁电脑

	public PingIpUtil() {
		
	}

	public PingIpUtil(String ip) {
		this.ip = ip;
		Thread r = new Thread(this);
		r.start();
	}

	public static void Ping(String ip) throws Exception {
		while (threadCount > 30) // 最多30个线程
			Thread.sleep(50);
		threadCount += 1;
		PingIpUtil p = new PingIpUtil(ip);
	}

	public void PingAll() throws Exception {
		threadCount = 0;
		ping = new Hashtable();
		while (true)
			// next()对所有局域网Ip放到cur
			Ping(cur.ip);
		// 等着所有Ping结束
		// while(threadCount>0)
		// Thread.sleep(50);
	}

	public void run() {
		try {
			Process p = Runtime.getRuntime().exec("ping " + ip + " -w 300 -n 1");
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			// 读取结果行
			for (int i = 1; i < 7; i++)
				input.readLine();
			String line = input.readLine();
			if (line.length() < 17 || line.substring(8, 17).equals("timed out"))
				ping.put(ip, new Boolean(false));
			else
				ping.put(ip, new Boolean(true));
			// 线程结束
			threadCount -= 1;
		} catch (IOException e) {
			
		}
	}

	public static void main(String[] args) throws Exception {
		PingIpUtil ip = new PingIpUtil();
		ip.PingAll();
		java.util.Enumeration key = ping.keys();
		String k;
		while ((k = (String) key.nextElement()) != null)
			System.out.println(k + ": " + ping.get(k));
	}
}
