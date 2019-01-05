package com.plugin.jbpm.service.impl;

import javax.persistence.EntityManagerFactory;
import org.drools.SystemEventListener;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.TaskServiceSession;

import com.plugin.jbpm.service.HumanTaskEventRegistry;
import com.plugin.jbpm.utils.JbpmTaskServiceSession;
/**
 * 
 * @author wujf
 */
public class JbpmTaskService extends TaskService {

    protected EntityManagerFactory emf;
    protected HumanTaskEventRegistry humanTaskEventRegistry;
    
    public JbpmTaskService(EntityManagerFactory emf,SystemEventListener systemEventListener,HumanTaskEventRegistry humanTaskEventRegistry) {
        super(emf, systemEventListener);
        this.emf = emf;
        this.humanTaskEventRegistry = humanTaskEventRegistry;
        
    }
    
    public TaskServiceSession createSession() {
        JbpmTaskServiceSession taskServiceSession = new JbpmTaskServiceSession(this, emf.createEntityManager());
        taskServiceSession.setHumanTaskEventRegistry(humanTaskEventRegistry);
        return taskServiceSession;
    }
}
