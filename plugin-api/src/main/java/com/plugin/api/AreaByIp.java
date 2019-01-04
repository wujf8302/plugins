package com.plugin.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * 校验Ip归属地
 */
public class AreaByIp {

	public static void main(String[] args) {

		System.out.println(getAreaByIp("202.101.103.55"));//218.85.157.99
	}
	
	/**
	 * 通过接口，查询ip归属地
	 */
	public static String getAreaByIp(String ip) {
		String retArea = "";
		try {
			String urlStr = "http://support.118114.cn/ip.action?ip=" + ip; //网百运营支撑系统
			String area = read(urlStr);
			String areaArr[] = area.split(",");
			if (areaArr.length > 1) {
				retArea = areaArr[1];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retArea;
	}

	public static String read(String urlStr) {
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			InputStream in = connection.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in));
			StringBuffer buf = new StringBuffer();
			String line = null;
			while ((line = read.readLine()) != null) {
				buf.append(line);
			}
			return buf.toString();
		} catch (MalformedURLException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}
}
