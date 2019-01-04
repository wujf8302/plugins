package com.plugin.api.mode.stencil;

public abstract class Template {
	
	public void fitment() {
		decorate();
		floor();
		paint();
	}

	public abstract void floor();

	public abstract void paint();

	public abstract void decorate();
};
