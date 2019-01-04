package com.wujf.study.collection.test1;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 遍历Set;
 * @author fj
 */
public class CTestSet2 {
	
	public static void main(String[] args) {

		Set set = new HashSet();

		set.add(new String("abc"));
		set.add(new Integer(123));
		set.add(new String("abc"));
		set.add(new Boolean(true));
		set.add(null);
		set.add(null);
		set.add(new Integer(123));

		// System.out.println(set);

		Iterator it = set.iterator();
		while (it.hasNext()) {
			Object obj=it.next();
			System.out.println(obj);
		}
	}
}
