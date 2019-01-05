package com.plugin.api.sort;

import java.util.HashSet;
import java.util.Iterator;
/**
 * 
 * @author wujf
 */
public class HashSetTest {
	// java 中Set的使用(不允许有重复的对象):
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		HashSet hashSet = new HashSet();
		String a = new String("A");
		String b = new String("B");
		String c = new String("B");
		hashSet.add(a);
		hashSet.add(b);
		System.out.println(hashSet.size());
		String cz = hashSet.add(c) ? "此对象不存在" : "已经存在";
		System.out.println("测试是否可以添加对象 " + cz);
		System.out.println(hashSet.isEmpty());
		// 测试其中是否已经包含某个对象
		System.out.println(hashSet.contains("A"));
		Iterator ir = hashSet.iterator();
		while (ir.hasNext()) {
			System.out.println(ir.next());
		}
		// 测试某个对象是否可以删除
		System.out.println(hashSet.remove("a"));
		System.out.println(hashSet.remove("A"));
		// 经过测试,如果你想再次使用ir变量,必须重新更新以下
		ir = hashSet.iterator();
		while (ir.hasNext()) {
			System.out.println(ir.next());
		}
	}
}
