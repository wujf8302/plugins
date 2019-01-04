package com.wujf.study.collection.test1;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试: List的 subList() , toArray().
 * @author fj
 */
public class ETestList3 {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		List list = new ArrayList();

		list.add(new String("a"));
		list.add(new String("b"));
		list.add(new String("c"));
		list.add(new String("1"));
		list.add(new String("2"));
		list.add(new String("3"));
		
		System.out.println(list);

		List slist = list.subList(1, 3);// 得到子集.
		System.out.println();
		System.out.println(slist);

		Object[] obj = list.toArray();// 转成数组.
		for (int i = 0; i < obj.length; i++) {
			System.out.println(obj[i]);
		}
	}
}
