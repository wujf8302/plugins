package com.wujf.study.collection.test4;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * 这是一个测试 TreeSet 及 Comparable 接口的例子.
 * @author fj
 */

public class TestComparator2 {

	// 入口方法; 注"//"开始的注释不是文档化的注释,但也是必须,可增加程序的可读性;
	@SuppressWarnings("unchecked")
	public static void main(String args[]) {

		// 由TreeSet类创set接口的对象;
		Set myTreeSet = new TreeSet(new aaa());

		Student2 st1, st2, st3, st4; // 声明四个对象
		st1 = new Student2(90, "zhan ying");
		st2 = new Student2(66, "wang heng");
		st3 = new Student2(86, "Liu qing");
		st4 = new Student2(76, "yage ming");

		Student2 st5 = new Student2(86, "wang");

		myTreeSet.add(st1); // 将对象加入集合对象set中
		myTreeSet.add(st2);
		myTreeSet.add(st3);
		myTreeSet.add(st4);

		myTreeSet.add(st5);

		Iterator it = myTreeSet.iterator(); // 用迭代器遍历输出
		while (it.hasNext()) {
			Student1 st = (Student1) it.next();
			System.out.println("" + st.name + "    " + st.english);
		}
	}
}

class aaa implements Comparator {

	public int compare(Object o1, Object o2) {
		
		Student1 s1 = (Student1) o1;
		Student1 s2 = (Student1) o2;
		int temp = s1.english - s2.english;
		if (temp == 0)
			temp = 1;
		return temp;
	}
}
