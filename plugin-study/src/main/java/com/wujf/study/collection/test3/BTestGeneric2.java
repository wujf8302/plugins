package com.wujf.study.collection.test3;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动装包/拆包
 * @author fj
 */
public class BTestGeneric2 {
	
	public static void main(String[] args) {

		// ---- 泛型
		List<Integer> list = new ArrayList<Integer>();

		list.add( new Integer(13) );
		list.add( new Integer(33) );
		list.add( new Integer(58) );
		list.add( 7 );// 自动装包
		
		// list.add( new Student(89,"小张") );出错，类型不匹配.

		int i1 = list.get(0);// 自动拆包

		System.out.println(i1);
	}
}
