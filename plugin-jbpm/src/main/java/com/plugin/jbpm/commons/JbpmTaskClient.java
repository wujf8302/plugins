package com.plugin.jbpm.commons;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.KnowledgeBase;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.process.audit.JPAProcessInstanceDbLog;
import org.jbpm.process.audit.JPAWorkingMemoryDbLogger;
import org.jbpm.task.service.TaskClient;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.plugin.jbpm.utils.ConstantsUtil;
import com.plugin.jbpm.utils.JbpmUtil;

/**
 * 流程引擎初始化类
 * 
 * @author wujf
 * @version Revision 1.0.0
 */
public class JbpmTaskClient {
    
    private static final Log log = LogFactory.getLog(JbpmTaskClient.class);
    
    //@Autowired
    private JdbcTemplate               jdbcTemplate;            //spring数据库操作类
    
    //@Autowired
    private EntityManagerFactory       jbpmEntityManagerFactory;//jbpm_persistence.xml
    
    //@Autowired
    private PlatformTransactionManager jbpmTransactionManager;  //jbpm_persistence.xml
    
    private String                     serverIpAddr = ConstantsUtil.serverIpAddr;
    private int                        serverPort   = ConstantsUtil.serverPort;
    
    private List<String>               bpmnFiles    = new ArrayList<String>();//流程文件定义集合
    
    private KnowledgeRuntimeLogger     logger;
    private JPAWorkingMemoryDbLogger   dblogger;
    private JPAProcessInstanceDbLog    processLog;
    
    private TaskClient                 client;
    private KnowledgeBase              kbase;
    
    private StatefulKnowledgeSession   ksession     = null;
    
    public JbpmTaskClient() {
        try {
            // 默认地址为本机地址.
            serverIpAddr = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("jbpm server ip地址获取失败.", e);
            serverIpAddr = "127.0.0.1";
        }
    }
    
    /**
     * 用于连接Mina client
     */
    public void connect() {
        if (client == null) {
            try {
                client = JbpmUtil.getTaskClient(serverIpAddr,serverPort);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
    
    public void init() {
        connect();
        if (bpmnFiles.size() <= 0) {
            throw new IllegalArgumentException("Could not init bpmnFlies");
        }
        try {
            ksession = getStatefulKnowledgeSession(bpmnFiles);
            logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession,"test");
            dblogger = new JPAWorkingMemoryDbLogger(ksession);
            
            log.info("jbpm connect successful." + ksession);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public StatefulKnowledgeSession getStatefulKnowledgeSession(List<String> bpmnFiles) throws Exception {
    	return JbpmUtil.getStatefulKnowledgeSession(jbpmEntityManagerFactory,jbpmTransactionManager,jdbcTemplate,client,bpmnFiles); 
    }
    public StatefulKnowledgeSession newKsession() {
        return JbpmUtil.getStatefulKnowledgeSession(jbpmEntityManagerFactory,jbpmTransactionManager,jdbcTemplate,client,kbase);
    }
    
    public void destroy() {
        if (dblogger != null){
            dblogger.dispose();
        }
        if (logger != null){
            logger.close();
        }
        if (processLog != null){
            processLog.dispose();
        }
        try {
            if (ksession != null){
                ksession.dispose();
            }
            if (client != null){
                client.disconnect();
            }
        } catch (Exception e) {
            log.error(null, e);
        }
    }
    
    public StatefulKnowledgeSession getKsession() {
        return ksession;
    }
    
    public void dispose() {
        if (ksession != null){
            ksession.dispose();
        }
    }
    
    public String getServerIpAddr() {
        return serverIpAddr;
    }
    
    public void setServerIpAddr(String serverIpAddr) {
        this.serverIpAddr = serverIpAddr;
    }
    
    public int getServerPort() {
        return serverPort;
    }
    
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
    
    public List<String> getBpmnFiles() {
        return bpmnFiles;
    }
    
    public void setBpmnFiles(List<String> bpmnFiles) {
        this.bpmnFiles = bpmnFiles;
    }
    
    public KnowledgeRuntimeLogger getLogger() {
        return logger;
    }
    
    public void setLogger(KnowledgeRuntimeLogger logger) {
        this.logger = logger;
    }
    
    public JPAWorkingMemoryDbLogger getDblogger() {
        return dblogger;
    }
    
    public void setDblogger(JPAWorkingMemoryDbLogger dblogger) {
        this.dblogger = dblogger;
    }
    
    public JPAProcessInstanceDbLog getProcessLog() {
        return processLog;
    }
    
    public void setProcessLog(JPAProcessInstanceDbLog processLog) {
        this.processLog = processLog;
    }
    
    public TaskClient getClient() {
        return client;
    }
    
    public void setClient(TaskClient client) {
        this.client = client;
    }
    
    //---
    
    public EntityManagerFactory getJbpmEntityManagerFactory() {
        return jbpmEntityManagerFactory;
    }
    
    public void setJbpmEntityManagerFactory(EntityManagerFactory jbpmEntityManagerFactory) {
        this.jbpmEntityManagerFactory = jbpmEntityManagerFactory;
    }
    
    public PlatformTransactionManager getJbpmTransactionManager() {
        return jbpmTransactionManager;
    }
    
    public void setJbpmTransactionManager(PlatformTransactionManager jbpmTransactionManager) {
        this.jbpmTransactionManager = jbpmTransactionManager;
    }

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
