package com.wujf.study.collection.old2;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * 测试 Hashtable的使用.和HashMap相似. Enumerator 和 Iterator 功能相似用于遍历.
 * @author fj
 */
public class ATestHashtable1 {
	
	public static void main(String[] args) {
		
		Hashtable ht = new Hashtable();

		ht.put("a", "小张");
		ht.put("b", "小张");
		ht.put("c", "432");
		ht.put("d", "yrt");
		ht.put("e", "kjh");

		// 使用Enumeration遍历出 键-值 对 中的值.
		Enumeration mye = ht.elements();
		while (mye.hasMoreElements()) {
			Object obj = mye.nextElement();
			System.out.println(obj);
		}
	}
}
