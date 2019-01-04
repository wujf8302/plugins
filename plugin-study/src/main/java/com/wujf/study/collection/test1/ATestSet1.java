package com.wujf.study.collection.test1;

import java.util.HashSet;
import java.util.Set;

/**
 * 测试Set接口特点. 1不可重复，2无序，3只允许加入有一个null值.
 * @author fj
 */
public class ATestSet1 {
	
	public static void main(String[] args) {

		Set set = new HashSet();

		// set.add(new String("abc"));
		// set.add(new Integer(123));
		// set.add(new String("abc"));
		// set.add(new Boolean(true));
		// set.add(null);
		// set.add(null);
		// set.add(new Integer(123));
		// set.add( new Student(98,"小张") );
		
		set.add( new Student(98,"小张") );
		set.add( new Student(98,"小张") );

		System.out.println(set);
	}
}
