package com.plugin.api;

import org.apache.log4j.Logger;

/**
 * 手机和PHS号码工具类，判断号码是否为移动，联通、电信的合法号码 采用正则表达式方式，具体号码信息是通过网上查找得来。
 * 
 * @author huangcm
 * @version 2009-1-28
 */
public class PhoneUtil {

	private static Logger log = Logger.getLogger(PhoneUtil.class);
	
	/**
	 * 第一个\为转义字符，\d为数字，{n,m}表示重复n到m次。
	 */

	/**
	 * 中国移动拥有号码段为:139,138,137,136,135,134,159,158,157(3G),151,150,188(3G),187(3G);13个号段
	 * 中国联通拥有号码段为:130,131,132,156(3G),186(3G),185(3G);6个号段
	 * 中国电信拥有号码段为:133,153,189(3G),180(3G);4个号码段 中国电信PHS号码：3-4位区号+(7-8)号码
	 */
	private static final String regMobileStr = "^1(([3][456789])|([5][01789])|([8][78]))[0-9]{8}$";
	private static final String regMobile3GStr = "^((157)|(18[78]))[0-9]{8}$";
	private static final String regUnicomStr = "^1(([3][012])|([5][6])|([8][56]))[0-9]{8}$";
	private static final String regUnicom3GStr = "^((156)|(18[56]))[0-9]{8}$";
	private static final String regTelecomStr = "^1(([3][3])|([5][3])|([8][09]))[0-9]{8}$";
	private static final String regTelocom3GStr = "^(18[09])[0-9]{8}$";
	private static final String regTelocomPHS = "^0(([1][0])|([2][0-9]{1})|([3-9][0-9]{2}))[0-9]{7,8}$";

	/**
	 * 简单判断是否为合法的号码
	 */
	private static final String regPhoneStr = "^(0([3-9]\\d\\d|10|2[0-9])[2-8]\\d{6,7}|((13|15|18)\\d{9}))$";

	/**
	 * 判断是否为合法的手机号码，不包括PHS
	 */
	public static boolean isValidPhone(String phone) {
		return phone.matches(regPhoneStr);
		// return isMobile(phone)||isUnicom(phone)||isTelecom(phone);
	}

	public static boolean isMobile(String phone) {
		if (phone == null) {
			return false;
		}
		/** 第一步判断中国移动 */
		return phone.matches(regMobileStr);
	}

	public static boolean isMobile3G(String phone) {
		if (phone == null) {
			return false;
		}
		return phone.matches(regMobile3GStr);
	}

	public static boolean isUnicom(String phone) {
		if (phone == null) {
			return false;
		}
		return phone.matches(regUnicomStr);
	}

	public static boolean isUnicom3G(String phone) {
		if (phone == null) {
			return false;
		}
		return phone.matches(regUnicom3GStr);
	}

	public static boolean isTelecom(String phone) {
		if (phone == null) {
			return false;
		}
		return phone.matches(regTelecomStr);
	}

	public static boolean isTelecom3G(String phone) {
		if (phone == null) {
			return false;
		}
		return phone.matches(regTelocom3GStr);
	}

	public static boolean isPHS(String phone) {
		if (phone == null) {
			return false;
		}
		return phone.matches(regTelocomPHS);
	}

	public static boolean judgetPhone(String phone, String reg) {

		if (phone == null || reg == null) {
			return false;
		}

		String str = reg;
		int len = str.length();

		str = str.replaceAll("\\?", "[0-9]");
		str = str.replaceAll("\\*", "[0-9]{0," + (21 - len) + "}");

		String regStr = "^" + str + "$";

		return phone.matches(regStr);
	}

	/**
	 * 测试
	 */
	public static void main(String[] args) throws Exception {
		String s = "0*?1000*";
		String no = "0210001";
		System.out.println(judgetPhone(no, s));
	}
}