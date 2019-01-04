package com.wujf.study.collection.old2;

import java.util.Vector;
import java.util.Enumeration;

/**
 * 测试 Vector 接口.
 * @author fj.
 */
public class BTestVector1 {

	public static void main(String args[]) {

		Vector myVector = null;
		myVector = new Vector();

		myVector.add(new Integer(3));
		myVector.add("it");
		myVector.add("is");
		myVector.add("a");
		myVector.add("door");
		myVector.add(new Integer(3)); // 加入相同数据: new Integer(3) .
		myVector.add("door"); // 加入相同数据: "door" .
		myVector.add(new Boolean(true));
		myVector.add(null);
		myVector.add(null);

		// System.out.println(myVector);//输出数据.

		// 使用 Enumeration 遍历.
		Enumeration enum1 = myVector.elements();
		while (enum1.hasMoreElements()) {
			Object obj = enum1.nextElement();
			System.out.println(obj);
		}
	}
}
