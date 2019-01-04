package com.plugin.api.cipher;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.List;

import com.plugin.api.FileUtil;

public class MD5Coder {
    
    private static final String[] HEXCHAR = {"0", "1", "2", "3", "4", "5", "6","7", "8", "9", "A", "B", "C", "D", "E", "F" };
    
    private String byte2HexStr(byte b) {
        int i = (b < 0) ? 256 + b: b;
        return HEXCHAR[(i / 16)] + HEXCHAR[(i % 16)];
    }
    
    /**
     * 将文件读取为16进制String
     */
    public static String readOriginal2Hex(String fileName) throws IOException {
        FileInputStream fin = new FileInputStream(new File(fileName));
        StringWriter sw = new StringWriter();

        int len = 1;
        byte[] temp = new byte[len];

       /*16进制转化模块*/
        for (; (fin.read(temp, 0, len)) != -1;) {
            if (temp[0] > 0xf && temp[0] <= 0xff) { 
                sw.write(Integer.toHexString(temp[0]));
            } else if (temp[0] >= 0x0 && temp[0] <= 0xf) {//对于只有1位的16进制数前边补“0”
                sw.write("0" + Integer.toHexString(temp[0]));
            } else { //对于int<0的位转化为16进制的特殊处理，因为Java没有Unsigned int，所以这个int可能为负数
                sw.write(Integer.toHexString(temp[0]).substring(6));
            }
        }

        return sw.toString();
    }

    
    public static void main(String[] args) {
    	try {
    		String hex = readOriginal2Hex("d:/a.db");
    		System.out.println(hex);
			System.out.println(new String(hexString2Bytes(hex),"GBK"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    public static byte[] hexString2Bytes(String src) {  
        int l = src.length() / 2;  
        byte[] ret = new byte[l];  
        for (int i = 0; i < l; i++) {  
            ret[i] = (byte) Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();  
        }  
        return ret;  
    }  
    
    private String bytes2HexStr(byte[] bs) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bs.length; ++i) {
            sb.append(byte2HexStr(bs[i]));
        }
        return sb.toString();
    }
    
    public String MD5Encode(String str) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bs = md.digest(str.getBytes());
            result = bytes2HexStr(bs);
        } catch (Exception e) {
            System.err.println("生成md5加密串异常");
        }
        return result;
    }
}
