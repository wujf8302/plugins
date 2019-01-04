package com.plugin.jbpm.service;

import java.util.Map;
import org.jbpm.task.Task;

import com.plugin.jbpm.commons.HumanTaskEventListener;

/**
 * 人工任务注册器接口.
 * @author wujf
 */
public interface HumanTaskEventService {
    
    public void addEventListener(String eventName, HumanTaskEventListener humanTaskListener);

    public void postEvent(String eventName, Task task, Map<String, Object> params);
}
