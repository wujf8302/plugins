package com.plugin.api;

import java.io.UnsupportedEncodingException;

public class CommonUtil {
    
    public static String enter = getEnter();
    
    public static void main(String[] args) {
        printByEncode("鎵句笉鍒伴厤缃枃浠�");
    }
    public static void printByEncode(String param){
        getParameter(param,"ISO-8859-1");
        getParameter(param,"UTF-8");
        getParameter(param,"GB2312");
        getParameter(param,"GBk");
        getParameter(param,"Unicode");
    }

    //request.getParameter(paramName),request.getCharacterEncoding()
    private static String getParameter(String param,String charset){
        if ((param != null) && (!"".equals(param))) {
            try {
                if(charset == null || "".equals(charset) || "iso-8859-1".equals(charset.toLowerCase())){
                    
                    charset = "ISO-8859-1";
                    
                    System.out.println(charset + " -> GB2312 = " + new String(param.getBytes(charset), "GB2312"));
                    System.out.println(charset + " -> GBK = " + new String(param.getBytes(charset), "GBK"));
                    System.out.println(charset + " -> UTF-8 = " + new String(param.getBytes(charset), "UTF-8"));
                    System.out.println(charset + " -> Unicode = " + new String(param.getBytes(charset), "Unicode"));
                    
                    return new String(param.getBytes(charset), "GBK");
                    
                }if("gb2312".equals(charset.toLowerCase())){
                    
                    charset = "GB2312";
                    
                    System.out.println(charset + " -> UTF-8 = " + new String(param.getBytes(charset), "UTF-8"));
                    System.out.println(charset + " -> ISO-8859-1 = " + new String(param.getBytes(charset), "ISO-8859-1"));
                    System.out.println(charset + " -> Unicode = " + new String(param.getBytes(charset), "Unicode"));
                    
                }if("gbk".equals(charset.toLowerCase())){
                    
                    charset = "GBK";
                    
                    System.out.println(charset + " -> UTF-8 = " + new String(param.getBytes(charset), "UTF-8"));
                    System.out.println(charset + " -> ISO-8859-1 = " + new String(param.getBytes(charset), "ISO-8859-1"));
                    System.out.println(charset + " -> Unicode = " + new String(param.getBytes(charset), "Unicode"));
                    
                }if("utf-8".equals(charset.toLowerCase())){
                    
                    charset = "UTF-8";
                    
                    System.out.println(charset + " -> GBK = " + new String(param.getBytes(charset), "GBK"));
                    System.out.println(charset + " -> ISO-8859-1 = " + new String(param.getBytes(charset), "ISO-8859-1"));
                    System.out.println(charset + " -> Unicode = " + new String(param.getBytes(charset), "Unicode"));
                    
                }if("unicode".equals(charset.toLowerCase())){
                    
                    charset = "Unicode";
                    
                    System.out.println(charset + " -> UTF-8 = " + new String(param.getBytes(charset), "UTF-8"));
                    System.out.println(charset + " -> ISO-8859-1 = " + new String(param.getBytes(charset), "ISO-8859-1"));
                    System.out.println(charset + " -> GBK = " + new String(param.getBytes(charset), "GBK"));
                    System.out.println(charset + " -> GB2312 = " + new String(param.getBytes(charset), "GB2312"));
                    
                }
            } catch (UnsupportedEncodingException e) {
                System.out.println("中文转换错误!!");
            }
            return param;
        }

        return "";
    }
    
    
    /**
     * 获取回车符
     */
    public static String getEnter(){
        String hc = "";
        if (osType().equals("windows")) {
            hc = "\r\n";
        } else if (osType().equals("linux")) {
            hc = "\n";
        } else {
            hc = "\r\n";
        }
        return hc;
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
    
    /**
     * 首字母变成小写
     */
    public static String firstToLower(String fieldName) {
        String s = "";
        if (fieldName != null && fieldName.length() >= 1) {
            String tmp1 = fieldName.substring(0, 1);
            String tmp2 = fieldName.substring(1);
            s = tmp1.toLowerCase() + tmp2;
        }
        
        return s;
    }
    
    /**
     * 首字母变成大写
     */
    public static String firstToUpperCase(String fieldName) {
        String s = "";
        if (fieldName != null && fieldName.length() >= 1) {
            String tmp1 = fieldName.substring(0, 1);
            String tmp2 = fieldName.substring(1);
            s = tmp1.toUpperCase() + tmp2;
        }
        
        return s;
    }
    
}
