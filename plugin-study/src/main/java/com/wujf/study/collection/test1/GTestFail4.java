package com.wujf.study.collection.test1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 快速失败机制.
 * @author fj
 */
public class GTestFail4 {
	
	public static void main(String[] args) {

		List list = new ArrayList();
		//list.add(new String("abc"));
		//list.add(new Integer(123));
		list.add(new Student(90, "小张"));
		list.add(new Student(60, "小王"));
		list.add(new Student(80, "小李"));

		Iterator it = list.iterator();
		while (it.hasNext()) {
			Object obj=it.next();
			System.out.println(((Student)obj).english);
			//list.remove(obj); //并发
		}
	}
}
