package com.plugin.api.mode.single.two;
/**
 * 单例模式(饿汉)
 */
public class Main {
	public static void main(String[] args) {
		Crow c = CrowFactory.getInstance();
		Crow c1 = CrowFactory.getInstance();
	}
}
