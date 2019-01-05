package com.plugin.api;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;
/**
 * 字节工具类。
 * @author wujf
 */
public class ByteUtil {
	
	private static Logger log = Logger.getLogger(ByteUtil.class);
	/**
	 * 将字符串转成字节数组
	 * @param charsetName
	 * @return
	 */
   public static byte[] charToByte(String charsetName){
	   return charsetName.getBytes();
   }
   
   /**
	 * 将字节数组转成字符串
	 * @param signature
	 * @return
	 */
   public static String byteToChar(byte[] bytes) {
		return new String(bytes);
   }
   
   public static byte[] hex2byte(byte b[]){
       if(b.length % 2 != 0)
           throw new IllegalArgumentException("\u957F\u5EA6\u4E0D\u662F\u5076\u6570");
       byte b2[] = new byte[b.length / 2];
       for(int n = 0; n < b.length; n += 2){
           String item = new String(b, n, 2);
           b2[n / 2] = (byte)Integer.parseInt(item, 16);
       }
       return b2;
	 }
	    
   public static String byte2hex(byte b[]){
       String hs = "";
       String stmp = "";
       for(int n = 0; n < b.length; n++){
           stmp = Integer.toHexString(b[n] & 0xff);
           if(stmp.length() == 1)
               hs = (new StringBuilder(String.valueOf(hs))).append("0").append(stmp).toString();
           else
               hs = (new StringBuilder(String.valueOf(hs))).append(stmp).toString();
       }
       return hs.toUpperCase();
   }
   
   //byte与16进制互转
   private static final char[] kDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',  'b', 'c', 'd', 'e', 'f' };   
   public static char[] bytesToHex(byte[] raw) {   
      int length = raw.length;   
      char[] hex = new char[length * 2];   
      for (int i = 0; i < length; i++) {   
        int value = (raw[i] + 256) % 256;   
        int highIndex = value >> 4;   
        int lowIndex = value & 0x0f;   
        hex[i * 2 + 0] = kDigits[highIndex];   
        hex[i * 2 + 1] = kDigits[lowIndex];   
      }   
      return hex;   
    }   
     
   public static byte[] hexToBytes(char[] hex) {   
      int length = hex.length / 2;   
      byte[] raw = new byte[length];   
      for (int i = 0; i < length; i++) {   
        int high = Character.digit(hex[i * 2], 16);   
        int low = Character.digit(hex[i * 2 + 1], 16);   
        int value = (high << 4) | low;   
        if (value > 127)   
          value -= 256;   
        raw[i] = (byte) value;   
      }   
      return raw;
   }
	public static void putShort(byte b[], short s, int index) {
		b[index] = (byte) (s >> 8);
		b[index + 1] = (byte) (s >> 0);
	}

	public static void putReverseBytesShort(byte b[], short s, int index) {
		b[index] = (byte) (s >> 0);
		b[index + 1] = (byte) (s >> 8);
	}

	public static short getShort(byte[] b, int index) {
		return (short) (((b[index] << 8) | b[index + 1] & 0xff));
	}

	public static short getReverseBytesShort(byte[] b, int index) {
		return (short) (((b[index + 1] << 8) | b[index] & 0xff));
	}

	// ///////////////////////////////////////////////////////
	public static void putInt(byte[] bb, int x, int index) {
		bb[index + 0] = (byte) (x >> 24);
		bb[index + 1] = (byte) (x >> 16);
		bb[index + 2] = (byte) (x >> 8);
		bb[index + 3] = (byte) (x >> 0);
	}

	public static void putReverseBytesInt(byte[] bb, int x, int index) {
		bb[index + 3] = (byte) (x >> 24);
		bb[index + 2] = (byte) (x >> 16);
		bb[index + 1] = (byte) (x >> 8);
		bb[index + 0] = (byte) (x >> 0);
	}

	public static int getInt(byte[] bb, int index) {
		return (int) ((((bb[index + 0] & 0xff) << 24)
				| ((bb[index + 1] & 0xff) << 16)
				| ((bb[index + 2] & 0xff) << 8) | ((bb[index + 3] & 0xff) << 0)));
	}

	public static int getReverseBytesInt(byte[] bb, int index) {
		return (int) ((((bb[index + 3] & 0xff) << 24)
				| ((bb[index + 2] & 0xff) << 16)
				| ((bb[index + 1] & 0xff) << 8) | ((bb[index + 0] & 0xff) << 0)));
	}

	// /////////////////////////////////////////////////////////
	public static void putLong(byte[] bb, long x, int index) {
		bb[index + 0] = (byte) (x >> 56);
		bb[index + 1] = (byte) (x >> 48);
		bb[index + 2] = (byte) (x >> 40);
		bb[index + 3] = (byte) (x >> 32);
		bb[index + 4] = (byte) (x >> 24);
		bb[index + 5] = (byte) (x >> 16);
		bb[index + 6] = (byte) (x >> 8);
		bb[index + 7] = (byte) (x >> 0);
	}

	public static void putReverseBytesLong(byte[] bb, long x, int index) {
		bb[index + 7] = (byte) (x >> 56);
		bb[index + 6] = (byte) (x >> 48);
		bb[index + 5] = (byte) (x >> 40);
		bb[index + 4] = (byte) (x >> 32);
		bb[index + 3] = (byte) (x >> 24);
		bb[index + 2] = (byte) (x >> 16);
		bb[index + 1] = (byte) (x >> 8);
		bb[index + 0] = (byte) (x >> 0);
	}

	public static long getLong(byte[] bb, int index) {
		return ((((long) bb[index + 0] & 0xff) << 56)
				| (((long) bb[index + 1] & 0xff) << 48)
				| (((long) bb[index + 2] & 0xff) << 40)
				| (((long) bb[index + 3] & 0xff) << 32)
				| (((long) bb[index + 4] & 0xff) << 24)
				| (((long) bb[index + 5] & 0xff) << 16)
				| (((long) bb[index + 6] & 0xff) << 8) | (((long) bb[index + 7] & 0xff) << 0));
	}

	public static long getReverseBytesLong(byte[] bb, int index) {
		return ((((long) bb[index + 7] & 0xff) << 56)
				| (((long) bb[index + 6] & 0xff) << 48)
				| (((long) bb[index + 5] & 0xff) << 40)
				| (((long) bb[index + 4] & 0xff) << 32)
				| (((long) bb[index + 3] & 0xff) << 24)
				| (((long) bb[index + 2] & 0xff) << 16)
				| (((long) bb[index + 1] & 0xff) << 8) | (((long) bb[index + 0] & 0xff) << 0));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		test2();
	}

	public static void test2() {
		short s = -20;
		byte[] b = new byte[2];
		putReverseBytesShort(b, s, 0);
		ByteBuffer buf = ByteBuffer.allocate(2);
		buf.put(b);
		buf.flip();
		System.out.println(getReverseBytesShort(b, 0));
		System.out.println(Short.reverseBytes(buf.getShort()));
		System.out.println("***************************");
		int i = -40;
		b = new byte[4];
		putReverseBytesInt(b, i, 0);
		buf = ByteBuffer.allocate(4);
		buf.put(b);
		buf.flip();
		System.out.println(getReverseBytesInt(b, 0));
		System.out.println(Integer.reverseBytes(buf.getInt()));
		System.out.println("***************************");
		long l = -50;
		b = new byte[8];
		putReverseBytesLong(b, l, 0);
		buf = ByteBuffer.allocate(8);
		buf.put(b);
		buf.flip();
		System.out.println(getReverseBytesLong(b, 0));
		System.out.println(Long.reverseBytes(buf.getLong()));
		System.out.println("***************************");
	}

	public static void test1() {
		short s = -20;
		byte[] b = new byte[2];
		putShort(b, s, 0);
		ByteBuffer buf = ByteBuffer.allocate(2);
		buf.put(b);
		buf.flip();
		System.out.println(getShort(b, 0));
		System.out.println(buf.getShort());
		System.out.println("***************************");
		int i = -40;
		b = new byte[4];
		putInt(b, i, 0);
		buf = ByteBuffer.allocate(4);
		buf.put(b);
		buf.flip();
		System.out.println(getInt(b, 0));
		System.out.println(buf.getInt());
		System.out.println("***************************");
		long l = -50;
		b = new byte[8];
		putLong(b, l, 0);
		buf = ByteBuffer.allocate(8);
		buf.put(b);
		buf.flip();
		System.out.println(getLong(b, 0));
		System.out.println(buf.getLong());
		System.out.println("***************************");
	}
}
