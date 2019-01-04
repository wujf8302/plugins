package com.plugin.drools.spring;

import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.plugin.drools.spring.service.LoginService;
import com.plugin.drools.spring.service.impl.LoginServiceImpl;

public class Test {
	
	public static void main(String[] args) {
		// new String[] {"classpath*:spring/applicationContext*.xml" }
		ClassPathXmlApplicationContext context =  new ClassPathXmlApplicationContext("spring/beans.xml");
		
		drools(context);
	}
	
	public static void drools(ClassPathXmlApplicationContext context){
        LoginService loginService = (LoginServiceImpl) context.getBean("loginService");
    	
        System.out.println("loginService: " + loginService);
        
    	System.out.println("--------------------------1");
    	
    	StatefulKnowledgeSession session =  null;
    	try {
            session = (StatefulKnowledgeSession) context.getBean("ksession1");
            
    		System.out.println("--------------------------2");
		} catch (Exception e) {
		    e.printStackTrace();
		    return;
		}
		loginService.checkLogin(session);
	}
}
