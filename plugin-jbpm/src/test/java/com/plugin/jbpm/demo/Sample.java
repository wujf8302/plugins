package com.plugin.jbpm.demo;

import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import com.plugin.jbpm.utils.JbpmUtil;

public class Sample {

	public static final void main(String[] args) {
		try {

			StatefulKnowledgeSession ksession = JbpmUtil
					.getStatefulKnowledgeSession("demo/sample.bpmn");
			KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory
					.newFileLogger(ksession, "test");

			ksession.startProcess("com.wujf.jbpm.hello");
			logger.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}