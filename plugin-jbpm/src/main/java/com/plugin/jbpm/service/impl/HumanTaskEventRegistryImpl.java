package com.plugin.jbpm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.plugin.jbpm.service.HumanTaskEventListener;
import com.plugin.jbpm.service.HumanTaskEventRegistry;

public class HumanTaskEventRegistryImpl implements HumanTaskEventRegistry {
    
    private static final Log log  = LogFactory.getLog(HumanTaskEventRegistryImpl.class);
    
    @Autowired
    private JdbcTemplate               jdbcTemplate;
    
    private Map<String, List<HumanTaskEventListener>> eventListenerRegistry = new HashMap<String, List<HumanTaskEventListener>>();
    
    public void postEvent(String eventName, Task task,
        Map<String, Object> params) {
        try {
            // String processId = findProcessId(processInstanceId);
            List<HumanTaskEventListener> eventListeners = eventListenerRegistry.get(eventName);
            if (eventListeners != null) {
                for (HumanTaskEventListener eventListener : eventListeners) {
                    eventListener.onEvent(task, params);
                }
            }
        } catch (Exception ex) {
            log.error(ex);
        }
    }
    
    public void addEventListener(String eventName,
        HumanTaskEventListener eventListener) {
        // String key = getEventListenerKey(processId, eventName);
        List<HumanTaskEventListener> eventListeners = eventListenerRegistry.get(eventName);
        if (eventListeners == null) {
            eventListeners = new ArrayList<HumanTaskEventListener>();
            eventListenerRegistry.put(eventName, eventListeners);
        }
        eventListeners.add(eventListener);
    }
    
    public Map<String, List<HumanTaskEventListener>> getEventListenerRegistry() {
        return eventListenerRegistry;
    }
    
    public void setEventListenerRegistry(
        Map<String, List<HumanTaskEventListener>> eventListenerRegistry) {
        this.eventListenerRegistry = eventListenerRegistry;
    }
    
    //----------------------------
    
    private String getEventListenerKey(String processId, String eventName) {
         return processId + "###" + eventName;
    }
    
    private String findProcessId(Long processInstanceId) {
        String sql = "select processid from jbpm_process_instance_tbl where instanceid=?";
        return jdbcTemplate.queryForObject(sql,new Object[]{processInstanceId},String.class);
    }
}
