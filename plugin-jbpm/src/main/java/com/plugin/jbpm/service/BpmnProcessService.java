package com.plugin.jbpm.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.drools.definition.process.Process;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.task.service.TaskClient;

import com.plugin.jbpm.commons.JbpmTaskClient;

/**
 * BPMN流程接口(和具体的流程实现无关).
 * @version Revision 1.0.0
 * ProcessRuntime接口定义了所有的会议方法与流程交互
 *  ProcessInstance startProcess(String processId);
 *  ProcessInstance startProcess(String processId, Map<String, Object> parameters);
 *  void signalEvent(String type,Object event);
 *  void signalEvent(String type,Object event,long processInstanceId);
 *  Collection<ProcessInstance> getProcessInstances();
 *  ProcessInstance getProcessInstance(long processInstanceId);
 *  void abortProcessInstance(long processInstanceId);
 *  WorkItemManager getWorkItemManager();
 *  
 *  使用ProcessEventListener注册自己的监听器
 *  void beforeProcessStarted( ProcessStartedEvent event );
 *  void afterProcessStarted( ProcessStartedEvent event );
 *  void beforeProcessCompleted( ProcessCompletedEvent event );
 *  void afterProcessCompleted( ProcessCompletedEvent event );
 *  void beforeNodeTriggered( ProcessNodeTriggeredEvent event );
 *  void afterNodeTriggered( ProcessNodeTriggeredEvent event );
 *  void beforeNodeLeft( ProcessNodeLeftEvent event );
 *  void afterNodeLeft( ProcessNodeLeftEvent event );
 *  void beforeVariableChanged(ProcessVariableChangedEvent event);
 *  void afterVariableChanged(ProcessVariableChangedEvent event);
 */
public interface BpmnProcessService {
	
	/******************** spring rmi ************************/
	public String rmi(String s);
	
    /******************** 公用方法 ************************/
	
	public JbpmTaskClient getJbpmTaskClient();
	
	public StatefulKnowledgeSession getSession();
	
	public TaskClient getClient();
	
    /******************** 规则方面 ************************/
    
	 public void execute(String drl,Object object);
	 
     public void fireAllRules(String bpmn,String drl,String processId,Map<String, Object> parameters);
		
     public void fireAllRules(String[] bpmns,String[] drls,String processId,Map<String, Object> parameters);

	 public void fireAllRules(List<String> bpmns,List<String> drls,String processId,Map<String, Object> parameters);
	
	/******************** 派单方面 ************************/
    
    /**
     * 发起一个流程实例，并返回实例ID.
     * @param processId 流程定义ID
     * @param parameters 流程相关数据
     */
    public Long startProcessInstance(String processId,Map<String, Object> parameters);
    
    /**
     * 获取任务(流程流转).
     * @param taskId 任务ID
     * @param userId用户ID
     * @param deptIds 部门信息.
     */
    public void claim(Long taskId, String userId, List<String> deptIds);
    
    /**
     * 完成任务.
     * @param taskId 任务ID
     * @param data  流程相关数据
     * @param userId 用户账号
     * @throws InterruptedException
     */
    public void completeTask(Long taskId, String userId, Map<String, Object> data);
    
    /**
     * 改派任务到部门.
     * @param taskId 任务ID
     * @param userId 办理人ID
     * @param deptId 部门ID
     * @return
     */
    public void reassignTaskToDept(Long taskId, String userId, String deptId);
    
    /**
     * 改派任务到个人.
     * @param taskId  任务ID
     * @param userId 任务原始处理人
     * @param targetUserId 任务改派处理人
     */
    public void reassignTask(Long taskInstanceId, String userId, String targetUserId);
    
    /*********************** 任务方面 **********************/
    /**
     * 获取某用户待处理任务
     * @param userAccount 用户账号
     */
    public List<TaskSummary> getAssignedTasks(String userAccount);
    
    /*********************** 流程方面 **********************/
 
    /**
     * 返回引擎加载的所有流程定义
     */
    public List<Process> getProcesss();
    
    /**
     * 查看当前ksession加载的某个流程定义.
     * @param processId 流程定义ID
     */
    public Process getProcess(String processId);
    
    /**
     * 返回当前ksession中存在的某个流程实例
     * @param processInstId 流程实例ID
     */
    public ProcessInstance getProcessInstInKseesion(Long processInstId);
    
    /**
     * 获取流程实例状态.
     */
    public int getProcessInstanceState(Long processInstId);
    
    /**
     * 返回当前ksession中存在的所有流程实例.
     */
    public Collection<ProcessInstance> getProcessInstsInKseesion(); 
}
