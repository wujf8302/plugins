package com.plugin.jbpm.commons;

import javax.persistence.EntityManagerFactory;
import org.drools.SystemEventListener;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.TaskServiceSession;
import com.plugin.jbpm.service.HumanTaskEventService;

public class JbpmTaskService extends TaskService {

    protected EntityManagerFactory emf;
    protected HumanTaskEventService humanTaskEventService;
    
    public JbpmTaskService(EntityManagerFactory emf,SystemEventListener systemEventListener,HumanTaskEventService humanTaskEventRegistry) {
        super(emf, systemEventListener);
        this.emf = emf;
        this.humanTaskEventService = humanTaskEventRegistry;
    }
    
    /**
     * 重写createSession方法.
     */
    public TaskServiceSession createSession() {
        JbpmTaskSession taskServiceSession = new JbpmTaskSession(this, emf.createEntityManager());
        taskServiceSession.setHumanTaskService(humanTaskEventService);
        return taskServiceSession;
    }
}
