package com.plugin.api;

import org.apache.commons.lang.StringUtils;

/**
 * 顺序字符串
 * @author wujf
 */
public class OrderUtil {
	
	public int index = 0;

	public String getOrder(int len) {
		String v = String.valueOf(index++);
		v = StringUtils.leftPad(v, len, '0');
		return v;
	}
}
