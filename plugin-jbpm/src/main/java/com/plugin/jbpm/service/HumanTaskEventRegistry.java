package com.plugin.jbpm.service;

import java.util.Map;
import org.jbpm.task.Task;

public interface HumanTaskEventRegistry {
    
    public void addEventListener(String eventName, HumanTaskEventListener eventListener);
    
    //public void addEventListener(String processId, String eventName,HumanTaskEventListener eventListener);
    
    public void postEvent(String eventName, Task task, Map<String, Object> params);
    
}
