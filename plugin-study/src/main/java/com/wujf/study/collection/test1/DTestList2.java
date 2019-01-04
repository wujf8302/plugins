package com.wujf.study.collection.test1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 遍历List. 三种方法.
 * @author fj.
 */
public class DTestList2 {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		List list = new ArrayList();

		list.add(new String("abc"));
		list.add(new Integer(123));
		list.add(new String("abc"));
		list.add(new Boolean(true));
		list.add(null);
		list.add(null);
		list.add(new Integer(123));

		// System.out.println(list);

		// 1. Iterator迭代器遍历
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Object obj1 = it.next();
			System.out.println(obj1);
		}

		System.out.println();
		System.out.println();
		int len = list.size();

		// 2. ListIterator迭代器遍历
		ListIterator lit = list.listIterator(len);
		while (lit.hasPrevious()) {
			Object obj1 = lit.previous();
			System.out.println(obj1);
		}

		System.out.println();
		System.out.println();

		// 3. for遍历
		for (int i = 0; i < len; i++) {
			Object obj2 = list.get(i); //ary[i]
			System.out.println(obj2);
		}
	}
}
