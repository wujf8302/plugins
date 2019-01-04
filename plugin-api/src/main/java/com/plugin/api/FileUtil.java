package com.plugin.api;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

public class FileUtil {

	private static Logger log = Logger.getLogger(FileUtil.class);
	private static String enter = EnterUtil.enter;
	
	/**
	 * 获得路径所在盘符.
	 */
	public static String getDrive(String filepath) {
		String pf = "";
		if(filepath != null && filepath.indexOf(":") != -1) {
			pf = filepath.substring(0, filepath.indexOf(":")+1);
		}
		return pf;
	}
	
	public static String mkFileDir(String path) {
		return  mkFileDir(new File(path));
	}
	public static String mkFileDir(File file) {
		//D:\\data\\demo\\bianhao.txt == 创建目录D:\\data\\demo\\bianhao
		String dir = file.getParentFile().getAbsolutePath() + File.separator + FileUtil.getSimpleName(file);
		FileUtil.mkDirectory(dir);
		return dir;
	}
	
	public static String getTargetPath(String spath,String tdir,String suffix) {
		File file = new File(spath);
		FileUtil.mkDirectory(tdir);
		String path =  tdir + File.separator + FileUtil.getSimpleName(file) + "." + suffix;
		
		return  path;
	}
	public static String getTargetPath(String path,String suffix) {
		File file = new File(path);
		//FileUtil.mkDirectory(file);
		String dir = file.getParentFile().getAbsolutePath();
		String fileName =  FileUtil.getSimpleName(path) + "." + suffix;
		
		return  dir + File.separator + fileName;
	}
	public static String getTargetPath(String path,int i,String suffix) {
		File file = new File(path);
		//FileUtil.mkDirectory(file);
		String dir = file.getParentFile().getAbsolutePath();
		String fileName =  FileUtil.getSimpleName(path) + "_" + i + "." + suffix;
		
		return  dir + File.separator + fileName;
	}
	
	/**
     * 获取文件长度
     */
    public static long getSize(File file) {
    	long len = 0L;
        if (file.exists() && file.isFile()) {
            len = file.length();
        }
        return len;
    }
    public static double getSize(File file,Integer limit) {
    	double len = 0.00;
        if (file.exists() && file.isFile()) {
        	if(limit != null && limit.intValue() > 0) {
        		len = file.length()*1.00/limit;
        	}else {
        		len = Double.valueOf(file.length());
        	}
        }
        return len;
    }
	
	public static String getFilePrefix(String fileName){
		int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(0, splitIndex);
	}
	
	public static String getFileSufix(String fileName){
		int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(splitIndex + 1);
	}
	public static String getFileSufix(File file){
		String fileName = file.getAbsolutePath();
		int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(splitIndex + 1);
	}
	
	public static void copyFile2(String inputFile,String outputFile){
		File sFile = new File(inputFile);
		File tFile = new File(outputFile);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		int temp = 0;  
        try {  
        	fis = new FileInputStream(sFile);
    		fos = new FileOutputStream(tFile);
			while ((temp = fis.read()) != -1) {  
			    fos.write(temp);  
			}
        } catch (FileNotFoundException e) {  
        	log.error("文档不存在: " + inputFile,e);
        } catch (IOException e) {  
        	log.error("写入文件错误: " + outputFile,e); 
        } finally{
            try {
            	if(fis != null) {
            		fis.close();
            	}
            	if(fis != null) {
            		fos.close();
            	}
			} catch (IOException e) {
				e.printStackTrace();
			}
        } 
	}
	
	/**
	 * 获得某目录下的所有文件类型.
	 */
	public static List<String> getFileTypes(String dir) {
		List<String> dataList = new ArrayList<String>();
		File file = new File(dir);
		File[] subs = file.listFiles();
		for (int i = 0; i < subs.length; i++) {
			if(subs[i] != null) {
				if(subs[i] != null) {
					if(subs[i].isFile()) {
						String name = subs[i].getName();//文件名
						if(name != null && !"".equals(name.trim())) {
							if(name.indexOf(".") != -1) {
								String suffix = name.substring(name.lastIndexOf("."));
								dataList.add(suffix);
								//System.out.println(suffix);
							}
						}
					}else {
						List<String> list = getFileTypes(subs[i].getAbsolutePath());
						dataList.addAll(list);
					}
				}
			}
		}
		return ListUtil.distinct(dataList);
	}
	
	public static List<String> getFileNames(String dir) {
		List<String> dataList = new ArrayList<String>();
		File file = new File(dir);
		File[] subs = file.listFiles();
		for (int i = 0; i < subs.length; i++) {
			if(subs[i] != null) {
				if(subs[i] != null) {
					if(subs[i].isFile()) {
						String name = subs[i].getName();//文件名
						if(name != null && !"".equals(name.trim())) {
							if(name.indexOf(".") != -1) {
								//name = name.substring(0,name.lastIndexOf("."));
								dataList.add(name);
								//System.out.println(name);
							}
						}
					}else {
						List<String> list = getFileTypes(subs[i].getAbsolutePath());
						dataList.addAll(list);
					}
				}
			}
		}
		return dataList;
	}
	
	public static void main(String[] args) {
		String s = null;
		s.length();
		unzipFiles("K:\\song","K:\\song\\song");


		//writeToFile("c:/123456.txt","共产党");
		/*
		String fileName = "C:/temp/newTemp.txt";
		IOUtil.readFileByBytes(fileName);
		IOUtil.readFileByChars(fileName);
		IOUtil.readFileByLines(fileName);
		IOUtil.readFileByRandomAccess(fileName);
		
		String content = "new append!";
		// 按方法A追加文件
		IOUtil.appendMethodA(fileName, content);
		IOUtil.appendMethodA(fileName, "append end. \n");
		// 显示文件内容
		IOUtil.readFileByLines(fileName);
		// 按方法B追加文件
		IOUtil.appendMethodB(fileName, content);
		IOUtil.appendMethodB(fileName, "append end. \n");
		// 显示文件内容
		IOUtil.readFileByLines(fileName);
		
		
         byte [] aa=new byte [3];
         try{
              DataOutputStream bos=new DataOutputStream(new FileOutputStream("zcq.doc",true));  
              bos.writeInt(123);//写入一个32位的整数
              bos.writeUTF("oxffff");
              bos.write(aa);
              bos.writeDouble(1.464654);
          }catch(IOException e){
              e.printStackTrace();
          }
          */
          test();
	}
	
	public static void test() {
		/*
		//System.out.println(reedFile("c:/123456.txt","\n"));
		char[] ch = readMemory(reedKey());//reedString(reedKey());
		if(ch != null){
			for (int i = 0; i < ch.length; i++) {
				System.out.println(ch[i]);
			}
		}
		*/

		//writeFile("c:/1fa.txt",reedFile("c:/123456.txt","\n"));
		appendMethodA("c:/1fa.txt","srdf*中"+"\r\n"); //带回车
		appendMethodB("c:/123456.txt","efes中");
	}
	
	public static String getSimpleName(String file){
		return getSimpleName(new File(file));
	}
	public static String getSimpleName(File file){
		String name = file.getName();
		if(name != null && name.indexOf(".") != -1){
			name = name.substring(0,name.lastIndexOf("."));
		}
		return name;
	}
	
	/**
	 * 通过File的
	 * listFiles(FileFilter filter)
	 * 或者
	 * listFiles(FilenameFilter filter)
	 * 得到当前目录下的后缀为.txt的文件， 并把文件信息写到控制台，
	 * 格式如下：文件名：文件大小，最后修改时间 
	 * 举例：
	 * xxx.txt:609bytes,2006-09-08 12:23:25 a.txt:5643bytes,2005-09-13 08:12:01
	 */
	public File[] fileFilter(String path,final String suffix) {
		
		File dir = new File(path);
		File[] files = dir.listFiles(new FileFilter(){
			public boolean accept(File pathname) {
				String[] s = suffix.split(",");
				if(s != null && s.length >0){
					for (int i = 0; i < s.length; i++) {
						if(pathname.getName().endsWith(s[i])) {
							return true;
						}
					}
				}
				return false;
			}
		});
		/*
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for (int i = 0; i < files.length; i++) {
			cal.setTimeInMillis(files[i].lastModified());
			
			System.out.println("文件名：" + files[i].getName() + ","+
					           "文件大小："+ files[i].length() + "bytes," +
					           "最后修改时间: " + format.format(cal.getTime()));
		}
		*/
		return files;
	}
	
	//true文件存在
	public static boolean exists(String path) {
		File FileTo = new File(path);
		if (FileTo.exists()) {
			return true;
		}
		return false;
	}

	public static int copy(String sourceFile, String toFile) {
		byte[] bytes = null;
		File FileTo = new File(toFile);
		if (FileTo.exists()) {
			System.out.println("此文件存在是否要覆盖[y/n]");
			int c = 0;
			try {
				c = System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (c == 'n') {
				return 0;
			}
		}
		// ----------------------
		File my1 = new File(sourceFile);
		bytes = new byte[(int) my1.length()];
		FileInputStream is = null;
		try {
			is = new FileInputStream(my1);
			is.read(bytes);
			// 输出源文件内容到屏幕
			for (int i = 0; i < bytes.length; i++) {
				System.out.print((char) bytes[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		File fileSource = new File(toFile);
		fileSource.delete();// 删除原来的文件
		OutputStream os = null;
		try {
			os = new FileOutputStream(fileSource);
			os.write(bytes);// 将源文件内容写入目标文件

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// ----------------------
		return 0;
	}
	
	/**
	 * 替换文件里的内容.
	 * @param path  文件路径
	 * @param oldStr  被替换的文件内容
	 * @param newStr 新的文件内容
	 */
	 public static void replace(String path,String oldStr,String newStr) {
        String temp = "";
        BufferedReader br = null;
        PrintWriter pw = null;
        String separator = System.getProperty("line.separator");
        try {
            File file = new File(path);
            boolean b = mkFile(file);
            if(b){
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
                br = new BufferedReader(isr);
                StringBuffer buf = new StringBuffer();

                // 保存该行前面的内容
                for (int j = 1; (temp = br.readLine()) != null && 
                      !(temp.equals(oldStr) || temp.startsWith(oldStr)); j++) {
                    buf = buf.append(temp);
                    buf = buf.append(separator);
                }

                // 将内容插入
                buf = buf.append(newStr);

                // 保存该行后面的内容
                while ((temp = br.readLine()) != null) {
                    buf = buf.append(separator);
                    buf = buf.append(temp);
                }

                pw = new PrintWriter(new FileOutputStream(file));
                pw.write(buf.toString().toCharArray());
                pw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        	if(br != null){
        		try {br.close();} catch (Exception e1) {}
        	}
        	if(pw != null){
        		try {pw.close();} catch (Exception e2) {}
        	}
        }
    }
	
	/**
	 * 写入文件
	 * FileUtil.FileTool.writeFile(new File(tpath),val,false) ;
	 */
	public static void writeFile(String filePath,String v) {
		try {
			RandomAccessFile rf = new RandomAccessFile(filePath,"rw");
			rf.seek(rf.length());//将指针移动到文件末尾
	    	
	    	rf.write(v.getBytes());
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void printWriter(String filePath,String value) {
		String s = "";
		try {
			BufferedReader in = new BufferedReader(new StringReader(value));
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filePath)));
			while ((s = in.readLine()) != null){
				out.println(s);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 数据追加写入到文件
	 */
	public static void writeFile(RandomAccessFile rf,String val) {
		if(rf != null && val != null && !"".equals(val)){
	        try{
	        	rf.seek(rf.length());//将指针移动到文件末尾
	            rf.write(val.getBytes());
			} catch (Exception e) {
				 throw new RuntimeException(e.getMessage());   
			}finally{
				if(rf != null){
					try {
						rf.close();
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				}
			}
		}
	}
	
	/**
	 * 数据追加写入到文件
	 */
	public static void writeFile(String path,List list,int perNum) {
		if(list != null && list.size() > 0){

			RandomAccessFile rf = null;
            String carriageReturnChar = EnterUtil.getEnter();
	        	
	        try{
	        	rf = new RandomAccessFile(path,"rw"); 
	        	rf.seek(rf.length());//将指针移动到文件末尾
				String tmp = "";

	        	long datanum = list.size();
	        	for (int i = 0; i < datanum; i++) {
	        		tmp += ((String)list.get(i) + carriageReturnChar);
	        		if ((i+1)%perNum == 0) {	
	        			rf.write(tmp.getBytes());
	        			tmp = "";
	    			}else if (i == (datanum - 1)) {
	    				rf.write(tmp.getBytes());
	    			}
				}
			} catch (Exception e) {
				 throw new RuntimeException(e.getMessage());   
			}finally{
				if(rf != null){
					try {
						rf.close();
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				}
			}
		}
	}
	
	public static BufferedReader openBufferedReader(String path){
		try {
			return new BufferedReader(new FileReader(new File(path)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void closeBufferedReader(BufferedReader buff){
		if(buff != null){
			try {
				buff.close();
			} catch (IOException e) {
				log.error("readFile方法(关闭BufferedReader流出错): "+e);
			}
		}
	}
	
	/**
	 * 读取指定路径的文件
	 */
	public static List readFile(BufferedReader buff,int len) {
		List records = new ArrayList();
		String str = "";
		int i = 0;
		try {
			while((str = buff.readLine()) != null) {
				i++;
				records.add(str);
				if(i == len && len != -1){
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return records;
	}
	
	/**
	 * 读取文件
	 */
	public static List readFile(String path) {
		return readFile(path,null); 
	}
	public static List readFile(String path,String charset) {
		File file = new File(path);
		return readFile(file,charset); 
	}
	public static List readFile(File file) {
		return readFile(file,null); 
	}
	public static List readFile(File file,String charset) {
		List list = new ArrayList();
		String str = "";
		BufferedReader buff = null;
		try {
			if(StringUtil.isNotEmpty(charset)){
				buff = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
			}else{
				buff = new BufferedReader(new FileReader(file));
			}
			while ((str = buff.readLine()) != null) {
				//System.out.println(str);
				//StringUtil.printByEncode(str);
				list.add(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (buff != null) {
				try {
					buff.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}
	public static List readFile2(String path,String charset) {
		return readFile2(new File(path),charset);
	}
	public static List readFile2(File file,String charset) {
		List list = new ArrayList();
		String str = "";
		BufferedReader buff = null;
		try {
			if(StringUtil.isNotEmpty(charset)){
				buff = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
			}else{
				buff = new BufferedReader(new FileReader(file));
			}
			while ((str = buff.readLine()) != null) {
				if(str != null){
					str = str.trim();
					if(!"".equals(str)){
						list.add(str);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (buff != null) {
				try {
					buff.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}
	
	public static StringBuffer getString(File file) {
		String path = file.getAbsolutePath();
		return getString(path);
	}
	public static StringBuffer getString(File file,String charset) {
		String path = file.getAbsolutePath();
		return getString(path,charset);
	}
	public static StringBuffer getString(String path) {
		return getString(path,null);
	}
	public static StringBuffer getString(String path,String charset) {
		return getString(path,charset,true);
	}
	public static StringBuffer getString(String path,String charset,boolean isBlank) {
		StringBuffer list = new StringBuffer();
		String str = "";
		BufferedReader buff = null;
		try {
			if(StringUtil.isNotEmpty(charset)){
				buff = new BufferedReader(new InputStreamReader(new FileInputStream(path), charset));
			}else{
				buff = new BufferedReader(new FileReader(new File(path)));
			}
			while ((str = buff.readLine()) != null) {
				if(isBlank) {
					str = StringUtil.replaceBlank(str);
				}
				list.append(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (buff != null) {
				try {
					buff.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return list;
	}
	
	public static StringBuffer getString2(File file) {
		String path = file.getAbsolutePath();
		return getString2(path);
	}
	public static StringBuffer getString2(String path) {
		File file = new File(path);
		StringBuffer list = new StringBuffer();
		if(file.exists()){
			String str = "";
			BufferedReader buff = null;
			try {
				buff = new BufferedReader(new FileReader(file));
				while ((str = buff.readLine()) != null) {
					//System.out.println(StringUtil.replaceBlank(str));
					list.append(StringUtil.replaceBlank(str) + EnterUtil.enter);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (buff != null) {
					try {
						buff.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return list;
	}
	public static StringBuffer getString2(File file,String charset) {
		String path = file.getAbsolutePath();
		return getString2(path,charset);
	}
	public static StringBuffer getString2(String path,String charset) {
		File file = new File(path);
		StringBuffer list = new StringBuffer();
		if(file.exists()){
			String str = "";
			BufferedReader buff = null;
			try {
				//buff = new BufferedReader(new FileReader(file));
				buff = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
				while ((str = buff.readLine()) != null) {
					//System.out.println(StringUtil.replaceBlank(str));
					list.append(StringUtil.replaceBlank(str) + EnterUtil.enter);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (buff != null) {
					try {
						buff.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return list;
	}
	
	/**
	 * 读取指定路径的文件
	 */
	public static Map readFileInfo(String path) {
		Map map = new HashMap();
		ArrayList records = new ArrayList();
		int emptyNum = 0;    //空行记录数
		String str = "";
		BufferedReader buff = null;
		try {
			buff = new BufferedReader(new FileReader(new File(path)));
			while((str = buff.readLine()) != null) {
				if(str == null || "".equals(str.trim())){
					emptyNum++;
				}
				records.add(str);
			}
		}catch (Exception e) {
			log.error("读取待处理的文件("+path+")出错: "+e);
		}finally{
			if(buff != null){
				try {
					buff.close();
				} catch (IOException e) {
					log.error("readFile方法(关闭BufferedReader流出错): "+e);
				}
			}
		}
		
		map.put("records", records);
		
		map.put("realNum", new Integer(records.size()-emptyNum));
		map.put("emptyNum", new Integer(emptyNum));
		map.put("tatalNum", new Integer(records.size()));
		
		return map;
	}
	
	public static List findSubFolder(String path,List list){
		File dir = new File(path);
		File[] files = dir.listFiles();
		if(files != null && files.length > 0){
			for (int i = 0; i < files.length; i++) {
				if(files[i].isDirectory()){
					list.add(files[i]);
					findSubFolder(files[i].getAbsolutePath(),list);
				}
			}
		}
		return list;
	}
	
	/**  
     * 找出指定目录及其子目录下，满足指定关键字的文件的绝对路径  
     * 提示：方法中出现异常被内部捕获
     * @param dir 指定目录  
     * @param key 关键字
     * */  
	public static List<String> findKey(File dirFile,String key){
		List<String> list = new ArrayList<String>();
        if(dirFile.exists() && dirFile.isDirectory()){   
            File[] subFiles = dirFile.listFiles();   
            for(int i = 0;i<subFiles.length;i++) {  
            	File subFile = subFiles[i];
                if(subFile.isDirectory()){   
                    List<String> sub = findKey(subFile,key);
                    list.addAll(sub);
                }else{   
                    String name = subFile.getName();   
                    if(name.indexOf(key) != -1){   
                        list.add(subFile.getAbsolutePath());   
                    }   
                }   
            }   
        }
        return list;
    }
	
	 /**  
     * 递归遍历，查找满足后辍的文件  
     * @param dirFile 必须为一个存在的目录.不能为null  
     * @param suffix  
     * @param list 递归遍历目录记录满足后缀的文件的绝对路径 
     * */ 
	public static List find(String dir,String suffix){  
		return find(new File(dir),suffix);
	}
	public static List find(File dirFile,String suffix){  
		List list = new ArrayList();
		find(dirFile,suffix,list);
        return list;
    }
	public static void find(File dirFile,String suffix,List list){   
        if(dirFile.exists() && dirFile.isDirectory()){   
            File[] subFiles = dirFile.listFiles();   
            for(int i = 0;i<subFiles.length;i++) {  
            	File subFile = subFiles[i];
                if(subFile.isDirectory()){   
                    find(subFile, suffix, list);   
                }else{   
                    String path = subFile.getAbsolutePath();   
                    if(path.endsWith(suffix)){   
                        list.add(path);   
                    }   
                }   
            }   
        } 
    }
	
	/**
	 * 删除文件.
	 */
	public static boolean deleteFile(String fileName) {
		return deleteFile(new File(fileName));
	}
	public static boolean deleteFile(File file) { 
		if (file != null && file.exists() && file.isFile()){  
            return file.delete();  
        }
        return false;   
	}
	
	/**
	 * 删除目录.
	 */
	public static boolean deleteDirectory(String fileName) {
		return deleteDirectory(new File(fileName));
	}
	public static boolean deleteDirectory(File file) { 
		if (file != null && file.exists() && file.isDirectory()){  
            return file.delete();  
        }
        return false;   
	}
	
	/**
	 * 删除文件或目录【通用】.
	 */
	public static boolean delete(String fileName) {
		return delete(new File(fileName));
	}
	public static boolean delete(File file) {
	    return FileTool.delete(file);
	}
	/**
	 * 删除目录下指定key的文件或目录.
	 * type 1全册2删除目录 3删除文件
	 */
	public static void delete(String sdir,String key,int type) {
		List<File> list = new ArrayList<File>();
		File dir = new File(sdir);
		if(dir.exists() && dir.isDirectory()) {
			File[] subs = dir.listFiles();
			if(subs != null && subs.length > 0) {
				for (int i = 0; i < subs.length; i++) {
					File sub = subs[i];
					if(sub.exists()) {//存在
						String name = FileUtil.getSimpleName(sub);
						if(type == 1) {//不论是文件还是目录
							if(name.equals(key)) {
								System.out.println("删除：" + sub.getAbsolutePath());
								FileUtil.delete(sub);
							}else {
								list.add(sub);
							}
						}else if(sub.isDirectory()) {//目录
							if(name.equals(key)) {
								if(type == 2) {
									System.out.println("删除目录：" + sub.getAbsolutePath());
									FileUtil.delete(sub);
								}
							}else {
								list.add(sub);
							}
						}else if(sub.isFile()) {//文件
							if(name.equals(key) && type == 3) {
								System.out.println("删除文件：" + sub.getAbsolutePath());
								FileUtil.delete(sub);
							}
						}
					}
					//---
					if(list != null) {
						for (int j = 0; j < list.size(); j++) {
							File file = list.get(j);
							delete(file.getAbsolutePath(),key,type);
						}
					}
					//---
				}
			}
		}
	}
	
	/**  
     *  拷贝文件  
     */  
    public static String copyFile(String oldPath, String tdir) {
    	if(tdir != null && !"".equals(tdir.trim())) {//如果目标目录不存在则创建
    		File tdirFile = new File(tdir);
    		if(tdirFile != null && !tdirFile.exists()) {
    			mkDirectory(tdirFile);
    		}
    	}
    	String newPath = "";
    	FileInputStream input = null;
    	FileOutputStream output = null;
    	try {   
            File temp = new File(oldPath); 
            if(temp != null && temp.exists()) {//如果源文件存在
            	 input = new FileInputStream(temp);
                 newPath = tdir + "/"  + (temp.getName()).toString();
                 output = new FileOutputStream(newPath);   
                 byte[] b = new byte[1024 * 5];   
                 int len;   
                 while ((len = input.read(b)) != -1) {   
                     output.write(b, 0, len);   
                 }   
        		 output.flush();  
            }
        } catch (Exception e1) {   
        	newPath = "";
        	log.error("",e1);
        }finally {
        	try {
        		if(output != null) {
                    output.close();  
            	}
            	if(input != null) {
            		input.close();  
            	}
			} catch (Exception e2) {
				log.error("",e2);
			}
        }
        return newPath;
    }
	
	/**  
     * 剪切文件  
     */  
    public static void cutFile(File temp, String newDir) {   
        try {   
            FileInputStream input = new FileInputStream(temp);   
            FileOutputStream output = new FileOutputStream(newDir + "/"  + (temp.getName()).toString());   
            byte[] b = new byte[1024 * 5];   
            int len;   
            while ((len = input.read(b)) != -1) {   
                output.write(b, 0, len);   
            }   
            output.flush();   
            output.close();   
            input.close();  
            temp.delete();
        } catch (Exception e) { 
            e.printStackTrace(); 
        }   
    }
    
    /**  
     * 剪切文件  
     */  
    public static String cutFile(String oldPath, String newDir) {   
    	String newPath = "";
        try {   
            File temp = new File(oldPath);   
            FileInputStream input = new FileInputStream(temp);   
            newPath = newDir + "/"  + (temp.getName()).toString();
            FileOutputStream output = new FileOutputStream(newPath);   
            byte[] b = new byte[1024 * 5];   
            int len;   
            while ((len = input.read(b)) != -1) {   
                output.write(b, 0, len);   
            }   
            output.flush();   
            output.close();   
            input.close();  
            temp.delete();
        } catch (FileNotFoundException e) {    
           e.printStackTrace();
        } catch (Exception e) {   
           e.printStackTrace();
        }  
        return newPath;
    } 
    
	/**
	 * 根据路径创建一系列的目录
	 */
	public static boolean mkDirectory(String path) {
		if(StringUtil.isEmpty(path)){
			return false;
		}
		return mkDirectory(new File(path));
	}
	/**
	 * 根据路径创建一系列的目录
	 */
	public static boolean mkDirectory(File file) {
		try {
			if (!file.exists()) {
				return file.mkdirs();
			}
		} catch (RuntimeException e) {
			log.error("create directory error: ",e);
			return false;
		} finally {
			file = null;
		}
		return true;
	}
	
	/**
	 * 根据路径创建一系列的目录
	 */
	public static boolean mkFile(String path) {
		if(path != null && !"".equals(path)){
			System.out.println((new File(path)).getAbsolutePath());
			return mkFile(new File(path));
		}
		return false;
	}
	public static boolean mkFile(File file) {
		if(file != null){
			try {
				mkDirectory(file.getParentFile());
				if (!file.exists()) {
					return file.createNewFile();
				}
				return true;
			} catch (IOException e) {
				log.error("create file error: ",e);
			} finally {
				file = null;
			}
		}
		return false;
	}
	
	/**
	 * 判断该文件下是否含有指定后辍的文件.
	 * true存在
	 */
	public static boolean isExist(File file,String suffix) {
		  File[] subFiles = file.listFiles();
		  if(subFiles != null){
			  for(int i = 0;i<subFiles.length;i++) {  
	          	    File subFile = subFiles[i];
		            String path = subFile.getAbsolutePath();   
		            if(path.endsWith(suffix)){   
		               return true; 
		            }   
	          }
		  }
          
          return false;
	}
	
	  /**
	 * 判断该文件下是否含有指定后辍的文件，如果有则返回其文件路径
	 */
	public static String isExistSuffix(List cutFileErrorList,File file,String suffix) {
		File[] subFiles = file.listFiles();   
        for(int i = 0;i<subFiles.length;i++) {
        	File subFile = subFiles[i];
            String path = subFile.getAbsolutePath();   
            if(path.endsWith(suffix)){ 
               if(cutFileErrorList != null && cutFileErrorList.size()>0){
            	   boolean b = false;
            	   for (int j = 0; j < cutFileErrorList.size(); j++) {
            		   File cutFile = new File((String)cutFileErrorList.get(j));
					   if(path.equals(cutFile.getAbsolutePath())){
							b = true;
					   }
				   }
            	   if(!b){
            		   return path;
            	   }
			   }else {
				   return path;
			   }
            }   
        } 
        return "";
	}
	
	public void writeByte(InputStream inputStream,OutputStream outputStream){
		 byte[] bytes = new byte[1024];
		 int len = 0;
		 try {
			 while((len = inputStream.read(bytes))!=-1){
				 outputStream.write(bytes,0,len);
			 }
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	}
	
	public  byte[] readByte(InputStream inputStream){
		 byte[] bytes = new byte[1024];
		 int len = 0;
		 try {
			 StringBuffer sb = new StringBuffer();
			 while((len = inputStream.read(bytes))!=-1){
				 sb.append(bytes);
			 }
			 return sb.toString().getBytes();
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		 return null;
	}
	
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}
	
	/*
	 * 使用MappedByteBuffer来操作内存映射文件是NIO提供的强大武器，由于将对文件的操作映射到内存中，读写性能都会提高。需要注意的问题是，在MappedByteBuffer的javadoc中有这样一句： 
	 * A mapped byte buffer and the file mapping that it represents remain 
	 * valid until the buffer itself is garbage-collected.
	 * 这句话的意思如果一个MappedByteBuffer对象没有被垃圾回收，那么这个对象和他所代表的映射文件是一直有效的。 
	 * 这意味着什么呢？这意味着一旦一个文件被映射为内存文件，即使你关闭了这个文件，该文件也无法再次被打开，直到文件所对应的MappedByteBuffer对象被GC，该文件会被系统释放，才可以再次打开。 
	 * 目前没有有效方法解决这个问题，一个workaround是调用System.gc()，增加回收MappedByteBuffer对象的几率。然后sleep()几百毫秒，等待好消息。 
	 */
	/**
	 * java写入大文件(上G级的)
	 */
    public static void largeMappedFilesWrite(String path,String data){  
        try {
	    	  RandomAccessFile randomAccessFile = new RandomAccessFile(path,"rw");
	    	  randomAccessFile.seek(randomAccessFile.length());
	    	  MappedByteBuffer out = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, data.length()); 
	    	  out.put(data.getBytes());
		} catch (Exception e) {
		      e.printStackTrace();
		}
    }   
    
    /**
	 * java读取大文件(上G级的)
	 */
    public static String largeMappedFilesRead(String path){ 
    	String s = "";
        try {
        	  RandomAccessFile randomAccessFile = new RandomAccessFile(path,"rw");
        	  MappedByteBuffer out = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0,randomAccessFile.length()); 
        	  
        	  //Charset charset = Charset.forName("GBK");//解决中文乱码问题
        	  //System.out.println( charset.decode(out.get(new byte[100])));
        	  
        	  for (int i = 0; i < randomAccessFile.length(); i++) {
				  char c = (char)out.get(i);
				  s = s + String.valueOf(c);
			  }
		} catch (Exception e) {
		      e.printStackTrace();
		}
		return s;
    }   
    
    /**
     * 需要改造
     */
	public static void lastModifyFileDateTime() {
		FilenameFilter filter;
		Calendar cal = Calendar.getInstance();
		final File dir = new File("c:/");
		File[] files = dir.listFiles(new FilenameFilter(){

			public boolean accept(File dir, String name) {
                
				return true;
			}});

		for (int i = 0; i < files.length; i++) {
			cal.setTimeInMillis(files[i].lastModified());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			System.out.println("文件名：" + files[i].getName() + ",文件大小："+ files[i].length() + "bytes,最后修改时间: "+ format.format(cal.getTime()));
		}
	}
	
	public static void demo() {
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {

			isr = new InputStreamReader(System.in,"ISO8859-1");
			br = new BufferedReader(isr);
			String s = br.readLine();
			System.out.println(new String(s.getBytes("ISO8859-1")));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(br != null){
					br.close();
				}
				if(isr != null){
					isr.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**  
	* 文件操作工具类
	*/  
	public static class FileTool {   
	    /**  
	     * 将字节流转换成字符串返回  
	     * @param is   输入流  
	     * @return 字符串  
	     */  
	    public static String readFileByLines(InputStream is) {   
	        BufferedReader reader = null;   
	        StringBuffer sb = new StringBuffer();   
	        try {   
	            reader = new BufferedReader(new InputStreamReader(is));   
	            String tempString = null;   
	            while ((tempString = reader.readLine()) != null) {   
	                sb.append(tempString + "\n");   
	            }   
	            reader.close();   
	        } catch (IOException e) {   
	            e.printStackTrace();   
	        } finally {   
	            if (reader != null) {   
	                try {   
	                    reader.close();   
	                } catch (IOException e1) {   
	                }   
	            }   
	        }   
	        return sb.toString();   
	    }   
	  
	    /**  
	     * 将文件一行一行的读成List返回  
	     * @param file  需要读取的文件  
	     * @return 文件的一行就是一个List的Item的返回  
	     */  
	    public static List<String> readFileToList(File file) {   
	        BufferedReader reader = null;   
	        List<String> list = new ArrayList<String>();   
	        try {   
	            reader = new BufferedReader(new FileReader(file));   
	            String tempString = null;   
	            while ((tempString = reader.readLine()) != null) {   
	                list.add(tempString);   
	            }   
	            reader.close();   
	        } catch (IOException e) {   
	            e.printStackTrace();   
	        } finally {   
	            if (reader != null) {   
	                try {   
	                    reader.close();   
	                } catch (IOException e1) {   
	                }   
	            }   
	        }   
	        return list;   
	    }   
	  
	    /**  
	     * 将文件按照一定的编码方式一行一行的读成List返回  
	     * @param file  需要读取的文件  
	     * @param encodType   字符编码  
	     * @return 文件的一行就是一个List的Item的返回  
	     */  
	    public static List<String> readFileToList(File file, String encodType) {   
	        BufferedReader reader = null;   
	        List<String> list = new ArrayList<String>();   
	        try {   
	            reader = new BufferedReader(new InputStreamReader(   
	                    new FileInputStream(file), encodType));   
	            String tempString = null;   
	            while ((tempString = reader.readLine()) != null) {   
	                if (!(tempString.charAt(0) >= 'a' && tempString.charAt(0) <= 'z'))   
	                    tempString = tempString.substring(1);   
	                list.add(tempString);   
	            }   
	            reader.close();   
	        } catch (IOException e) {   
	            e.printStackTrace();   
	        } finally {   
	            if (reader != null) {   
	                try {   
	                    reader.close();   
	                } catch (IOException e1) {   
	                }   
	            }   
	        }   
	        return list;   
	    }   
	  
	    /**  
	     * 将指定的字符串内容以指定的方式写入到指定的文件中  
	     * @param file  需要写人的文件  
	     * @param content 需要写入的内容  
	     * @param flag   是否追加写入  
	     */  
	    public static void writeFile(File file, String content, Boolean flag) {   
	        try {   
	            if (!file.exists())   
	                file.createNewFile();   
	            FileWriter writer = new FileWriter(file, flag);   
	            writer.write(content);   
	            writer.close();   
	        } catch (IOException e) {   
	            e.printStackTrace();   
	        }   
	    }   
	  
	    /**  
	     * 将指定的字符串内容以指定的方式及编码写入到指定的文件中  
	     * @param file  需要写人的文件  
	     * @param content 需要写入的内容  
	     * @param flag    是否追加写入  
	     * @param encodType  文件编码  
	     */  
	    public static void writeFile(File file, String content, Boolean flag,   
	            String encodType) {   
	        try {   
	            FileOutputStream writerStream = new FileOutputStream(file, flag);   
	            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, encodType));   
	            writer.write(content);   
	            writer.close();   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	        }   
	    }   
	  
	    /**  
	     * 拷贝文件夹  
	     * @param oldPath  源目录  
	     * @param newPath  目标目录  
	     */  
	    public static void copyFolder(String oldPath, String newPath) {   
	        try {   
	            (new File(newPath)).mkdirs();   
	            File a = new File(oldPath);   
	            String[] file = a.list();   
	            File temp = null;   
	            for (int i = 0; i < file.length; i++) {   
	                if (oldPath.endsWith(File.separator)) {   
	                    temp = new File(oldPath + file[i]);   
	                } else {   
	                    temp = new File(oldPath + File.separator + file[i]);   
	                }   
	                if (temp.isFile()) {   
	                    FileInputStream input = new FileInputStream(temp);   
	                    FileOutputStream output = new FileOutputStream(newPath   
	                            + "/" + (temp.getName()).toString());   
	                    byte[] b = new byte[1024 * 5];   
	                    int len;   
	                    while ((len = input.read(b)) != -1) {   
	                        output.write(b, 0, len);   
	                    }   
	                    output.flush();   
	                    output.close();   
	                    input.close();   
	                }   
	                if (temp.isDirectory()) {   
	                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);   
	                }   
	            }   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	        }   
	    }   
	    
	    /**
		 * 文件夹拷贝，将源文件夹下的所有文件及其子文件夹（文件）拷贝到目标文件夹（文件）下
		 * @param sourceFile 源文件
		 * @param desFile    目标文件
		 * @return
		 */
		public static boolean copyDirectiory(String sourceFile, String desFile)throws IOException {

			File source = new File(sourceFile);
			if (!source.exists()) {
				return false;
			}
			File des = new File(desFile);
			if (!des.exists()){
				des.mkdirs();//不存在目标文件就创建
			}

			File[] file = source.listFiles();
			FileInputStream input = null;
			FileOutputStream output = null;
			try {
				for (int i = 0; i < file.length; i++) {
					if (file[i].isFile()) { // 如果是文件 则读源文件 写入目标文件
						input = new FileInputStream(file[i]);
						output = new FileOutputStream(new File(desFile + "/"+ file[i].getName()));
						byte[] b = new byte[1024 * 5];
						int len;
						while ((len = input.read(b)) != -1) { // 读文件
							output.write(b, 0, len);          // 向目标文件写文件
						}
						input.close();
						output.flush();
						output.close();
					} else if (file[i].isDirectory()) { // 如果是文件夹 递归读取子文件或文件夹
						copyDirectiory(sourceFile + "/" + file[i].getName(),desFile + "/" + file[i].getName());
					}
				}
				return true;
			} catch (Exception e) {
				log.error("",e);
				return false;
			} finally {
				if (input != null)
					input.close();
				if (output != null)
					output.close();
			}
		}
	  
	    /**  
	     * 将文件重命名.
	     * @param oldName  源文件名  
	     * @param newName  新文件名  
	     */  
	    public static void reName(String oldName, String newName) {
	    	try {
		        File oldF = new File(oldName);   
		        File newF = new File(newName);   
		        oldF.renameTo(newF);   
			} catch (Exception e) {
				log.error("",e);
			}
	    }   
	  
	    /**  
	     * 将一个文件列表文件中所有文件拷贝到指定目录中  
	     * @param listFile  包含需要拷贝的文件的列表的文件，每个文件写在一行  
	     * @param targetFloder  目标目录  
	     */  
	    public static void copyFilesFromList(String listFile, String targetFloder) {   
	        BufferedReader reader = null;   
	        try {   
	            reader = new BufferedReader(new FileReader(listFile));   
	            String tempString = null;   
	            while ((tempString = reader.readLine()) != null) {   
	                copyFile(tempString, targetFloder);   
	            }   
	            reader.close();   
	        } catch (IOException e) {   
	            e.printStackTrace();   
	        } finally {   
	            if (reader != null) {   
	                try {   
	                    reader.close();   
	                } catch (IOException e1) {   
	                }   
	            }   
	        }   
	    }   
	  
	    /**  
	     * 拷贝文件  
	     * @param oldPath  源文件  
	     * @param tdir     目标目录
	     */  
	    public static void copyFile(String oldPath, String tdir) {   
	        try {   
	            File temp = new File(oldPath);   
	            FileInputStream input = new FileInputStream(temp);   
	            FileOutputStream output = new FileOutputStream(tdir + File.separator   + (temp.getName()).toString());   
	            byte[] b = new byte[1024 * 5];   
	            int len;   
	            while ((len = input.read(b)) != -1) {   
	                output.write(b, 0, len);   
	            }   
	            output.flush();   
	            output.close();   
	            input.close();   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	        }   
	    }   
	    
	    /**  
	     * 拷贝文件  
	     * @param oldPath  源文件  
	     * @param tdir     目标目录
	     */  
	    public static void copyFile(String oldPath, String tdir,String newName) {   
	        try {   
	            File temp = new File(oldPath);   
	            FileInputStream input = new FileInputStream(temp);   
	            FileOutputStream output = new FileOutputStream(tdir + File.separator   + newName);   
	            byte[] b = new byte[1024 * 5];   
	            int len;   
	            while ((len = input.read(b)) != -1) {   
	                output.write(b, 0, len);   
	            }   
	            output.flush();   
	            output.close();   
	            input.close();   
	        } catch (Exception e) {   
	            e.printStackTrace();   
	        }   
	    }   
	  
	    /**  
	     * 删除文件列表  
	     * @param files  需要删除的文件/文件夹列表  
	     * @return 删除成功true，否则返回false  
	     */  
	    public static boolean deleteFiles(List<String> files) {   
	        boolean flag = true;   
	        for (String file : files) {   
	            flag = delete(file);   
	            if (!flag)   
	                break;   
	        }   
	        return flag;   
	    }   
	  
	    /**  
	     * 删除文件或文件夹  
	     * @param fileName  要删除的文件名  
	     * @return 删除成功返回true，否则返回false  
	     */  
	    public static boolean delete(String fileName) {   
	        File file = new File(fileName);   
	        if (!file.exists()) {   
	            return false;   
	        } else {   
	            if (file.isFile())   
	                return deleteFile(fileName);   
	            else  
	                return deleteDirectory(fileName);   
	        }   
	    }   
	  
	    /**  
	     * 删除文件  
	     * @param fileName  要删除的文件的文件名  
	     * @return 删除成功返回true，否则返回false  
	     */  
	    public static boolean deleteFile(String fileName) {   
	        File file = new File(fileName);   
	        if (file.exists() && file.isFile()){   
	            return file.delete();  
	        }
	        return false;   
	    }   
	  
	    /**  
	     * 删除目录及目录下的文件  
	     * @param dir   要删除的目录路径  
	     * @return 删除成功返回true，否则返回false  
	     */  
	    public static boolean deleteDirectory(String dir) {   
	        if (!dir.endsWith(File.separator)){   
	            dir = dir + File.separator;
	        }
	        File dirFile = new File(dir);   
	        if ((!dirFile.exists()) || (!dirFile.isDirectory())) { 
	            return false;  
	        }
	        
	        boolean flag = true;   
	        File[] files = dirFile.listFiles();   
	        for (int i = 0; i < files.length; i++) {   
	            if (files[i].isFile()) {   
	                flag = deleteFile(files[i].getAbsolutePath());   
	                if (!flag){   
	                    break; 
	                }
	            } else if (files[i].isDirectory()) {   
	                flag = deleteDirectory(files[i].getAbsolutePath());   
	                if (!flag){  
	                    break;   
	                }
	            }   
	        }   
	        if(!flag) {   
	            return false;   
	        }   
	        return dirFile.delete();   
	    } 
	    
	    public static boolean delete(File dir) {
	    	boolean b = true;
	    	if(dir != null && dir.exists()) {
	    		if(dir.isFile()) {
	    			b = dir.delete();
	    		}else {
	    	    	if(deleteSubFile(dir)) {
	    	    		dir.delete();
	    	    	}
	    		}
	    	}
	    	return b;
	    }
	    
	    /**  
	     * 删除目录及目录下的文件  
	     * @param dir   要删除的目录路径  
	     * @return 删除成功返回true，否则返回false  
	     */  
	    public static boolean deleteSubFile(File dir) {
	    	if(dir == null) {
	    		return false;
	    	}
	    	return deleteSubFile(dir.getAbsolutePath());
	    }
	    public static boolean deleteSubFile(String dir) {   
	        if (!dir.endsWith(File.separator)){   
	            dir = dir + File.separator;
	        }
	        File dirFile = new File(dir);   
	        if ((!dirFile.exists()) || (!dirFile.isDirectory())) { 
	            return false;  
	        }
	        
	        boolean flag = true;   
	        File[] files = dirFile.listFiles();   
	        for (int i = 0; i < files.length; i++) {   
	            if (files[i].isFile()) {   
	                flag = deleteFile(files[i].getAbsolutePath());   
	                if (!flag){   
	                    break; 
	                }
	            } else if (files[i].isDirectory()) {   
	                flag = deleteDirectory(files[i].getAbsolutePath());   
	                if (!flag){  
	                    break;   
	                }
	            }   
	        }   
	        if(!flag) {   
	            return false;   
	        }   
	        return true;   
	    } 
		
	    /**
		 * 剪切目录
		 */
		public static boolean cutDirectiory(String sourceFile, String desFile) {
			File source = new File(sourceFile);
			if (!source.exists()) {
				return false;
			}
			File des = new File(desFile);
			if (!des.exists()){
				des.mkdirs();//不存在目标文件就创建
			}

			File[] subFiles = source.listFiles();
			FileInputStream input = null;
			FileOutputStream output = null;
			try {
				for (int i = 0; i < subFiles.length; i++) {
					if (subFiles[i].isFile()) { // 如果是文件 则读源文件 写入目标文件
						input = new FileInputStream(subFiles[i]);
						output = new FileOutputStream(new File(desFile + "/"+ subFiles[i].getName()));
						byte[] b = new byte[1024 * 5];
						int len;
						while ((len = input.read(b)) != -1) { // 读文件
							output.write(b, 0, len);          // 向目标文件写文件
						}
						
						input.close();
						output.flush();
						output.close();
						
					} else if (subFiles[i].isDirectory()) { // 如果是文件夹 递归读取子文件或文件夹
						cutDirectiory(sourceFile + "/" + subFiles[i].getName(),desFile + "/" + subFiles[i].getName());
					}
				}
				return true;
			} catch (Exception e) {
				log.error("",e);
				return false;
			} finally {
				if (input != null){
					try {
						input.close();
					} catch (Exception e) {
						log.error("",e);
					}
				}
				if (output != null){
					try {
						output.close();
					} catch (Exception e) {
						log.error("",e);
					}
				}
				try {
					FileUtil.delete(source);
				} catch (Exception e) {
					log.error("",e);
				}
			}
		}
	    
	    /**  
	     * 剪切文件  
	     */  
	    public static boolean cutFile(String oldPath, String dir) {
	    	boolean rs = false;
	        try {   
	            File sourceFile = new File(oldPath);   
	            FileInputStream input = new FileInputStream(sourceFile);   
	            FileOutputStream output = new FileOutputStream(dir + File.separator  + (sourceFile.getName()).toString());   
	            byte[] b = new byte[1024 * 5];   
	            int len;   
	            while ((len = input.read(b)) != -1) {   
	                output.write(b, 0, len);   
	            }   
	            output.flush();   
	            output.close();   
	            input.close();  
	            sourceFile.delete();
	            rs = true;
	        } catch (Exception e) {   
	            log.error("",e);
	            rs = false;
	        }
	        return rs;
	    }
	    
		/**
		 * 判断是否为空白文件
		 */
		public static boolean isEmpty(String path) {
			boolean b = false;
			try {
				FileReader fr = new FileReader(path);
				if(fr.read()==-1){//判断是否已读到文件的结尾 
					b = true; 
				}else{ 
					b = false;
				}
				fr.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return b;
		}

		/*
		 * 判断该文件是否正被其他程序占用(待测)
		 * 这个在WIN下面基本可以 在其他平台上可能需要延迟判断. 你先读取文件大小休眠 然后再读取文件大小  比较  不过这个方法弊端也很严重  最好的办法可能还是需要靠底层C去提供 
		 */
		public static boolean employFile(String path) {
			boolean b = false;
			File file = new File(path);   
			if(file.renameTo(file)){ 
				b = false;//文件未被操作 
			}else{ 
				b = true;//文件正在被操作
			}
			return b;
		}
		
		/**
		 * 根据路径创建一系列的目录
		 * @param path
		 */
		public static boolean mkDirectory(String path) {
			File file = null;
			try {
				file = new File(path);
				if (!file.exists()) {
					return file.mkdirs();
				}
			} catch (RuntimeException e) {
				e.printStackTrace();
			} finally {
				file = null;
			}
			return false;
		}
		
		/**
		 * 新建文件
		 */
		public void newFile(String filePathAndName, String fileContent) {
			try {
				String filePath = filePathAndName;
				filePath = filePath.toString();
				File myFilePath = new File(filePath);
				if (!myFilePath.exists()) {
					myFilePath.createNewFile();
				}
				FileWriter resultFile = new FileWriter(myFilePath);
				PrintWriter myFile = new PrintWriter(resultFile);
				String strContent = fileContent;
				myFile.println(strContent);
				resultFile.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}  

	/**
	 * 获取配置文件下的参数
	 */
	public static String getProperties(String paramName,String propertiesFileName) {
		String paramValue = "";
		File file = new File(propertiesFileName);
		try {
			FileInputStream fileInput = new FileInputStream(file);
	        Properties properties = new Properties();
	        properties.load(fileInput);
	        fileInput.close();
	        paramValue = properties.getProperty(paramName);
	        properties.clone();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paramValue;
	}
	
	/**
	 * 获取配置文件下的所有参数
	 */
	public static Map getProperties(String propertiesFileName) {
		Map paramMap = new HashMap();
		File file = new File(propertiesFileName);
		try {
			FileInputStream fileInput = new FileInputStream(file);
	        Properties properties = new Properties();
	        properties.load(fileInput);
	        fileInput.close();
	        Set set = properties.keySet();
	        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				paramMap.put(key, properties.get(key));
			}
	        properties.clone();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paramMap;
	}
	
	/**
	 * 数据追加写入到文件
	 */
	public static void writeFile(String path,Map map) {
        try {
        	RandomAccessFile rf=new RandomAccessFile(path,"rw"); 
        	rf.seek(rf.length());//将指针移动到文件末尾 	
        	Set set = map.keySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				String value = (String)map.get(key);
				rf.write(key.getBytes());
				rf.write(":".getBytes());
				rf.write(value.getBytes());
				rf.write(enter.getBytes());
			}
        	rf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 倒序读取n长度　
	 */
	public String invertedOrderRead(String path,long n){
		String tmp = "";
		try {
			RandomAccessFile rf = new RandomAccessFile(path,"r"); 
			long m = rf.length();
			if(m>n){
				rf.seek(rf.length()-n);//将指针移动到文件末尾 
				int c=rf.read();//读取一个字节 
				while(c!=-1){ 
				   tmp = tmp + (char)c; 
				   c=rf.read(); 
				} 
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return tmp;
	}
	
	/**
	 * 将信息写入目标文件(每次清空后写)
	 */
	public static void writeTargetFile(String path,Map map) {
		if(path != null && !"".equals(path) && map != null && map.size()>0){
			try {
				FileWriter fw = new FileWriter(path);
				BufferedWriter bw = new BufferedWriter(fw);
				
				Set set = map.keySet();
				for (Iterator iterator = set.iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					String value = (String)map.get(key);
					bw.write(value); 
					bw.newLine();//断行 
					bw.flush();//将数据更新至文件 
				}
				if(fw != null){
					fw.close();
				}
				if(bw != null){
					bw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}	
	}
	
	/**
	 * 返回上传的结果，成功与否
	 * @param uploadFileName
	 * @param savePath
	 * @param uploadFile
	 * @return
	 */
	public static boolean upload(String uploadFileName, String savePath, File uploadFile) {
		boolean flag = false;
		try {
			uploadForName(uploadFileName, savePath, uploadFile);
			flag = true;
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 判断文件是否存在
	 * @param fileName
	 * @param dir
	 * @return
	 */
	public static boolean isFileExist(String fileName, String dir) {
		File files = new File(dir + fileName);
		return (files.exists()) ? true : false;
	}
	
	/**
	 * 判断文件名是否已经存在，如果存在则在后面家(n)的形式返回新的文件名，否则返回原始文件名 例如：已经存在文件名 log4j.htm 则返回log4j(1).htm
	 * @param fileName 文件名
	 * @param dir  判断的文件路径
	 * @return 判断后的文件名
	 */
	public static String checkFileName(String fileName, String dir) {
		boolean isDirectory = new File(dir + fileName).isDirectory();
		if (FileUtil.isFileExist(fileName, dir)) {
			int index = fileName.lastIndexOf(".");
			StringBuffer newFileName = new StringBuffer();
			String name = isDirectory ? fileName : fileName.substring(0, index);
			String extendName = isDirectory ? "" : fileName.substring(index);
			int nameNum = 1;
			while (true) {
				newFileName.append(name).append("(").append(nameNum).append(")");
				if (!isDirectory) {
					newFileName.append(extendName);
				}
				if (FileUtil.isFileExist(newFileName.toString(), dir)) {
					nameNum++;
					newFileName = new StringBuffer();
					continue;
				}
				return newFileName.toString();
			}
		}
		return fileName;
	}

	/**
	 * 上传文件并返回上传后的文件名
	 * @param uploadFileName 被上传的文件名称
	 * @param savePath  文件的保存路径
	 * @param uploadFile 被上传的文件
	 * @return 成功与否
	 * @throws IOException
	 */
	public static String uploadForName(String uploadFileName, String savePath, File uploadFile) throws IOException {
		String newFileName = checkFileName(uploadFileName, savePath);
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			fos = new FileOutputStream(savePath + newFileName);
			fis = new FileInputStream(uploadFile);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return newFileName;
	}

	/**
	 * 将对象数组的每一个元素分别添加到指定集合中,调用Apache commons collections 中的方法
	 * @param collection 目标集合对象
	 * @param arr 对象数组
	 */
	public static void addToCollection(Collection collection, Object[] arr) {
		if (null != collection && null != arr) {
			CollectionUtils.addAll(collection, arr);
		}
	}

	/**
	 * 将字符串已多个分隔符拆分为数组,调用Apache commons lang 中的方法
	 *  Example:
	 *  String[] arr = StringUtils.split(&quot;a b,c d,e-f&quot;, &quot; ,&quot;);
	 *  System.out.println(arr.length);//输出6
	 * @param str 目标字符串
	 * @param separatorChars分隔符字符串
	 * @return 字符串数组
	 */
	public static String[] split(String str, String separatorChars) {
		return StringUtils.split(str, separatorChars);
	}

	/**
	 * 调用指定字段的setter方法
	 * Example:
	 * User user = new User();
	 * MyUtils.invokeSetMethod(&quot;userName&quot;, user, new Object[] {&quot;张三&quot;});
	 * @param fieldName 字段(属性)名称
	 * @param invokeObj 被调用方法的对象
	 * @param args 被调用方法的参数数组
	 * @return 成功与否
	 */
	public static boolean invokeSetMethod(String fieldName, Object invokeObj, Object[] args) {
		boolean flag = false;
		Field[] fields = invokeObj.getClass().getDeclaredFields(); // 获得对象实体类中所有定义的字段
		Method[] methods = invokeObj.getClass().getDeclaredMethods(); // 获得对象实体类中所有定义的方法
		for (Field f : fields) {
			String fname = f.getName();
			if (fname.equals(fieldName)) {// 找到要更新的字段名
				String mname = "set" + (fname.substring(0, 1).toUpperCase() + fname.substring(1));// 组建setter方法
				for (Method m : methods) {
					String name = m.getName();
					if (mname.equals(name)) {
						// 处理Integer参数
						if (f.getType().getSimpleName().equalsIgnoreCase("integer") && args.length > 0) {
							args[0] = Integer.valueOf(args[0].toString());
						}
						// 处理Boolean参数
						if (f.getType().getSimpleName().equalsIgnoreCase("boolean") && args.length > 0) {
							args[0] = Boolean.valueOf(args[0].toString());
						}
						try {
							m.invoke(invokeObj, args);
							flag = true;
						} catch (IllegalArgumentException e) {
							flag = false;
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							flag = false;
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							flag = false;
							e.printStackTrace();
						}
					}
				}
			}
		}
		return flag;
	}

	/**
	 * 获得随机文件名,保证在同一个文件夹下不同名
	 * @param fileName
	 * @param dir
	 * @return
	 */
	public static String getRandomName(String fileName, String dir) {
		String[] split = fileName.split("\\.");// 将文件名已.的形式拆分
		String extendFile = "." + split[split.length - 1].toLowerCase(); // 获文件的有效后缀

		Random random = new Random();
		int add = random.nextInt(1000000); // 产生随机数10000以内
		String ret = add + extendFile;
		while (isFileExist(ret, dir)) {
			add = random.nextInt(1000000);
			ret = fileName + add + extendFile;
		}
		return ret;
	}

	/**
	 * 判断文件类型是否是合法的,就是判断allowTypes中是否包含contentType
	 * @param contentType 文件类型
	 * @param allowTypes 文件类型列表
	 * @return 是否合法
	 */
	public static boolean isValid(String contentType, String[] allowTypes) {
		if (null == contentType || "".equals(contentType)) {
			return false;
		}
		for (String type : allowTypes) {
			if (contentType.equals(type)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 多文件压缩
	 * Example : 
	 *    ZipOutputStream zosm = new ZipOutputStream(new FileOutputStream(&quot;c:/b.zip&quot;));
	 *    zipFiles(zosm, new File(&quot;c:/com&quot;), &quot;&quot;);
	 *    zosm.close();
	 * @param zosm
	 * @param file
	 * @param basePath
	 * @throws IOException
	 */
	public static void compressionFiles(ZipOutputStream zosm, File file, String basePath) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			try {
				zosm.putNextEntry(new ZipEntry(basePath + "/"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			basePath = basePath + (basePath.length() == 0 ? "" : "/") + file.getName();
			for (File f : files) {
				compressionFiles(zosm, f, basePath);
			}
		} else {
			FileInputStream fism = null;
			BufferedInputStream bism = null;
			try {
				byte[] bytes = new byte[1024];
				fism = new FileInputStream(file);
				bism = new BufferedInputStream(fism, 1024);
				basePath = basePath + (basePath.length() == 0 ? "" : "/") + file.getName();
				zosm.putNextEntry(new ZipEntry(basePath));
				int count;
				while ((count = bism.read(bytes, 0, 1024)) != -1) {
					zosm.write(bytes, 0, count);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (bism != null) {
					try {
						bism.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fism != null) {
					try {
						fism.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 解压缩zip文件
	 * @param zipFileName 压缩文件
	 * @param extPlace  解压的路径
	 */
	public static boolean decompressionZipFiles(String zipFileName, String extPlace) {
		boolean flag = false;
		try {
			unZip(zipFileName,extPlace);
			flag = true;
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	private static void getDir(String directory, String subDirectory){
	     String dir[];
	     File fileDir = new File(directory);
	     try {
		      if (subDirectory == "" && fileDir.exists() != true){
		          fileDir.mkdir();
		      }else if (subDirectory != "") {
			       dir = subDirectory.replace('\\', '/').split("/");
			       for (int i = 0; i < dir.length; i++) {
				        File subFile = new File(directory + File.separator + dir[i]);
				        if (subFile.exists() == false)
				        subFile.mkdir();
				        directory += File.separator + dir[i];
			       }
		      }
	     }catch (Exception ex) {
	         System.out.println(ex.getMessage());
	     }
	 }
	
	 /**
	  * @param zipFileNaame      being unzip file including  file name and path ;   
	  * @param outputDirectory    unzip files to this directory
	  *
	  */ 
	 public static  void unZip(String zipFileName, String outputDirectory){
	     try {
	      ZipFile zipFile = new ZipFile(zipFileName);
	      java.util.Enumeration e = zipFile.getEntries();
	      ZipEntry zipEntry = null;
	      getDir(outputDirectory, "");
	      while (e.hasMoreElements()) {
	          zipEntry = (ZipEntry) e.nextElement();
	          //System.out.println("unziping " + zipEntry.getName());
	          if (zipEntry.isDirectory()) {                //如果得到的是个目录，
		          String name = zipEntry.getName();         //  就创建在指定的文件夹下创建目录
		          name = name.substring(0, name.length() - 1);
		          File f = new File(outputDirectory + File.separator + name);
		          f.mkdir();
		          //System.out.println("创建目录：" + outputDirectory + File.separator + name);
	          }else {
		          String fileName = zipEntry.getName();
		          fileName = fileName.replace('\\', '/');
		          // System.out.println("测试文件1：" +fileName);
		          if (fileName.indexOf("/") != -1){
			          getDir(outputDirectory,
			          fileName.substring(0, fileName.lastIndexOf("/")));
			          //System.out.println("文件的路径："+fileName);
			          fileName=fileName.substring(fileName.lastIndexOf("/")+1,fileName.length());
		          }
	              File f = new File(outputDirectory + File.separator + zipEntry.getName());

	              f.createNewFile();
	              InputStream in = zipFile.getInputStream(zipEntry);
	              FileOutputStream out=new FileOutputStream(f);

	              byte[] by = new byte[1024];
	              int c;
	              while ( (c = in.read(by)) != -1) {
	                   out.write(by, 0, c);
		          }
		          out.close();
		          in.close();
	           }
	       }
	     }catch (Exception ex) {
	        System.out.println(ex.getMessage());
	     }
	 }
	 
	 /**
	  * this mothed will unzip all the files  which in your specifeid  folder;
	  * @param filesFolder   
	  * @param outputDirectory       
	  */
	 public static  void unzipFiles(String filesFolder ,String outputDirectory){
		  File zipFolder=new File (filesFolder);
		  String zipFiles [];
		  String zipFileAbs;
		  try{
		      zipFiles=zipFolder.list();
		      for(int i=0;i<zipFiles.length;i++){
			      if(zipFiles[i].length()==(zipFiles[i].lastIndexOf(".zip")+4)){//判断是不是zip包 
			         zipFileAbs=filesFolder+File.separator+zipFiles[i];
			         unZip(zipFileAbs,outputDirectory);
			      }
		      }
		  }catch (SecurityException ex){
		     ex.printStackTrace();
		  }
	 }   

    /**  
     * 读取Jar包中的资源文件
     * 临时文件夹：
     * 1 读文件中，ftp到中央日志服务的文件，被放到临时文件夹后再读。  
     * 2 分析会后的日志，保存一个月。如果选择了备份，则把每天需要备份的文件移动到一个临时备份文件夹。  
     * 逻辑：如果getDirFromClassLoader()方法计算不出来path，就取System.getProperty("user.dir")用户工作目录  
     * */  
    public static String getJarDir(){   
        String path = getDirFromClassLoader();   
        if(path == null){   
            path = System.getProperty("user.dir");   
        }   
        return path;   
    }   
       
    /**  
     * 从通过Class Loading计算路径：  
     * 1 class文件通过jar包加载：  
     * 如果为jar包，该包为d:/test/myProj.jar  
     * 该方法返回d:/test这个目录（不受用户工作目录影响）  
     * 提示：在jar包加载 的时候，通过指定加载FileUtil的class资源得到jar:<url>!/{entry}计算出加载路径  
     * 2 class文件直接被加载：  
     * 如果是web工程,class文件放在D:\tools\apache-tomcat-5.5.27\webapps\webProj\WEB-INF\classes  
     * 该方法返回D:\tools\apache-tomcat-5.5.27\webapps\webProj\WEB-INF  
     * 即返回放class文件夹的上一层目录。  
     * */  
    private static String getDirFromClassLoader(){   
        try {   
            String path = FileUtil.class.getName().replace(".", "/");   
            path ="/"+path+".class";   
            URL url=FileUtil.class.getResource(path);   
            String jarUrl= url.getPath();   
            if(jarUrl.startsWith("file:")){   
                if(jarUrl.length()>5){   
                    jarUrl = jarUrl.substring(5);   
                }   
                jarUrl = jarUrl.split("!")[0];   
  
            }else{   
                jarUrl = FileUtil.class.getResource("/").toString().substring(5);   
            }   
            File file = new File(jarUrl);   
            return file.getParent();   
               
        } catch (Exception e) {   
        }   
        return null;   
    }  
    
    /**
	 * 以行为单位从一个文件读取数据
	 */
	public static String reedFile(String filePath,String str) {
		String s = "";
		try {
			String temp = "";
			BufferedReader in = new BufferedReader(new FileReader(filePath));
			while ((temp = in.readLine()) != null){
				s += temp + str;
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 接收键盘的输入
	 */
	public static String reedKey() {
		String s = "";
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		try {
			s = stdin.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	/**
	 * 从一个String对象中读取数据
	 */
	public static char[] reedString(String value) {
		char[] ch = null;
		if(value != null && value.length()>0){
			ch = new char[value.length()];
			StringReader in = new StringReader(value);
			int c;
			int i = 0;
			try {
				while ((c = in.read()) != -1){
					ch[i++] = (char)c;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			in.close();
		}
		return ch;
	}

	public static char[] readMemory(String value) {
		char[] ch = null;
		int c;
		int i = 0;
		if(value != null){
			ch = new char[value.length()];
			try {
				DataInputStream in = new DataInputStream(new ByteArrayInputStream(value.getBytes()));
				while((c = in.read()) != -1){
					ch[i++] = (char)c;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return ch;
	}
	
	/**
	 * 数据的存储
	 */
	public static void saveValue(String filePath){
		try {
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
			out.writeDouble(3.1415926);
			out.writeChars("\nThas was pi:writeChars\n");
			out.writeBytes("Thas was pi:writeByte\n");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 数据的恢复
	 */
	public static void readValue(String filePath){
		try {
			DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			System.out.println(in.readDouble());
			System.out.println(br.readLine());
			System.out.println(br.readLine());
			br.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  通过RandomAccessFile操作文件
	 */
	public static void randomAccessFile(String filePath){
		try {
			RandomAccessFile rf = new RandomAccessFile(filePath,"rw"); //读写
			for (int i = 0; i < 10; i++){
				rf.writeDouble(i * 1.414);
			}
			rf.close();
	
			rf = new RandomAccessFile(filePath, "r"); //读
			for (int i = 0; i < 10; i++){
				System.out.println("Value " + i + "：" + rf.readDouble());
			}
			rf.close();
	
			rf = new RandomAccessFile(filePath, "rw");//读写
			rf.seek(5 * 8);
			rf.writeDouble(47.0001);
			rf.close();
	
			rf = new RandomAccessFile(filePath, "r");//写
			for (int i = 0; i < 10; i++){
				System.out.println("Value " + i + "：" + rf.readDouble());
			}
			rf.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//FileUtil.mkFile(tFile);//文件不存在，创建
	public static void write(String filePath,String text,int saveType) {
		write(filePath,text,saveType,"GBK");
	}
	public static void write(String filePath,String text,int saveType,String charset) {
		if(text != null && !"".equals(text.trim())) {
			if(saveType == 2) {//追加到文件尾
				FileUtil.appendMethodA(filePath,text,charset);
			}else {//重写文件
				FileUtil.writeToFile(filePath,text,charset);
			}
		}
	}
	public static String write(String filePath,List<String> rs,int saveType) {
		return write(filePath,rs,saveType,"GBK");
	}
	public static String write(String filePath,List<String> rs,int saveType,String charset) {
		StringBuffer sb = new StringBuffer();
		if(rs != null && rs.size() > 0) {
			for (int i = 0; i < rs.size(); i++) {
				String s = rs.get(i);
				if(s != null && !"".equals(s.trim())) {
					sb.append(s + EnterUtil.enter);
				}
			}
        }
		
		String text = sb.toString();
		try {
			FileUtil.write(filePath,text,saveType,charset);
		} catch (Exception e) {
			log.error("",e);
		}
		
		return text;
	}

	/**
	 * String filePath =this.servlet.getServletContext().getRealPath("/百事付日志记录.txt"); 覆盖的写
	 */
	public static void writeToFile(String filePath, String text) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
			bw.write(text);
			//bw.newLine();
			bw.flush();
			if (bw != null) {
				bw.close();
			}
		} catch (Exception e) {
			log.error("保存文件异常：",e);
		}
	}
	
	/**
	 * 新的文件名。
	 */
	public static String newFile(String fileName,String newSuffix) {
		if(fileName != null && !"".equals(fileName)) {
			File file = new File(fileName);
			
			//System.out.println("1---"+file.getAbsolutePath());
			
			int splitIndex = fileName.indexOf(".");
			if(splitIndex != -1) {
				 String suffix = fileName.substring(fileName.lastIndexOf("."));//test.txt = .txt
				 //System.out.println(suffix);
				 fileName = fileName.replaceAll("\\"+suffix, newSuffix);
			}
			//System.out.println("2---"+fileName);
		}
		return fileName;
	}
	public static void writeToFile(String filePath, String text,String charset) {
		try {
			File file = new File(filePath);
			if(!file.exists()) {
				file.createNewFile();
			}
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),charset));
			bw.write(text);
			//bw.newLine();
			bw.flush();
			if (bw != null) {
				bw.close();
			}
		} catch (Exception e) {
			log.error("保存文件异常：",e);
		}
	}

	/**
	 * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
	 */
	public static void readFileByBytes(String fileName) {
		File file = new File(fileName);
		InputStream in = null;
		try {
			// 一次读一个字节
			in = new FileInputStream(file);
			int tempbyte;
			while ((tempbyte = in.read()) != -1) {
				System.out.write(tempbyte);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			// 一次读多个字节
			byte[] tempbytes = new byte[100];
			int byteread = 0;
			in = new FileInputStream(fileName);
			showAvailableBytes(in);
			// 读入多个字节到字节数组中，byteread为一次读入的字节数
			while ((byteread = in.read(tempbytes)) != -1) {
				System.out.write(tempbytes, 0, byteread);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 以字符为单位读取文件，常用于读文本，数字等类型的文件
	 */
	public static void readFileByChars(String fileName) {
		System.out.println("---------------------------");
		File file = new File(fileName);
		Reader reader = null;
		try {
			System.out.println("以字符为单位读取文件内容，一次读一个字节：");
			// 一次读一个字符
			reader = new InputStreamReader(new FileInputStream(file));
			int tempchar;
			while ((tempchar = reader.read()) != -1) {
				// 对于windows下，\r\n这两个字符在一起时，表示一个换行。但如果这两个字符分开显示时，会换两次行。因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
				if (((char) tempchar) != '\r') {
					System.out.print((char) tempchar);
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("---------------------------");
		try {
			System.out.println("以字符为单位读取文件内容，一次读多个字节：");
			// 一次读多个字符
			char[] tempchars = new char[30];
			int charread = 0;
			reader = new InputStreamReader(new FileInputStream(fileName));
			// 读入多个字符到字符数组中，charread为一次读取字符数
			while ((charread = reader.read(tempchars)) != -1) {
				// 同样屏蔽掉\r不显示
				if ((charread == tempchars.length)&& (tempchars[tempchars.length - 1] != '\r')) {
					System.out.print(tempchars);
				} else {
					for (int i = 0; i < charread; i++) {
						if (tempchars[i] == '\r') {
							continue;
						} else {
							System.out.print(tempchars[i]);
						}
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public static void readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println("line " + line + ": " + tempString);
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 随机读取文件内容
	 */
	public static void readFileByRandomAccess(String fileName) {
		RandomAccessFile randomFile = null;
		try {
			System.out.println("随机读取一段文件内容：");
			// 打开一个随机访问文件流，按只读方式
			randomFile = new RandomAccessFile(fileName, "r");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 读文件的起始位置
			int beginIndex = (fileLength > 4) ? 4 : 0;
			// 将读文件的开始位置移到beginIndex位置。
			randomFile.seek(beginIndex);
			byte[] bytes = new byte[10];
			int byteread = 0;
			// 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
			// 将一次读取的字节数赋给byteread
			while ((byteread = randomFile.read(bytes)) != -1) {
				System.out.write(bytes, 0, byteread);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (randomFile != null) {
				try {
					randomFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 显示输入流中还剩的字节数
	 */
	private static void showAvailableBytes(InputStream in) {
		try {
			System.out.println("当前字节输入流中的字节数为:" + in.available());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
   //insert("F:\AttendanceActivity.java", 57, "插入的内容rn");
	public static void insert(String fileName,String content,Long pos) throws IOException{
		insert(fileName,content,pos,"GBK");
	}
   public static void insert(String fileName,String content,Long pos,String charset) throws IOException{
	   if(pos == null){
		   appendMethodA(fileName,content) ;
	   }else{
	       File file = File.createTempFile("tmp", null);
	       file.deleteOnExit();
	       RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
	       FileInputStream fileInputStream = new FileInputStream(file);
	       FileOutputStream fileOutputStream = new FileOutputStream(file);
	       raf.seek(pos);
	       byte[] buff = new byte[64];
	       int hasRead = 0;
	       while((hasRead = raf.read(buff)) > 0){
	           fileOutputStream.write(buff);
	       }
	       raf.seek(pos);
	       raf.write(content.getBytes(charset));
	       //追加文件插入点之后的内容
	       while((hasRead = fileInputStream.read(buff)) > 0){
	           raf.write(buff, 0, hasRead);
	       }
	       raf.close();
	       fileInputStream.close();
	       fileOutputStream.close();
	   }
    }

	/**
	 * 将内容追加到文件尾部
	 * A方法追加文件：使用RandomAccessFile
	 */
	public static void appendMethodA(String fileName,String content) {
		appendMethodA(fileName,content,"UTF-8");
	}
	public static void appendMethodA(String fileName,String content,String charset) {
		RandomAccessFile randomFile = null;
		try {
			// 打开一个随机访问文件流，按读写方式
			randomFile = new RandomAccessFile(fileName,"rw");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 将写文件指针移到文件尾。
			randomFile.seek(fileLength);
			randomFile.write(content.getBytes(charset)); //解决写入中文
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(randomFile != null){
				try {
					randomFile.close();
				} catch (Exception e2) {}
			}
		}
	}

	/**
	 * 将内容追加到文件尾部.
	 * B方法追加文件：使用FileWriter
	 */
	public static void appendMethodB(String fileName, String content) {
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static InputStream byte2Input(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }

    public static byte[] input2byte(InputStream inStream)throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }
    
    /**
	 * String与InputStream相互转换.
	 */
	public static InputStream string2InputStream(String str) {
		return string2InputStream(str,null) ;
	}
	public static InputStream string2InputStream(String str,String charset) {
		InputStream is = null;
		try {
			if(StringUtil.isEmpty(charset)){
				is =new ByteArrayInputStream(str.getBytes());  
			}else{
				is =new ByteArrayInputStream(str.getBytes(charset));  
			}
			
		} catch (Exception e) {
			log.error("",e); 
		}
		return is;
	}
	
	/**
	 * InputStream与String相互转换.
	 */
	public static String inputStream2String1(InputStream in){ 
        String str = "";
        StringBuffer out = null;
        try {
        	 out = new StringBuffer(); 
        	 byte[] b = new   byte[4096]; 
             for(int n;(n = in.read(b))!= -1;){ 
                out.append(new   String(b,0,n)); 
             } 
             str = out.toString(); 
		} catch (Exception e) {
			log.error("",e); 
		} finally {   
            try {   
            	if(in != null){
            		in.close();
            	}
            } catch (IOException e) {   
            	 log.error("",e); 
            }   
        }   
        return str;
    } 
	public static String inputStream2String2(InputStream in) {
	   BufferedReader reader = new BufferedReader(new InputStreamReader(in));   
	   StringBuilder sb = new StringBuilder();   
       String line = null;   
        try {   
            while ((line = reader.readLine()) != null) {   
                sb.append(line + EnterUtil.enter);   
            }   
        } catch (IOException e) {   
            log.error("",e);
        } finally {   
            try {   
            	if(reader != null){
            	   reader.close();
            	}
            } catch (IOException e) {   
            	 log.error("",e); 
            }   
        }   
        return sb.toString();   
	} 
	public static String inputStream2String3(InputStream in){ 
		return inputStream2String3(in,null);
	}
	public static String inputStream2String3(InputStream in,String charset){ 
		String str = "";
		ByteArrayOutputStream baos = null;
		try {   
			baos = new ByteArrayOutputStream(); 
	        int i=-1; 
	        while((i=in.read())!=-1){ 
	           baos.write(i); 
	        } 
	       if(charset == null || "".equals(charset)){
	    	   return baos.toString(); 
	       }
	       return baos.toString(charset); 
        } catch (IOException e) {   
            log.error("",e);
        } finally {   
            try {   
            	if(baos != null){
            		baos.close();
            	}
            } catch (IOException e) {   
                log.error("",e); 
            }   
        }
        return str;
    }
}
