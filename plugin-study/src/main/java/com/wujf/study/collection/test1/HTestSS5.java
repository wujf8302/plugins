package com.wujf.study.collection.test1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Collections , Arrays 的使用.
 * @author fj
 */
public class HTestSS5 {
	
	public static void main(String[] args) {

		// ----------
		List list = new ArrayList();
		list.add(new String("d"));
		list.add(new String("e"));
		list.add(new String("a"));
		list.add(new String("b"));
		list.add(new String("k"));
		list.add(new String("w"));

		System.out.print("排序前: ");
		System.out.println(list);
		Collections.sort(list);// 对集合 排序
		System.out.print("排序后: ");
		System.out.println(list);

		//
		System.out.println();
		System.out.println();

		// ----------
		String[] strAry = { "d", "e", "a", "b", "k", "w" };
		System.out.println("排序前: ");
		for (int i = 0; i < strAry.length; i++) {
			System.out.println(strAry[i]);
		}
		Arrays.sort(strAry);// 对数组 排序
		System.out.println("排序后: ");
		for (int i = 0; i < strAry.length; i++) {
			System.out.println(strAry[i]);
		}

		// ----------将字符串转为集合
		List mylist = Arrays.asList(strAry);
	}
}
