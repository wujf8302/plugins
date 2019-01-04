package com.plugin.jbpm.junit;

import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import com.plugin.jbpm.utils.JbpmUtil;

public class RuleTaskTest extends TestCase {

	private String basepath = "junit/";

	public void testRuleTask() throws Exception {

		System.out.println("Loading process BPMN2-RuleTask.bpmn2");
		
		StatefulKnowledgeSession ksession = JbpmUtil.getStatefulKnowledgeSession(basepath+ "BPMN2-RuleTask.bpmn2", basepath + "BPMN2-RuleTask.drl");
		
		List<String> list = new ArrayList<String>();
		ksession.setGlobal("list", list);
		ProcessInstance processInstance = ksession.startProcess("RuleTask");

		System.out.println(processInstance.getState() + " : " + (processInstance.getState() == ProcessInstance.STATE_ACTIVE));

		ksession.fireAllRules();
		
		int size = list.size();
		
		System.out.println("size: " + size + " - " + (size == 1));
		
		if(size > 0){
			System.out.println(list.get(0));
		}

		System.out.println(processInstance.getId());
		
		System.out.println(ksession.getProcessInstance(processInstance.getId()));
	}
}
