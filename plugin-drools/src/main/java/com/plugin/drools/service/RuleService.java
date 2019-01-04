package com.plugin.drools.service;

import java.util.List;

public interface RuleService {
	
	// 初始化规则
	public void init(List<String> drls);

	// 刷新规则
	public void refresh(List<String> drls);
	
	// 判断是否允许
	public boolean isAllow();

	//执行规则
	public void execute(Object o);
	
}
