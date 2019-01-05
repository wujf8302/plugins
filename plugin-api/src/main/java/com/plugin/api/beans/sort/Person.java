package com.plugin.api.beans.sort;

/**
 * 要排序的元素对象 实现Comparator接口排序.
 * @author wujf
 */
public class Person {
	private int age;
	private String name;

	public Person(int age, String name) {
		this.age = age;
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return "Person{" + "age=" + age + ", name='" + name + '\'' + '}';
	}
}
