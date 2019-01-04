package com.plugin.jbpm.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.SystemEventListenerFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.compiler.BPMN2ProcessFactory;
import org.drools.compiler.ProcessBuilderFactory;
import org.drools.io.ResourceFactory;
import org.drools.io.impl.ClassPathResource;
import org.drools.marshalling.impl.ProcessMarshallerFactory;
import org.drools.persistence.jpa.JPAKnowledgeService;
import org.drools.runtime.Environment;
import org.drools.runtime.EnvironmentName;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;
import org.drools.runtime.process.ProcessRuntimeFactory;
import org.jbpm.bpmn2.BPMN2ProcessProviderImpl;
import org.jbpm.marshalling.impl.ProcessMarshallerFactoryServiceImpl;
import org.jbpm.process.builder.ProcessBuilderFactoryServiceImpl;
import org.jbpm.process.instance.ProcessRuntimeFactoryServiceImpl;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.mina.MinaTaskClientConnector;
import org.jbpm.task.service.mina.MinaTaskClientHandler;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import com.plugin.jbpm.commons.HumanTaskSupport;
/**
 * Jbpm工具类.
 * @author wujf
 */
public class JbpmUtil {
    
    private static final Log log  = LogFactory.getLog(JbpmUtil.class);
    
    private static String JBPM_SESSION_INFO_TBL = "SESSIONINFO";//JBPM_SESSION_INFO_TBL
    
    //------------------------------------------------------------------------------
    //自行加载规则文件
	public static void execute(String drl, Object o) {
		StatelessKnowledgeSession ksession = JbpmUtil.getStatelessKnowledgeSession(null, drl);
		ksession.execute(o);
	}
	
	//自行加载规则文件
	public static void fireAllRules(String bpmn,String drl,String processId,Map<String, Object> parameters) {
	    StatefulKnowledgeSession ksession = JbpmUtil.getStatefulKnowledgeSession(bpmn, drl);
	    //KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);
		ksession.startProcess(processId, parameters);
	    ksession.fireAllRules();
	    //logger.close();
	    ksession.dispose();
	}
	
	public static void fireAllRules(String[] bpmns,String[] drls,String processId,Map<String, Object> parameters) {
	    StatefulKnowledgeSession ksession = JbpmUtil.getStatefulKnowledgeSession(bpmns, drls);
	    //KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);
		ksession.startProcess(processId, parameters);
	    ksession.fireAllRules();
	    //logger.close();
	    ksession.dispose();
	}
	
	public static void fireAllRules(List<String> bpmns,List<String> drls,String processId,Map<String, Object> parameters) {
	    StatefulKnowledgeSession ksession = JbpmUtil.getStatefulKnowledgeSession(bpmns, drls);
	    //KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newConsoleLogger(ksession);
		ksession.startProcess(processId, parameters);
	    ksession.fireAllRules();
	    //logger.close();
	    ksession.dispose();
	}
	
	//------------------------------------------------------------------------------
    
    public static StatefulKnowledgeSession getStatefulKnowledgeSession(){
        return getStatefulKnowledgeSession("","");
    }
    
    public static StatefulKnowledgeSession getStatefulKnowledgeSession(String bpmn){
        return getStatefulKnowledgeSession(bpmn,null);
    }
    
    public static StatefulKnowledgeSession getStatefulKnowledgeSession(String bpmn,String drl){
        KnowledgeBase kbase = getKnowledgeBase(bpmn,drl);
        return kbase.newStatefulKnowledgeSession();
    }
    
    public static StatefulKnowledgeSession getStatefulKnowledgeSession(String[] bpmns,String[] drls){
        KnowledgeBase kbase = getKnowledgeBase(bpmns,drls);
        return kbase.newStatefulKnowledgeSession();
    }
    
    public static StatefulKnowledgeSession getStatefulKnowledgeSession(List<String> bpmns,List<String> drls){
        KnowledgeBase kbase = getKnowledgeBase(bpmns,drls);
        return kbase.newStatefulKnowledgeSession();
    }
    
    public static StatefulKnowledgeSession getStatefulKnowledgeSession(String[] bpmns){
        KnowledgeBase kbase = getKnowledgeBase(bpmns,null);
        return kbase.newStatefulKnowledgeSession();
    }
    
    /**
     * 持久化jbpm5相关对象，返回流程session.
     */
    public static StatefulKnowledgeSession getStatefulKnowledgeSession(EntityManagerFactory jbpmEntityManagerFactory,
        PlatformTransactionManager jbpmTransactionManager,JdbcTemplate jdbcTemplate,TaskClient taskClient,List<String> bpmnFiles) {
    	if(bpmnFiles != null){
    		for (int i = 0; i < bpmnFiles.size(); i++) {
    			log.info((i+1) + " - " + bpmnFiles.get(i));
			}
    	}
        return JbpmUtil.getStatefulKnowledgeSession(jbpmEntityManagerFactory,jbpmTransactionManager,jdbcTemplate,taskClient,getKnowledgeBase(bpmnFiles));
    }
    
    public static StatefulKnowledgeSession getStatefulKnowledgeSession(EntityManagerFactory jbpmEntityManagerFactory,
        PlatformTransactionManager jbpmTransactionManager,JdbcTemplate jdbcTemplate,TaskClient taskClient,KnowledgeBase kbase) {
        
        Environment env = KnowledgeBaseFactory.newEnvironment();
        env.set(EnvironmentName.ENTITY_MANAGER_FACTORY,jbpmEntityManagerFactory);
        env.set(EnvironmentName.TRANSACTION_MANAGER, jbpmTransactionManager);
        
        StatefulKnowledgeSession ksession = null;
        try {
            int sessionId = jdbcTemplate.queryForInt("SELECT MAX(si.id) FROM " + JBPM_SESSION_INFO_TBL + " si");
            
            log.info("sessionId: " + sessionId);
            
            if (sessionId != 0) {
                ksession = JPAKnowledgeService.loadStatefulKnowledgeSession(sessionId, kbase, null, env);
            } else {
                ksession = JPAKnowledgeService.newStatefulKnowledgeSession(kbase, null, env);
            }
            HumanTaskSupport workItemHandler = new HumanTaskSupport(ksession);
            workItemHandler.setClient(taskClient);
            workItemHandler.connect();
            ksession.getWorkItemManager().registerWorkItemHandler("Human Task",workItemHandler);
            
            log.info("ksession:" + ksession);
            
        } catch (Exception e) {
            log.error("create KnowledgeSession errors!", e);
        }
        return ksession;
    }
    
    //------------------------------------------------------------------------------
    
    public static StatelessKnowledgeSession getStatelessKnowledgeSession(){
        return getStatelessKnowledgeSession("","");
    }
    
    public static StatelessKnowledgeSession getStatelessKnowledgeSession(String bpmn,String drl){
        KnowledgeBase kbase = getKnowledgeBase(bpmn,drl);
        return kbase.newStatelessKnowledgeSession();
    }
    
    public static StatelessKnowledgeSession getStatelessKnowledgeSession(String[] bpmns,String[] drls){
        KnowledgeBase kbase = getKnowledgeBase(bpmns,drls);
        return kbase.newStatelessKnowledgeSession();
    }
    
    public static StatelessKnowledgeSession getStatelessKnowledgeSession(List<String> bpmns,List<String> drls){
        KnowledgeBase kbase = getKnowledgeBase(bpmns,drls);
        return kbase.newStatelessKnowledgeSession();
    }
    
    //------------------------------------------------------------------------------
    
    /**
     * 初始化
     */
    public static void init(){
        ProcessBuilderFactory.setProcessBuilderFactoryService(new ProcessBuilderFactoryServiceImpl());
        ProcessMarshallerFactory.setProcessMarshallerFactoryService(new ProcessMarshallerFactoryServiceImpl());
        ProcessRuntimeFactory.setProcessRuntimeFactoryService(new ProcessRuntimeFactoryServiceImpl());
        BPMN2ProcessFactory.setBPMN2ProcessProvider(new BPMN2ProcessProviderImpl());
    }
    
   //------------------------------------------------------------------------------
    
    public static KnowledgeBase getKnowledgeBase(String jbpm){
        return getKnowledgeBase(jbpm,null);
    }
    
    public static KnowledgeBase getKnowledgeBase(String jbpm,String drl){
        String[] jbpms = null;
        if(jbpm != null && !"".equals(jbpm)){
            jbpms = new String[]{jbpm};
        }
        String[] drls = null;
        if(drl != null && !"".equals(drl)){
            drls = new String[]{drl};
        }
        return getKnowledgeBase(jbpms,drls);
    }
    
    public static KnowledgeBase getKnowledgeBase(List<String> files){
        init();
        List<String> bpmns = null;
        List<String> drls = null;
        if (files != null) {
        	bpmns = new ArrayList<String>();
        	drls = new ArrayList<String>();
            for (String file : files) {
            	if(file != null && !"".equals(file)){
            		if(file.indexOf(",") != -1){
            			String[] temps = file.split(",");
            		    if(temps != null && temps.length > 0){
            		    	for (int i = 0; i < temps.length; i++) {
            		    		addBpmnOrDrl(temps[i],bpmns,drls);
							}
            		    } 
            		}else{
            			addBpmnOrDrl(file,bpmns,drls);
            		}
            	}
            }
        }
        return getKnowledgeBase(bpmns,drls);
    }
    
    public static KnowledgeBase getKnowledgeBase(List<String> bpmns,List<String> drls){
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        if(bpmns != null){
            for (int i = 0; i < bpmns.size(); i++) {
            	String bpmn = (String)bpmns.get(i);
            	if(bpmn != null && !"".equals(bpmn.trim())){
            		 kbuilder.add(ResourceFactory.newClassPathResource(bpmn), ResourceType.BPMN2);
            	}
            }
        }
        if(drls != null){
            for (int i = 0; i < drls.size(); i++) {
            	String drl = (String)drls.get(i);
            	if(drl != null && !"".equals(drl.trim())){
            		kbuilder.add(ResourceFactory.newClassPathResource(drl),ResourceType.DRL);
            	}
            }
        }
       
        return kbuilder.newKnowledgeBase();
    }
    
    public static KnowledgeBase getKnowledgeBase(String[] bpmns,String[] drls){
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        if(bpmns != null){
            for (int i = 0; i < bpmns.length; i++) {
            	String bpmn = bpmns[i];
            	if(bpmn != null && !"".equals(bpmn.trim())){
            		 kbuilder.add(ResourceFactory.newClassPathResource(bpmn), ResourceType.BPMN2);
            	}
            }
        }
        if(drls != null){
            for (int i = 0; i < drls.length; i++) {
            	String drl = drls[i];
            	if(drl != null && !"".equals(drl.trim())){
            		kbuilder.add(ResourceFactory.newClassPathResource(drl),ResourceType.DRL);
            	}
                
            }
        }
       
        return kbuilder.newKnowledgeBase();
    }
    
    public static KnowledgeBase getKnowledgeBase(){
        return KnowledgeBaseFactory.newKnowledgeBase();
    }
    
    //------------------------------------------------------------------------------
    
    public static KnowledgeBuilder getKnowledgeBuilder(String jbpm){
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(new ClassPathResource(jbpm),ResourceType.BPMN2);
        return kbuilder;
    }
    
    public static KnowledgeBuilder getKnowledgeBuilder(String jbpm,String drl){
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        //kbuilder.add(new ClassPathResource(jbpm),ResourceType.BPMN2);
        //kbuilder.add(new ClassPathResource(drl),ResourceType.DRL);
        
        kbuilder.add(ResourceFactory.newClassPathResource(jbpm),ResourceType.BPMN2);
        kbuilder.add(ResourceFactory.newClassPathResource(drl),ResourceType.DRL);
        
        return kbuilder;
    }
    
    //------------------------------------------------------------------------------
    
    private static void addBpmnOrDrl(String file,List<String> bpmns,List<String> drls){
    	if(file != null && file.length() > 0){
    		String suffix = file.substring(file.indexOf("."),file.length());
    		suffix = suffix.toLowerCase();
    		if(suffix.endsWith(".bpmn") || suffix.endsWith(".bpmn2")){
    			 bpmns.add(file);
    		}else if(suffix.endsWith(".drl")){
    			 drls.add(file);
    		}
    	}
    }

    public static TaskClient getTaskClient(String serverIpAddr,int serverPort)throws IllegalStateException{
        TaskClient taskClient = new TaskClient(new MinaTaskClientConnector("org.drools.process.workitem.wsht.WSHumanTaskHandler",
            new MinaTaskClientHandler(SystemEventListenerFactory.getSystemEventListener())));
        boolean connected = taskClient.connect(serverIpAddr, serverPort);
        if (!connected) {
            throw new IllegalStateException("Could not connect task client!");
        }
        return taskClient;
    }
    
    //------------------------------------------------------------------------------
}
