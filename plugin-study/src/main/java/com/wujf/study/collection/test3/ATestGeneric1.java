package com.wujf.study.collection.test3;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试泛型的用法.
 * @author fj
 */
public class ATestGeneric1 {
	
	public static void main(String[] args) {
		
		//----泛型
		List<String> list=new ArrayList<String>();
		
		list.add( new String("ce") );
		list.add( new String("kd") );
		list.add( new String("au") );
		list.add( new String("dd") );
		
		//list.add( new Student(89,"小张") );//出错，类型不匹配.
	}

}
