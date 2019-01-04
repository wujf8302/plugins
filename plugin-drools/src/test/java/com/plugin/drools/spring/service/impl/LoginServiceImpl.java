package com.plugin.drools.spring.service.impl;

import org.drools.runtime.StatefulKnowledgeSession;

import com.plugin.drools.spring.model.Vip;
import com.plugin.drools.spring.service.LoginService;
import com.plugin.drools.utils.DroolsUtil;

public class LoginServiceImpl implements LoginService{

	private Vip vip;
	
	public void checkLogin(StatefulKnowledgeSession session) {
		System.out.println("session: " + session);
		if (session != null) {
			DroolsUtil.runAllRules(session, vip);
		}
	}
	
	public Vip getVip() {
		return vip;
	}

	public void setVip(Vip vip) {
		this.vip = vip;
	}

}
