package com.plugin.drools.service.impl;

import java.util.List;
import org.drools.RuleBase;
import org.drools.compiler.PackageBuilder;
import org.drools.spi.AgendaFilter;
import com.plugin.drools.service.RuleService;
import com.plugin.drools.utils.DroolsUtil;
import com.plugin.drools.utils.RuleBaseFacatory;

public class RuleServiceImpl implements RuleService {

	private RuleBase ruleBase;
	
	private String dateformat = "yyyy-MM-dd HH:mm:ss";

	// 初始化
	public void init(List<String> drls) {
		// 设置时间格式
		System.setProperty("drools.dateformat", dateformat);
		ruleBase = RuleBaseFacatory.getRuleBase();

		try {
			PackageBuilder builder = DroolsUtil.getPackageBuilder(drls);
			ruleBase.addPackages(builder.getPackages());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 刷新
	public void refresh(List<String> drls) {
		ruleBase = RuleBaseFacatory.getRuleBase();
		org.drools.rule.Package[] packages = ruleBase.getPackages();
		for (org.drools.rule.Package pg : packages) {
			ruleBase.removePackage(pg.getName());
		}
		init(drls);
	}
	
	// 判断是否允许
	public boolean isAllow(){
		if (ruleBase.getPackages() == null|| ruleBase.getPackages().length == 0) {
			return false;
		}
		return true;
	}

	// 执行
	public void execute(Object o,AgendaFilter filer) {
		if(isAllow()) {
			/*
			 new org.drools.spi.AgendaFilter() {
				public boolean accept(Activation activation) {
					return !activation.getRule().getName().contains("test");
				}
			 }
			 */
			DroolsUtil.runAllRules(ruleBase,o,filer);
		}
	}
	
	// 执行
	public void execute(Object o) {
		if(isAllow()) {
			DroolsUtil.runAllRules(ruleBase,o);
		}
	}
}
