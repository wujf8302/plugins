package com.plugin.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 校验工具类.
 * @author wujf
 * @version 1.0.0
 */
public class VerifyUtil {
    
    private static Log         log    = LogFactory.getLog(VerifyUtil.class);
    
    public static final String number = "^[-\\+]?\\d+$"; // 数字 ^[0-9]*$    ^\\d+$
    public static final String integer = "^\\d+$";       // 整数 ^[0-9]*$    ^\\d+$
    public static final String doubleOrFloat = "^[-\\+]?\\d+(\\.\\d+)?$";  
                                                                             
    public static void main(String[] args) {
        System.out.println(verifyParam("1", VerifyUtil.number));
        System.out.println(verifyParam("-1", VerifyUtil.number));
        System.out.println(verifyParam("1.0", VerifyUtil.number));
        System.out.println(verifyParam("-1.0", VerifyUtil.number));
        System.out.println(verifyParam("", VerifyUtil.number));
        System.out.println(verifyParam("s", VerifyUtil.number));
        System.out.println(verifyParam("是", VerifyUtil.number));
        System.out.println(verifyParam("1是1", VerifyUtil.number));
        System.out.println("------------------");
        System.out.println(verifyParam("3.00", VerifyUtil.doubleOrFloat));
        System.out.println(verifyParam("-3.00", VerifyUtil.doubleOrFloat));
        System.out.println(verifyParam("3", VerifyUtil.doubleOrFloat));
        System.out.println(verifyParam("-3", VerifyUtil.doubleOrFloat));
        System.out.println(verifyParam("0.3", VerifyUtil.doubleOrFloat));
        System.out.println(verifyParam("-0.3", VerifyUtil.doubleOrFloat));
        System.out.println(verifyParam("0.35", VerifyUtil.doubleOrFloat));
        System.out.println(verifyParam("-0.35", VerifyUtil.doubleOrFloat));
    }
    
    /**
     * @return 为空或null 返回true
     */
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s);
    }
    
    /**
     * @return 为null 返回true
     */
    public static boolean isNull(Object o) {
        return o == null;
    }
    
    public static Boolean verifyParam(String param, String regex) {
        if (param == null || "".equals(param)) {
            return false;
        }
        return new Boolean(param.matches(regex));
    }
    
    /**
     * java去除字符串中的空格、回车、换行符、制表符
     */
    public static String replaceBlank(String str) {
        if (str == null || "".equals(str)) {
            return str;
        }
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }
    
	/**
	 * 判断String是否是整数.
	 */
	public static boolean isInteger(String s) {
		return VerifyUtil.verifyParam(s, VerifyUtil.integer);
	}
	
	/**
	 * 判断String是否是数字.
	 */
	public static boolean isNumber(String s) {
		return VerifyUtil.verifyParam(s, VerifyUtil.number);
	}

	/**
	 * 判断字符串是否是浮点.
	 */
	public static boolean isDouble(String value) {
		return VerifyUtil.verifyParam(value, VerifyUtil.doubleOrFloat);
	}

	/**
	 * 判断字符串是否是数字【浮点、数字】.
	 */
	public static boolean isDigit(String value) {
		return isNumber(value) || isDouble(value);
	}
    
}
