package com.plugin.api.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
/**
 * 
 * @author wujf
 */
public class TreeSetTest {

	public static void main(String[] args) {
	 int x = 3 ;
		TreeSet tree = new TreeSet();
		tree.add("China");
		tree.add("America");
		tree.add("Japan");
		tree.add("Chinese");

		Iterator iter = tree.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
		System.out.println("---------------");
		int[] myArray = new int[] {1, 2, 3, 4, 5 };
		List myList = Arrays.asList(myArray);
		System.out.println(myList.size());
		for (int i = 0; i < myList.size(); i++) {
			System.out.println(myList.get(i));
		}
		
		System.out.println("---------------");//Arrays.asList(Object obj);
		String[] str = new String[]{"Larry", "Moe", "Curly"};
		Arrays.sort(str);//对数组进行排序
		List mList = Arrays.asList(str);
		System.out.println(mList.size());
		for (int i = 0; i < mList.size(); i++) {
			System.out.println(mList.get(i));
		}
		
	     Object[] obj={new Integer(1),new String("ok")};
	 	 //Arrays.sort(obj);//java.lang.ClassCastException
	     System.out.println("------------------");
	     Object[] obj2={new String("qo"),new String("ok")};
	 	 Arrays.sort(obj2);//Arrays.sort(obj); obj里存的必须是同一类型的数据
	     for (int i = 0; i < obj2.length; i++) {
			System.out.println(obj2[i]);
		}
	     
	     int n = 10;
	     int y =0;
	     y+=x+++n;//y=y+(x++)+n;
	     System.out.println(y);
	     System.out.println("------------------");
	     HashMap map = new HashMap();
	     map.put("key45", "吴剑飞");
	     map.put("key12", "陆小凤");
	     //Collections.sort(list对象);
	     Set s = map.keySet();
	     s = new TreeSet(s);//map的排序问题
	    for (Iterator iterator = s.iterator(); iterator.hasNext();) {
			String element = (String) iterator.next();
			System.out.println(element);
		}
	     System.out.println("------------------");
	     //List无序，允许多个null,允许空字符串,重复元素全部输出
	     List l = new ArrayList();
	     l.add("a");
	     l.add("a");
	     l.add("");
	     l.add("");
	     l.add(null);
	     l.add(null);
	     System.out.println(l.size());
	     for (int i = 0; i < l.size(); i++) {
			System.out.println(l.get(i));
		}
	     System.out.println("----------------");
	     //set有序，不允许null,允许空字符串,重复元素只会被输出一次
	     Set sa = new HashSet();
	     sa.add("edfa");
	     sa.add("edfa");
	     sa.add("");
	     sa.add("");
	     sa.add(new Integer(10));
	     sa.add(new Integer(10));
	     sa.add(new Integer(11));
	     //sa.add(null);

	     System.out.println(sa.size());
	     for (Object object : sa) {
			System.out.println(object.toString());
		}
	}
}
