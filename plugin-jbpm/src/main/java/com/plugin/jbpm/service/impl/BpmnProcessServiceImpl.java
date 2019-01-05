package com.plugin.jbpm.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.drools.definition.process.Process;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.task.AccessType;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.TaskClient;
import org.jbpm.task.service.TaskClientHandler.TaskOperationResponseHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskOperationResponseHandler;
import org.jbpm.task.service.responsehandlers.BlockingTaskSummaryResponseHandler;
import org.springframework.util.Assert;

import com.plugin.jbpm.commons.JbpmTaskClient;
import com.plugin.jbpm.service.BpmnProcessService;
import com.plugin.jbpm.utils.JbpmUtil;

/**
 * jbpm5流程实现.
 * @author wujf
 */
public class BpmnProcessServiceImpl implements BpmnProcessService {
    
    private static final Log   log = LogFactory.getLog(BpmnProcessServiceImpl.class);
    
    private JbpmTaskClient jbpmTaskClient;
    
    /******************** spring rmi ************************/
    public String rmi(String s){
    	s = "server: 【" + s + "】";
    	log.info(s);
    	return s;
    }
    
    /******************** 公用方法 ************************/
    
    public JbpmTaskClient getJbpmTaskClient() {
        return jbpmTaskClient;
    }
    
    public void setJbpmTaskClient(JbpmTaskClient jbpmTaskClient) {
        this.jbpmTaskClient = jbpmTaskClient;
    }
    
    public StatefulKnowledgeSession getSession(){
    	return jbpmTaskClient.getKsession();
    }
    
    public TaskClient getClient(){
    	return jbpmTaskClient.getClient();
    }
    
    /************** 规则方面(自行加载规则文件) ***************/
    
    public void execute(String drl,Object object) {
		JbpmUtil.execute(drl, object);
	}
    
	public void fireAllRules(String bpmn,String drl,String processId,Map<String, Object> parameters) {
		JbpmUtil.fireAllRules(bpmn,drl, processId,parameters);
	}
	
	public void fireAllRules(String[] bpmns,String[] drls,String processId,Map<String, Object> parameters) {
	    JbpmUtil.fireAllRules(bpmns,drls,processId,parameters);
	}
	
	public void fireAllRules(List<String> bpmns,List<String> drls,String processId,Map<String, Object> parameters) {
	    JbpmUtil.fireAllRules(bpmns,drls,processId,parameters);
	}
    
    /******************** 派单方面 ************************/
    
    /**
     * 发起一个流程实例，    并返回实例ID.
     * @param processId  流程定义ID
     * @param parameters 流程相关数据
     */
    public Long startProcessInstance(String processId,Map<String, Object> parameters) {
        ProcessInstance processInst = getSession().startProcess(processId, parameters);
        // jbpmTaskClient.dispose();
        return processInst.getId();
    }
    
    /**
     * 获取任务.
     * @param taskId   任务ID
     * @param userId   用户ID
     * @param deptIds  部门信息. 
     */
    public void claim(Long taskId, String userId, List<String> groupIds) {
        TaskOperationResponseHandler handler = new BlockingTaskOperationResponseHandler();
        getClient().claim(taskId, userId, groupIds, handler);
    }
    
    /**
     * 完成任务.
     * @param taskId  任务ID
     * @param data    流程相关数据
     * @param userId  用户账号
     */
    public void completeTask(Long taskId, String userId, Map<String, Object> data) {
    	
        log.info("begin completeTask :" + taskId);
        
        BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();
        getClient().start(taskId, userId, responseHandler);
        
        responseHandler.waitTillDone(5000);
        responseHandler = new BlockingTaskOperationResponseHandler();
        ContentData contentData = null;
        if (data != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out;
            try {
                out = new ObjectOutputStream(bos);
                out.writeObject(data);
                out.close();
                contentData = new ContentData();
                contentData.setContent(bos.toByteArray());
                contentData.setAccessType(AccessType.Inline);
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
        
        getClient().complete(taskId, userId, contentData,responseHandler);
        responseHandler.waitTillDone(5000);
    }
    
    /**
     * 改派任务到部门. 
     * @param taskId  任务ID
     * @param userId  办理人ID
     * @param deptId  部门ID
     */
    public void reassignTaskToDept(Long taskId, String userId, String deptId) {
    	
        Assert.notNull(taskId, "task id must not null.");
        Assert.notNull(userId, "user id must not null.");
        Assert.notNull(deptId, "dept id must not null.");
        
        BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();
        getClient().delegate(taskId, userId, userId,responseHandler);
        getClient().forward(taskId, userId, deptId,responseHandler);
    }
    
    /**
     * 改派任务到个人. 
     * @param taskId 任务ID
     * @param userId 任务原始处理人
     * @param targetUserId  任务改派处理人
     */
    public void reassignTask(Long taskId, String userId, String targetUserId) {
        Assert.notNull(taskId, "task id must not null.");
        Assert.notNull(userId, "user id must not null.");
        Assert.notNull(targetUserId, "dept id must not null.");
        
        BlockingTaskOperationResponseHandler responseHandler = new BlockingTaskOperationResponseHandler();
        getClient().delegate(taskId, userId, targetUserId,responseHandler);
        responseHandler.waitTillDone(5000);
    }
    
    /*********************** 任务方面 **********************/
    
    /**
     * 获取某用户待处理任务.
     * @param userAccount 用户账号
     */
    public List<TaskSummary> getAssignedTasks(String userAccount) {
        List<TaskSummary> tasks = null;
        try {
            BlockingTaskSummaryResponseHandler responseHandler = new BlockingTaskSummaryResponseHandler();
            getClient().getTasksAssignedAsPotentialOwner(userAccount, "en-UK", responseHandler);
            tasks = responseHandler.getResults();
        } catch (Exception e) {
            log.error(e);
        }
        return tasks;
    }
    
    /*********************** 流程方面 **********************/
    
    /**
     * 返回引擎加载的所有流程定义.
     */
    public List<Process> getProcesss() {
        Collection<Process> processes = getSession().getKnowledgeBase().getProcesses();
        if (processes == null || processes.size() == 0)
            return null;
        return new ArrayList<Process>(processes);
    }
    
    /**
     * 查看当前ksession加载的某个流程定义 .
     * @param processId  流程定义ID
     */
    public Process getProcess(String processId) {
        Process process = null;
        try {
            process = getSession().getKnowledgeBase().getProcess(processId);
        } catch (Exception e) {
            log.error(e);
        }
        return process;
    }
    
    /**
     * 返回当前ksession中存在的某个流程实例.
     * @param processInstId 流程实例ID
     */
    public ProcessInstance getProcessInstInKseesion(Long processInstId) {
        ProcessInstance processInst = null;
        try {
            processInst = getSession().getProcessInstance(processInstId);
        } catch (Exception e) {
            log.error(e);
        }
        return processInst;
    }
    
    /**
     * 获取流程实例状态.
     */
    public int getProcessInstanceState(Long processInstId) {
    	ProcessInstance processInst = getProcessInstInKseesion(processInstId);
        return  processInst.getState();
    }
    
    /**
     * 返回当前ksession中存在的所有流程实例.
     */
    public Collection<ProcessInstance> getProcessInstsInKseesion() {
        Collection<ProcessInstance> processInsts = null;
        try {
            processInsts = getSession().getProcessInstances();
        } catch (Exception e) {
            log.error(e);
        }
        return processInsts;
    }
}
