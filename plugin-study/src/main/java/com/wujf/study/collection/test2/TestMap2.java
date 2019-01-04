package com.wujf.study.collection.test2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 遍历map; (keySet, entrySet) ( Map.entry : getKey(),getValue() )
 * 
 * @author fj.
 * 
 */
public class TestMap2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Map map = new HashMap();

		map.put("a", "小张");// 添入 键-值 对
		map.put("b", "小王");
		map.put("c", "小李");
		map.put("d", "小叶");
		map.put("e", "小忠");

		// keySet()
		Set set1 = map.keySet();
		Iterator it = set1.iterator();
		while (it.hasNext()) {
			Object key = it.next();
			System.out.println(key + "=" + map.get(key));
		}

		System.out.println();
		System.out.println();

		// entrySet() 实体
		Set set2 = map.entrySet();
		Iterator it2 = set2.iterator();
		while (it2.hasNext()) {

			Object tobj = it2.next();

			Map.Entry me = (Map.Entry) tobj;

			System.out.println(me.getKey() + "=" + me.getValue());

		}

	}

}

// public class Map.Entry{
// private Object key;
// private Object value;
// }

