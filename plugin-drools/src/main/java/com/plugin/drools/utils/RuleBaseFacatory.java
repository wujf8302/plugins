package com.plugin.drools.utils;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;

public class RuleBaseFacatory {

	private static RuleBase ruleBase;

	public static RuleBase getRuleBase() {
		if (ruleBase == null) {
			synchronized (Singleton.class) {
				if (ruleBase == null) {
					ruleBase = RuleBaseFactory.newRuleBase();
				}
			}
		}
		return ruleBase;
	}
	
	public class Singleton {

	}
}
