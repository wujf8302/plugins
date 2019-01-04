package com.plugin.api;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * 工具类，生成uuid
 */
public class IDUtil extends Object {

	private String seedingString = "";
	private String rawGUID = "";
	private boolean bSecure = false;
	private static Random myRand;
	private static SecureRandom mySecureRand;

	private static String s_id;

	public static final int BeforeMD5 = 1;
	public static final int AfterMD5 = 2;
	public static final int FormatString = 3;

	static {
		mySecureRand = new SecureRandom();
		long secureInitializer = mySecureRand.nextLong();
		myRand = new Random(secureInitializer);
		try {
			s_id = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public IDUtil() {
		
	}

	public IDUtil(boolean secure) {
		bSecure = secure;
	}

	private void getRandomGUID(boolean secure) {
		MessageDigest md5 = null;
		StringBuffer sbValueBeforeMD5 = new StringBuffer();

		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error: " + e);
		}

		try {
			long time = System.currentTimeMillis();
			long rand = 0;

			if (secure) {
				rand = mySecureRand.nextLong();
			} else {
				rand = myRand.nextLong();
			}

			sbValueBeforeMD5.append(s_id);
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(time));
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(rand));

			seedingString = sbValueBeforeMD5.toString();
			md5.update(seedingString.getBytes());

			byte[] array = md5.digest();
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < array.length; ++j) {
				int b = array[j] & 0xFF;
				if (b < 0x10)
					sb.append('0');
				sb.append(Integer.toHexString(b));
			}

			rawGUID = sb.toString();

		} catch (Exception e) {
			System.out.println("Error:" + e);
		}
	}

	public String createNewGuid(int nFormatType, boolean secure) {
		getRandomGUID(secure);
		String sGuid = "";
		if (BeforeMD5 == nFormatType) {
			sGuid = this.seedingString;
		} else if (AfterMD5 == nFormatType) {
			sGuid = this.rawGUID;
		} else {
			sGuid = this.toString();
		}
		return sGuid;
	}

	public String createNewGuid(int nFormatType) {
		return this.createNewGuid(nFormatType, this.bSecure);
	}

	public String toString() {
		String raw = rawGUID.toUpperCase();
		StringBuffer sb = new StringBuffer();
		sb.append(raw.substring(0, 8));
		sb.append("-");
		sb.append(raw.substring(8, 12));
		sb.append("-");
		sb.append(raw.substring(12, 16));
		sb.append("-");
		sb.append(raw.substring(16, 20));
		sb.append("-");
		sb.append(raw.substring(20));

		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(new IDUtil().createNewGuid(IDUtil.FormatString));//00B595FF-241C-FE5B-2D93-82259F1A24BF
	}

}
