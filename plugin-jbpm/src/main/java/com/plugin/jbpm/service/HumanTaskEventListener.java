package com.plugin.jbpm.service;

import java.util.Map;

import org.jbpm.task.Task;

/**
 * 人工任务事件监听器.
 * @author wujf
 */
public interface HumanTaskEventListener {
    public void onEvent(Task task, Map<String, Object> params);
}
