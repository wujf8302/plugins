package com.plugin.jbpm.commons;

import javax.persistence.EntityManagerFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.SystemEventListenerFactory;
import org.jbpm.task.service.TaskServer;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.mina.MinaTaskServer;

import com.plugin.jbpm.service.HumanTaskEventService;
import com.plugin.jbpm.utils.ConstantsUtil;

/**
 * jbpm服务端.
 * @author wujf
 */
public class JbpmTaskServer {
    
    private static final Log       log = LogFactory.getLog(JbpmTaskServer.class);
    
    private String  serverIpAddr = ConstantsUtil.serverIpAddr;
    private int     serverPort   = ConstantsUtil.serverPort;
    
    private TaskServer             taskServer;
    private TaskService            taskService;
    
    //@Autowired
    private EntityManagerFactory   entityManagerFactory;  //jbpm_persistence.xml
    
    //@Autowired
    private HumanTaskEventService humanTaskEventService;  //人工任务注册器

	public void init() {
        try {
            log.info("JBPM Server Starting...");
            
            //JbpmTaskService类继承TaskService类并重写了TaskService类中的方法
            taskService = new JbpmTaskService(entityManagerFactory,SystemEventListenerFactory.getSystemEventListener(),humanTaskEventService);
            taskServer = new MinaTaskServer(taskService, serverPort, serverIpAddr);
            taskServer.start();
            
            log.info("JBPM Server start successful...");
        } catch (Exception ex) {
            log.error("JBPM Server start failed...", ex);
        }
    }
    
    public void stop() {
        try {
            log.info("stoping jbpm task server...");
            if(taskServer != null) {
                taskServer.stop();
            }
            log.info("stop jbpm task server successful...");
        } catch (Exception e) {
            log.error("stop jbpm task server failed. ", e);
        } finally {
            try {
                taskServer.stop();
            } catch (Exception e) {
                log.error(e);
            }
        }
    }
    
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
    
    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    
    public TaskService getTaskService() {
        return taskService;
    }
    
    public TaskServer getTaskServer() {
        return taskServer;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
    
    public HumanTaskEventService getHumanTaskEventService() {
		return humanTaskEventService;
	}

	public void setHumanTaskEventService(HumanTaskEventService humanTaskEventService) {
		this.humanTaskEventService = humanTaskEventService;
	}
}
