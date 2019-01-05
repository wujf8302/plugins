package com.plugin.jbpm.service.impl;

import javax.persistence.EntityManagerFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.SystemEventListenerFactory;
import org.jbpm.task.service.TaskServer;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.mina.MinaTaskServer;

import com.plugin.jbpm.service.HumanTaskEventRegistry;
import com.plugin.jbpm.utils.ConstantsUtil;
/**
 * 
 * @author wujf
 */
public class JbpmTaskServer {
    
    private static final Log       log = LogFactory.getLog(JbpmTaskServer.class);
    
    private String  serverIpAddr = ConstantsUtil.serverIpAddr;
    private int     serverPort   = ConstantsUtil.serverPort;
    private TaskServer             taskServer;
    private TaskService            taskService;
    private EntityManagerFactory   entityManagerFactory;
    private HumanTaskEventRegistry humanTaskEventRegistry;
    
    public void init() {
        try {
            log.info("JBPM Server Starting...");
            
            taskService = new JbpmTaskService(entityManagerFactory,SystemEventListenerFactory.getSystemEventListener(),humanTaskEventRegistry);
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
    
    public void setHumanTaskEventRegistry(HumanTaskEventRegistry humanTaskEventRegistry) {
        this.humanTaskEventRegistry = humanTaskEventRegistry;
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
}
