package com.plugin.jbpm.drools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import org.drools.runtime.StatefulKnowledgeSession;

import com.plugin.jbpm.drools.model.Hour;
import com.plugin.jbpm.utils.JbpmUtil;

public class Greetings {
	
	private static String base = "drools/";
	
	private static SimpleDateFormat format = new SimpleDateFormat("HH"); 
    
    public static final void main(String[] args) {
        String processId = "com.wujf.jbpm.greetings";
        
        //run1(processId,new Hour(Integer.valueOf(format.format(new Date()))));
        System.out.println("----------------------------------");
        run2(processId,new Hour(new Random().nextInt(24)));
    }
    
    public static void run1(String processId,Hour hour){
    	
    	org.drools.runtime.process.WorkflowProcessInstance process;
    	/*
    	System.out.println("Id: " + process.getId());
    	System.out.println("ProcessId: " + process.getProcessId());
    	System.out.println("ProcessName: " + process.getProcessName());
    	System.out.println("State: " + process.getState());
    	String[] s = process.getEventTypes();
    	for (int i = 0; i < s.length; i++) {
			System.out.println(s[i]);
		}
			System.out.println(process.getProcess().getName());
    	*/
 
    	System.out.println(hour.toString());
    	
    	HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("hourOfDay", hour);//bpmn ---> drl
    	
    	//$process.getVariable("hourOfDay")
    	//morning=[value >=6 && < 12]   afternoon=[value >= 12 && < 20]  night=[value >= 20 || < 6]
    	//$process.setVariable("period", "morning");
    	
    	 StatefulKnowledgeSession ksession = JbpmUtil.getStatefulKnowledgeSession(base + "greetings.bpmn", base + "greetings.drl");
         //KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);
    	 
    	 /*
        org.drools.runtime.process.WorkflowProcessInstance process = (org.drools.runtime.process.WorkflowProcessInstance)kcontext.getProcessInstance();
        System.out.print("It is " + process.getVariable("hourOfDay") + " and i wish you a ");
        kcontext.getKnowledgeRuntime().insert(process);

        Define Period

        org.drools.runtime.process.WorkflowProcessInstance process = (org.drools.runtime.process.WorkflowProcessInstance)kcontext.getProcessInstance();     
        return "morning".equals(process.getVariable("period"));

        org.drools.runtime.process.WorkflowProcessInstance process = (org.drools.runtime.process.WorkflowProcessInstance)kcontext.getProcessInstance();     
        return "afternoon".equals(process.getVariable("period"));


        org.drools.runtime.process.WorkflowProcessInstance process = (org.drools.runtime.process.WorkflowProcessInstance)kcontext.getProcessInstance();     
        return "night".equals(process.getVariable("period"));
        */
    	 
     	ksession.startProcess(processId, params);
    	// ProcessInstance processInst = jbpm5Configuration.getKsession().startProcess(processDefId, parameters);
        
        ksession.fireAllRules();
        // logger.close();
        ksession.dispose();
    }
    
    public static void run2(String processId,Hour hour){
    	
    	System.out.println(hour.toString());
    	
    	HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("hourOfDay", hour);

    	StatefulKnowledgeSession ksession = JbpmUtil.getStatefulKnowledgeSession(new String[]{base + "greetings.bpmn"}, new String[]{base + "fenkai/greetings1.drl",base + "fenkai/greetings2.drl",base + "fenkai/greetings3.drl"});
        //KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);
    	 
    	ksession.startProcess(processId, params);
    	// ProcessInstance processInst = jbpm5Configuration.getKsession().startProcess(processDefId, parameters);
        
        ksession.fireAllRules();
        // logger.close();
        ksession.dispose();
    }
}
