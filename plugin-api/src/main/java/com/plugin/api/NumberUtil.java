package com.plugin.api;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;

public class NumberUtil {

	private static Logger log = Logger.getLogger(NumberUtil.class);
	
	/**
	 * 根据给定的最小数字和最大数字，以及随机数的个数，产生指定的不重复的数组
	 * @param begin 最小数字（包含该数）
	 * @param end 最大数字（不包含该数）
	 * @param size  指定产生随机数的个数
	 */
	public static int[] generateRandomNumber(int begin, int end, int size) {
		// 加入逻辑判断，确保begin<end并且size不能大于该表示范围
		if (begin >= end || (end - begin) < size) {
			return null;
		}
		// 种子你可以随意生成，但不能重复
		int[] seed = new int[end - begin];

		for (int i = begin; i < end; i++) {
			seed[i - begin] = i;
		}
		int[] ranArr = new int[size];
		Random ran = new Random();
		// 数量你可以自己定义。
		for (int i = 0; i < size; i++) {
			// 得到一个位置
			int j = ran.nextInt(seed.length - i);
			// 得到那个位置的数值
			ranArr[i] = seed[j];
			// 将最后一个未用的数字放到这里
			seed[j] = seed[seed.length - 1 - i];
		}
		return ranArr;
	}

	/**
	 * 根据给定的最小数字和最大数字，以及随机数的个数，产生指定的不重复的数组
	 * @param begin 最小数字（包含该数）
	 * @param end   最大数字（不包含该数）
	 * @param size   指定产生随机数的个数
	 */
	public static Integer[] generateBySet(int begin, int end, int size) {
		// 加入逻辑判断，确保begin<end并且size不能大于该表示范围
		if (begin >= end || (end - begin) < size) {
			return null;
		}
		Random ran = new Random();
		Set<Integer> set = new HashSet<Integer>();
		while (set.size() < size) {
			set.add(begin + ran.nextInt(end - begin));
		}

		Integer[] ranArr = new Integer[size];
		ranArr = set.toArray(new Integer[size]);
		// ranArr = (Integer[]) set.toArray();

		return ranArr;
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
	
	/**
	 * 补n个0.
	 */
	public static String getNumber(int n) {
		String v = "1";
		for (int i = 0; i < n; i++) {
			v = v + "0";
		}
		return v;
	}
}
