package com.wujf.study.collection.test3;

/**
 * 这是一个 Student 学生类,所有的学生对象都可由他创建.
 * @author fj
 */
public class Student{

	public int english; // 定义成员变量 english.

	public String name; // 定义成员变量 name.

	// 定义构造方法 则系统不再提供 默认的 无参数的 构造方法.
	public Student(int english, String name) {
		this.english = english;
		this.name = name;
	}
}
