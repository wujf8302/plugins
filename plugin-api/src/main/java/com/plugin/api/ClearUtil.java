package com.plugin.api;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;

/**
 * Oracle数据库的执行逻辑备份操作
 * ms-dos: exp system/oracle@myoracle inctype=complete file='c:\db.dmp ' log='c:\exp.log' 
 * @author wujf
*/
public class ClearUtil {
    
    private static Logger log = Logger.getLogger(ClearUtil.class);

	private static String basePath = "D:\\tomcat-6.0.18";
	private static String startup = "startup.bat";
	private static String clear = "clear.bat";
	private static String shutdown = "shutdown.bat";
	private static String projectName = "platform";
	
	public static void main(String[] args) {
		try {
			String basePath = getBasePath();
			//Process child = Runtime.getRuntime().exec("notepad");
			Process comm1 = Runtime.getRuntime().exec("cmd /c start "+basePath+shutdown);
			Process comm2 = Runtime.getRuntime().exec("cmd /c start "+basePath+clear);
			Process comm3 = Runtime.getRuntime().exec("cmd /c start "+basePath+startup);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//clear();
	}
	
	private static String getBasePath(){
	    URL url = ClearUtil.class.getResource("");
	    String basePath = url.getPath();
	    if(basePath != null && !"".equals(basePath) && basePath.length()>1){
		  basePath = basePath.substring(1,basePath.length());
	    }
		return basePath;
	}
	
	public static void clear() {
		String[] directory = new String[10];
		directory[0] = basePath+"\\bin\\"+startup;
		directory[1] = basePath+"\\bin\\"+projectName;
		directory[2] = basePath+"\\work\\Catalina\\localhost\\"+projectName;
		directory[3] = basePath+"\\webapps\\"+projectName;
		//删除目录及缓存
		for (int i = 1; i < directory.length; i++) {
			delFiles(new File(directory[i]));
		}
	}
	
	/**
	 * 删除指定文件路径下面的所有文件和文件夹
	 */
	public static boolean delFiles(File file) {
		boolean flag = false;
		try {
			if (file.exists()) {
				if (file.isDirectory()) {
					String[] contents = file.list();
					for (int i = 0; i < contents.length; i++) {
						File file2X = new File(file.getAbsolutePath() + "/" + contents[i]);
						if (file2X.exists()) {
							if (file2X.isFile()) {
								flag = file2X.delete();
							} else if (file2X.isDirectory()) {
								delFiles(file2X);
							}
						} else {
							throw new RuntimeException("File not exist!");
						}
					}
				}
				flag = file.delete();
			} else {
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

}
