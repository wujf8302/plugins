package com.wujf.study.collection.test4;

import java.lang.Comparable; //可以不导入,因为系统默认导入java.lang包中所有类;

/**
 * 这是一个 Student 学生类,所有的学生对象都可由他创建.
 * @author fj
 */
public class Student1 implements Comparable {

	public int english; // 定义成员变量 english.

	public String name; // 定义成员变量 name.

	// 定义构造方法 则系统不再提供 默认的 无参数的 构造方法.
	public Student1(int english, String name) {
		this.english = english;
		this.name = name;
	}

	// 实现接口 Comparable 中的 compareTo 方法
	public int compareTo(Object b) {
		Student1 st = (Student1) b;
		int temp = this.english - st.english;
		if (temp == 0)
			temp = 1;
		return temp;
	}
}
