package com.plugin.api;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class CommonalityUtil {

	private static Logger log = Logger.getLogger(CommonalityUtil.class);
	
	public static final String NUMBER_REGEX = "^\\d+$";                 // 数字
	public static final String INTEGER_REGEX = "^[-\\+]?\\d+$";         // 正负数字
	public static final String MOBILE_REGEX = "(^0?[1][358][0-9]{9}$)"; // 手机号码
	public static final String PHONE_OR_MOBILE_REGEX = "^((\\(\\d{3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}$|(^0?[1][358][0-9]{9}$)";// 手机或固话

	private static boolean IS_REGEX_VERIFY_PRINT = false;
	
	public static void main(String[] args) {
		String val = "\"13306636435\"";
		boolean b = CommonalityUtil.verifyParam(val,CommonalityUtil.NUMBER_REGEX).booleanValue() && CommonalityUtil.verifyParam(val,CommonalityUtil.MOBILE_REGEX).booleanValue() &&places(1,11);
		log.info(String.valueOf(b));
		if(!b){
			log.info(val + " is error");
		}
		
		getXYTest();
	}
	
	public static void getXYTest() {
		int realityNum = 100000;
		int perNum = 1000;
		int currentRow = 47000;
		
		int[] xy = getXY(realityNum,perNum,currentRow);
		System.out.println(xy[0]);
		System.out.println(xy[1]);
	}
	
	/**
	 * 判断值域范围
	 */
	public static boolean places(int n,int m){
		if(n<=m){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据分隔符辟开字符串
	 */
	public static String[] splitRecord(String record,String separator){
		String spt = separator.replaceAll("\\\\", "");
		if(record != null && !"".equals(record) && record.indexOf(spt)!=-1){
			String[] columns = record.split((separator.indexOf("\\") != -1?"\\":"")+spt);
			return columns;
		}
		return null;
	}
	
	/**
	 * 判断是否为数字
	 */
	public static boolean isDigit(String str){   
		Pattern pattern = Pattern.compile("^(\\+|-)?[0-9]*");      
	    return pattern.matcher(str).matches();      
	}
	
	public static String[] copyArray(String[] src,int maxLen){
        if(src != null){
        	String[] columns = new String[maxLen];
        	
        	System.arraycopy(src, 0, columns, 0, src.length);
        	return columns;
        }
        return null;
	}
	
	public static void printColumns(String[] columns){
		if(columns != null){
			for (int i = 0; i < columns.length; i++) {
				System.out.println((i+1) + " - "+ columns[i]);
			}
		}
	}
	
	public static java.sql.Date utilDate2SqlDate(String s,String pattern) { 
		 java.sql.Date date = null;
		 try {
			 SimpleDateFormat format = new SimpleDateFormat(pattern);
			 date = new java.sql.Date(format.parse(s).getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	 }
	
	public static java.sql.Date utilDate2SqlDate(Date d,String pattern) { 
		 java.sql.Date date = null;
		 try {
			 SimpleDateFormat format = new SimpleDateFormat(pattern);
			 date = new java.sql.Date(d.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	 }
	 
	 public static java.util.Date sqlDate2UtilDate(java.sql.Date date) { 
		return date;
	 }
	 
	 public static boolean isEmpty(String val){
		 return val == null || "".equals(val);
	 }
	 
	 public static boolean isCollectionEmpty(Object val){
		 if(val instanceof List){
			 List list = (List)val;
			 return list == null || list.size()==0;
		 }else if(val instanceof Map){
			 Map map = (Map)val;
			 return map == null || map.size()==0;
		 }
		 return false;
		
	 }
	 
	 public static boolean isArrayEmpty(Object[] val){
		 return val == null || val.length==0;
	 }
	 
	 public static boolean isObjectNull(Object val){
		 return val == null;
	 }
	
	public static int[] getXY(int realityNum,int perNum,int currentRow){
		//log.info("realityNum: "+realityNum+"---perNum: "+perNum+"---currentRow: "+currentRow);
    	int[] xy = new int[2];
    	
    	xy[0] = 0;
    	if(perNum<currentRow){
    		xy[0] = (currentRow-1)/perNum*perNum;
    	}
    	int n = xy[0] + perNum;
    	if(n > realityNum){
    		xy[1] = realityNum;
    	}else {
    		xy[1] = n;
		}
    	
    	return xy;
	 }
	
	/**
	 * 将集合分隔成多个n长度子集合
	 */
    public static List subList(List dataList,int n) {
    	List sublists = new ArrayList();
    	if(dataList != null && dataList.size()>0){
        	
        	int m  = dataList.size();
        	int x = 0;
        	int y = m/n;
        	int z = 0;
        	
        	for (int i = 0; i <y; i++) {
        		x = i*n;
        		z = x+n;
        		List list = dataList.subList(x,z);
        		sublists.add(list);
    		}
            if(m%n>0){
            	List list = dataList.subList(z,dataList.size());
            	sublists.add(list);
    		}
    	}
    	
    	return sublists;
	}
    

	/**
	 * 根据指定的正则表达式校验参数
	 * @param param  参数
	 * @param regex  正则表达式
	 * @return 参数为空 返回null 匹配返回true 不匹配返回false
	 */
	public static Boolean verifyParam(String param, String regex) {
		if (param == null || "".equals(param)) {
			return null;
		}
		/*
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(param);
        */
		
		if(IS_REGEX_VERIFY_PRINT){
			log.info("正则表达式校验参数(param:" + param + "------regex:" + regex + "):"+ param.matches(regex));
		}

		return new Boolean(param.matches(regex));
	}
	
	public static String replaceCode(String val){
		if(val==null || val.equals("")) 
			return val;
    	String tmp=val.trim();
    	String result=tmp;
    	if(!PhoneUtil.isValidPhone(val))
    		return val;
        
	    if (tmp.substring(0,1).equals("0")) 
	    	result="86"+tmp.substring(1,tmp.length());
	    else
	    	if (tmp.length()>1){
	    		if (!tmp.substring(0,2).equals("86")) 
	    			result="86"+tmp;
	    	}
	    	else{
	    		result="86"+tmp;
	    	}
	    return result;
	}
	
	public static void printLog(Logger log,String msg){
		log.info(msg);
	}
	
	public static long getSeq(Connection connection,String sql) {
		long id = 0L;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				id = (long) rs.getLong("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			if(rs != null){
				try {
					rs.close();
				} catch (Exception e) {
					log.error(e.getMessage());
				}
				
			}
			if(stmt != null){
				try {
					stmt.close();
				} catch (Exception e) {
					log.error(e.getMessage());
				}
			}
		}

		return id;
	}
	
	public static void printByEncode(String msg) {
		try {
			System.out.println("I-G"
					+ new String(msg.getBytes("ISO-8859-1"), "GBK"));
			System.out.println("I-U8"
					+ new String(msg.getBytes("ISO-8859-1"), "UTF-8"));
			System.out.println("I-U"
					+ new String(msg.getBytes("ISO-8859-1"), "Unicode"));
			System.out.println("U8-G"
					+ new String(msg.getBytes("UTF-8"), "GBK"));
			System.out.println("U8-I"
					+ new String(msg.getBytes("UTF-8"), "ISO-8859-1"));
			System.out.println("U8-U"
					+ new String(msg.getBytes("UTF-8"), "Unicode"));
			System.out.println("G-U8"
					+ new String(msg.getBytes("GBK"), "UTF-8"));
			System.out.println("G-I"
					+ new String(msg.getBytes("GBK"), "ISO-8859-1"));
			System.out.println("G-U"
					+ new String(msg.getBytes("GBK"), "Unicode"));
			System.out.println("U-U8"
					+ new String(msg.getBytes("Unicode"), "UTF-8"));
			System.out.println("U-I"
					+ new String(msg.getBytes("Unicode"), "ISO-8859-1"));
			System.out.println("U-G"
					+ new String(msg.getBytes("Unicode"), "GBK"));
			System.out.println("U-GB2312"
					+ new String(msg.getBytes("Unicode"), "GB2312"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
