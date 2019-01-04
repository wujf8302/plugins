package com.plugin.jbpm.beans;

/**
 * 人工环节节点.
 * @author wujf
 */
public class HumanTaskNode {
	
    /** 流程ID. */
    private String processId;
    
    /** 环节ID. */
    private String nodeId;
    
    public String getProcessId() {
        return processId;
    }
    
    public void setProcessId(String processId) {
        this.processId = processId;
    }
    
    public String getNodeId() {
        return nodeId;
    }
    
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
