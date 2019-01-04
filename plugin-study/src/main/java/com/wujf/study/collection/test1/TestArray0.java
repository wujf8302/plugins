package com.wujf.study.collection.test1;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试Object类型的数组.上转型对象.
 * @author fj
 */
public class TestArray0 {
	
	public static void main(String[] args) {

		//A a=new B();
		
		// 数组.
		Object[] obj = new Object[5];

		obj[0] = new String("abc");
		obj[1] = new Integer(123);
		obj[2] = new Student(90, "小张");
		obj[3] = new Student(60, "小王");
		obj[4] = new Student(80, "小李");

		// 取子元素
		//Student s1 = (Student) obj[3];
		//System.out.println(s1.english);

		//
		//
		// 集合
		List list = new ArrayList();
		
		list.add(new String("abc"));
		list.add(new Integer(123));
		list.add(new Student(90, "小张"));
		list.add(new Student(60, "小王"));
		list.add(new Student(80, "小李"));
		
		java.util.Iterator it=list.iterator();
		while(it.hasNext()){
			Object tobj=it.next();
			
			if(tobj instanceof String){
				String str=(String)tobj;
				System.out.println(str);
			}
			else if(tobj instanceof Integer){
				Integer int1=(Integer)tobj;
				System.out.println(int1);
			}
			else if(tobj instanceof Student){
				Student s1=(Student)tobj;
				System.out.println(s1.english);
			}
		}

		// Student s2 = (Student) list.get(3);
		// System.out.println(s2.english);
	}
}
