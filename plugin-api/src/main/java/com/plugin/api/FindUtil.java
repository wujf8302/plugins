package com.plugin.api;

import java.util.List;

/**
 * 判断集合中是否含有关键字.
 * 
 * @author wujf
 */
public class FindUtil {
	
	public static boolean find(String[] keys, String key) {
		if (keys != null && keys.length > 0 && key != null && !"".equals(key.trim())) {
			for (int i = 0; i < keys.length; i++) {
				String temp = keys[i];
				if (temp != null && !"".equals(temp.trim())) {
					if (temp.equals(key.trim())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean find(List<String> keys, String key) {
		if (keys != null && keys.size() > 0 && key != null && !"".equals(key.trim())) {
			for (int i = 0; i < keys.size(); i++) {
				String temp = keys.get(i);
				if (temp != null && !"".equals(temp.trim())) {
					if (temp.equals(key.trim())) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
