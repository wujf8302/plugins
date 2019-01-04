package com.wujf.study.collection.test3;

import java.util.ArrayList;
import java.util.List;

/**
 * 增强的 for each循环
 * @author fj
 */
public class DTestFor1 {
	
	public static void main(String[] args) {

		List list = new ArrayList();
		list.add(new String("abc"));
		list.add(new Integer(123));
		list.add(new String("abc"));
		list.add(new Boolean(true));
		list.add(null);
		list.add(null);
		list.add(new Integer(123));

		// 遍历输出
		for (Object object : list) {
			System.out.println(object);
		}

		System.out.println();
		System.out.println();
		
		//
		List<String> list2 = new ArrayList<String>();
		list2.add(new String("efew"));
		list2.add(new String("dfd"));
		list2.add(new String("afdsabc"));
		list2.add(new String("hyhr"));
		list2.add(new String("oiu"));

		// 遍历输出
		for (String str : list2) {
			System.out.println(str);
		}
	}
}
