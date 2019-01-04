package com.plugin.jbpm.drools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.drools.SystemEventListenerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.process.workitem.wsht.WSHumanTaskHandler;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.mina.MinaTaskClientConnector;
import org.jbpm.task.service.mina.MinaTaskClientHandler;

import com.plugin.jbpm.drools.model.Hour;
import com.plugin.jbpm.utils.JbpmUtil;

public class FPTask {
	
	private static String base = "drools/";
	
	private static SimpleDateFormat format = new SimpleDateFormat("HH"); 
    
    public static final void main(String[] args) {
        String processId = "com.wujf.jbpm.fptask";
        run1(processId);
    }
    
    public static void run1(String processId){
    	
    	HashMap<String, Object> params = new HashMap<String, Object>();
    	params.put("hourOfDay", new Hour(Integer.valueOf(format.format(new Date()))));
        params.put("hostActorLimitDay", "_hostActorLimitDay");
        params.put("hostActor", "3001");
        params.put("deptId", "3");


    	StatefulKnowledgeSession ksession = JbpmUtil.getStatefulKnowledgeSession(base + "fptask.bpmn", base + "fptask.drl");
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", new WSHumanTaskHandler());
		
        SystemEventListenerFactory.setSystemEventListener(new SystemEventListener());
        TaskClient taskClient = new TaskClient(new MinaTaskClientConnector("MinaConnector", new MinaTaskClientHandler(SystemEventListenerFactory.getSystemEventListener())));
        taskClient.connect("127.0.0.1", 9123);
		
    	 
     	ksession.startProcess(processId, params);
        
        ksession.fireAllRules();
        
        ksession.dispose();
    }
    
	private static class SystemEventListener implements org.drools.SystemEventListener {
		public void debug(String arg0) {
		}
		public void debug(String arg0, Object arg1) {
		}
		public void exception(Throwable arg0) {
		}
		public void exception(String arg0, Throwable arg1) {
		}
		public void info(String arg0) {
		}
		public void info(String arg0, Object arg1) {
		}
		public void warning(String arg0) {
		}
		public void warning(String arg0, Object arg1) {
		}
	}
}
