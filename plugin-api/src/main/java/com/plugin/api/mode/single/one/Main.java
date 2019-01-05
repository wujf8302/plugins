package com.plugin.api.mode.single.one;
/**
 * 单例模式(懒汉).
 * @author wujf
 */
public class Main {
	public static void main(String[] args) {
		Crow c = CrowFactory.getInstance();
		Crow c1 = CrowFactory.getInstance();
	}
};
