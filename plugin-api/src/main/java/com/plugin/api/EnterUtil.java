package com.plugin.api;

public class EnterUtil {
	
	public static String enter = getEnter();
	
	public static String getEnter(int n) {
		String enter = "";
		for (int i = 0; i < n; i++) {
			enter = enter + EnterUtil.enter;
		}
		return enter;
	}
	
	public static String osType() {
		String tmp = System.getProperty("os.name");
		String string = "";
		if (tmp.indexOf("Linux") != -1 || tmp.indexOf("linux") != -1) {
			string = "linux";
		} else if (tmp.indexOf("Windows") != -1 || tmp.indexOf("windows") != -1) {
			string = "windows";
		}
		return string;
	}

	public static String getEnter() {
		String c = osType();
		String s = "";
		if ("windows".equals(c)) {
			s = "\r\n";  //windows的换行符是 \r\n
		}else if("linux".equals(c)){
			s = "\n";    //linux的换行符是\n
		}else {
			s = "\r\n";
		}
		return s;
	}
}
