package com.plugin.jbpm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.task.Task;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.plugin.jbpm.commons.HumanTaskEventListener;
import com.plugin.jbpm.service.HumanTaskEventService;
/**
 * 人工任务注册器实现类.
 * @author wujf
 */
public class HumanTaskEventServiceImpl implements HumanTaskEventService {

	private static final Log log = LogFactory.getLog(HumanTaskEventServiceImpl.class);

	//@Autowired
	private JdbcTemplate jdbcTemplate;

	private Map<String, List<HumanTaskEventListener>> humanTaskEventListeners = new HashMap<String, List<HumanTaskEventListener>>();

	public void addEventListener(String eventName,HumanTaskEventListener humanTaskListener) {
		// String key = getEventListenerKey(processId, eventName);
		List<HumanTaskEventListener> listeners = humanTaskEventListeners.get(eventName);
		if (listeners == null) {
			listeners = new ArrayList<HumanTaskEventListener>();
			humanTaskEventListeners.put(eventName, listeners);
		}
		listeners.add(humanTaskListener);
	}

	public void postEvent(String eventName, Task task,Map<String, Object> params) {
		try {
			// String processId = findProcessId(processInstanceId);
			List<HumanTaskEventListener> listeners = humanTaskEventListeners.get(eventName);
			if (listeners != null) {
				for (HumanTaskEventListener listener : listeners) {
					listener.onEvent(task, params);
				}
			}
		} catch (Exception ex) {
			log.error(ex);
		}
	}

	public Map<String, List<HumanTaskEventListener>> getHumanTaskListeners() {
		return humanTaskEventListeners;
	}

	public void setHumanTaskListeners(Map<String, List<HumanTaskEventListener>> humanTaskListeners) {
		this.humanTaskEventListeners = humanTaskListeners;
	}

	// ----------------------------

	private String getEventListenerKey(String processId, String eventName) {
		return processId + "###" + eventName;
	}

	private String findProcessById(Long instanceid) {
		String sql = "select processid from jbpm_process_instance_tbl where instanceid=?";
		return jdbcTemplate.queryForObject(sql,new Object[] {instanceid}, String.class);
	}
	
	// ----------------------------

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
