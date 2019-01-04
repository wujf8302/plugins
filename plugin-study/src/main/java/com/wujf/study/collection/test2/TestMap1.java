package com.wujf.study.collection.test2;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试map; 需掌握的方法: put, get, remove ;
 * @author fj.
 */
public class TestMap1 {

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
		map.put(null, null);// 可加null值.

		map.put("c", "789");// 修改 键为"c" 的 值

		map.remove("e");// 删除map中键为"e"的元素

		Object obj = map.get("a");// 通过键 "a" 取出对应的值
		System.out.println(obj);

	}

}
