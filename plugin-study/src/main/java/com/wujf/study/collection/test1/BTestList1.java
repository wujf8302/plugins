package com.wujf.study.collection.test1;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试List接口的特点. 1可重复, 2有序, 3允许有加入多个null值
 * @author fj.
 */
public class BTestList1 {
	
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
		
		list.add(new Student(98,"小张"));

		System.out.println(list);
	}
}
