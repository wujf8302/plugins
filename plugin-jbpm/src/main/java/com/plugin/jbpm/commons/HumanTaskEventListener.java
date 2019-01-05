package com.plugin.jbpm.commons;

import java.util.Map;

import org.jbpm.task.Task;

/**
 * 人工任务事件监听器(传参接口).
 * @author wujf
 */
public interface HumanTaskEventListener {
	
    public void onEvent(Task task, Map<String, Object> params);
    
}
