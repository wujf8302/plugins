package com.plugin.api;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DataFormater {
    private static final String[] hexDigits                = {"0", "1", "2",
            "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
    private static final String   LUCENE_SPECIAL_CHARS     = "[\\+\\-\\&&\\||\\!\\(\\)\\{\\}\\[\\]\\^\\\"\\~\\*\\?\\:\\\\]";
    private static final String   SEARCH_CHARS_REPLACEMENT = "[\\,\\.\\，\\。\\、]";
    private static final String[] LUCENE_CHARS_REPLACEMENT = {"\\\\",
            "\\\\\\\\", "\\+", "\\\\+", "-", "\\\\-", "&&", "\\\\&&", "[\\||]",
            "\\\\||", "!", "\\\\!", "\\{", "\\\\{", "\\}", "\\\\}", "\\[",
            "\\\\[", "\\]", "\\\\]", "\\(", "\\\\(", "\\)", "\\\\)", "\\^",
            "\\\\^", "\"", "\\\\\"", "~", "\\\\~", "\\*", "\\\\*", "\\?",
            "\\\\?", ":", "\\\\:"                         };
    
    public static String noNullValue(String arg) {
        String result = "";
        if (arg != null) {
            result = arg;
        }
        return result;
    }
    
    public static String noNullValue(Object arg) {
        String result = "";
        if (arg != null) {
            result = arg.toString();
        }
        return result;
    }
    
    public static String noNullValue(Date arg) {
        return noNullValue(arg, "yyyy年MM月dd日");
    }
    
    public static String noNullValue(Date arg, String format) {
        String result = "";
        if (arg != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            result = sdf.format(arg);
        }
        return result;
    }
    
    public static String noNullValue(Timestamp arg) {
        return noNullValue(arg, "yyyy年MM月dd日 HH时mm分ss秒");
    }
    
    public static String noNullValue(Timestamp arg, String format) {
        String result = "";
        if (arg != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            result = sdf.format(arg);
        }
        return result;
    }
    
    public static String noNullValue(double arg) {
        String result = "";
        if (arg != 0.0D) {
            result = Double.toString(arg);
        }
        return result;
    }
    
    public static String noNullValue(int arg) {
        String result = "";
        if (arg != 0) {
            result = Integer.toString(arg);
        }
        return result;
    }
    
    public static String noNullValue(long arg) {
        String result = "";
        if (arg != 0L) {
            result = Long.toString(arg);
        }
        return result;
    }
    
    public static String intToString(int number, int length) {
        String intStr = String.valueOf(number);
        if (intStr.length() > length)
            throw new IllegalArgumentException(
                "Argument 'length' is smaller than number's length.");
        if (intStr.length() < length) {
            StringBuffer tmpIntStr = new StringBuffer();
            for (int i = 0; i < length - intStr.length(); ++i) {
                tmpIntStr.append('0');
            }
            intStr = intStr;
        }
        return intStr;
    }
    
    public static String reverse(String srcString) {
        if (srcString != null) {
            StringBuffer sb = new StringBuffer(srcString);
            return sb.reverse().toString();
        }
        return "";
    }
    
    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
    
    public static String byteArrayToString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; ++i) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }
    
    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToString(md.digest(origin.getBytes()));
        } catch (Exception localException) {
        }
        return resultString;
    }
    
    public static String toDBC(String input) {
        if (input == null) {
            return "";
        }
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; ++i) {
            if (c[i] == '　') {
                c[i] = ' ';
            } else if ((c[i] > 65280) && (c[i] < 65375)) {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }
    
    public static String strEncoding(String sValue) {
        String sTemp = null;
        if (sValue == null)
            return "";
        try {
            sTemp = new String(sValue.getBytes("UTF-8"), "UTF-8");
            return sTemp;
        } catch (UnsupportedEncodingException e) {
        }
        return sValue;
    }
    
    public static String filterLuceneReservedWord(String orgStr,
        boolean useSpace) {
        String result = orgStr;
        if (useSpace)
            result = orgStr.replaceAll(
                "[\\+\\-\\&&\\||\\!\\(\\)\\{\\}\\[\\]\\^\\\"\\~\\*\\?\\:\\\\]",
                " ");
        else {
            result = orgStr.replaceAll(
                "[\\+\\-\\&&\\||\\!\\(\\)\\{\\}\\[\\]\\^\\\"\\~\\*\\?\\:\\\\]",
                "");
        }
        return result;
    }
    
    public static String filterInterpunction(String sOld, boolean useSpace) {
        String sResult = sOld;
        if (useSpace)
            sResult = sResult.replaceAll("[\\,\\.\\，\\。\\、]", " ");
        else {
            sResult = sResult.replaceAll("[\\,\\.\\，\\。\\、]", "");
        }
        
        sResult = sResult.replaceAll("\\s{2,}", " ");
        return sResult;
    }
    
    public static String convertLuceneReservedWord(String sOld) {
        String sResult = sOld;
        for (int i = 0; i < LUCENE_CHARS_REPLACEMENT.length; i += 2) {
            sResult = sResult.replaceAll(LUCENE_CHARS_REPLACEMENT[i],
                LUCENE_CHARS_REPLACEMENT[(i + 1)]);
        }
        return sResult;
    }
    
    public static void main(String[] arg) {
        String sSearch = "电信， 长乐、 你好。   谁说的";
        System.out.println("原文：" + sSearch);
        System.out.println("不用空格过滤：" + filterInterpunction(sSearch, false));
        System.out.println("使用空格过滤：" + filterInterpunction(sSearch, true));
    }
}
