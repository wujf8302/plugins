package com.plugin.api.cipher;

import java.io.IOException;
import javax.xml.bind.DatatypeConverter;
/**
 * 
 * @author wujf
 *
 */
public class BASE64Coder {
	
	 /**
     * BASE64解密.
     */
	public static byte[] decode(String base64) throws IOException {
		return DatatypeConverter.parseBase64Binary(base64);
	}
    public static byte[] decryptBASE64(String key) throws Exception {
        //return (new BASE64Decoder()).decodeBuffer(key);
    	return decode(key);
    }

    /**
     * BASE64加密.
     */
	public static String encode(byte[] data) {
		return DatatypeConverter.printBase64Binary(data);
	}
    public static String encryptBASE64(byte[] key) throws Exception {
        //return (new BASE64Encoder()).encodeBuffer(key);
        return encode(key);
    }
}
