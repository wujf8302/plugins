package com.plugin.drools.spring.service;

import org.drools.runtime.StatefulKnowledgeSession;

public interface LoginService {
	
	public void checkLogin(StatefulKnowledgeSession session);
}
