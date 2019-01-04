package com.plugin.api.mode.decorate;

public class Milk implements Product {

	private Product p;

	public Milk(Product p) {
		this.p = p;
	}

	public int money() {
		return p.money() + 10;
	}

}
