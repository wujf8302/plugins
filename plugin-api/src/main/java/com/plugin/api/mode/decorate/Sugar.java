package com.plugin.api.mode.decorate;
/**
 * 
 * @author wujf
 */
public class Sugar implements Product {

	private Product p; // 组合

	public Sugar(Product p) {
		this.p = p;
	}

	public int money() {
		return p.money() + 5;
	}

}
