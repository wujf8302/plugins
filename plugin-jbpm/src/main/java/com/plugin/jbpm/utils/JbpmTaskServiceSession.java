package com.plugin.jbpm.utils;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.task.Task;
import org.jbpm.task.service.CannotAddTaskException;
import org.jbpm.task.service.ContentData;
import org.jbpm.task.service.Operation;
import org.jbpm.task.service.TaskException;
import org.jbpm.task.service.TaskService;
import org.jbpm.task.service.TaskServiceSession;

import com.plugin.jbpm.service.HumanTaskEventRegistry;
import com.plugin.jbpm.service.HumanTaskEvents;
/**
 * Jbpm会话类.
 * @author wujf
 */
public class JbpmTaskServiceSession extends TaskServiceSession {
    
    private static final Log       log = LogFactory.getLog(JbpmTaskServiceSession.class);
    
    private HumanTaskEventRegistry humanTaskEventRegistry;
    
    public JbpmTaskServiceSession(TaskService service, EntityManager em) {
        super(service, em);
    }
    
    @Override
    public void addTask(final Task task, final ContentData contentData)
        throws CannotAddTaskException {
        log.info("begin add task ." + task.getNames());
        super.addTask(task, contentData);
        
        if (log.isInfoEnabled()) {
            log.info("add task complete." + task.getId());
        }
        Map<String, Object> params = getTaskParams(contentData);
        humanTaskEventRegistry.postEvent(HumanTaskEvents.ON_CREATED, task, params);
    }
    
    @Override
    public void taskOperation(final Operation operation, final long taskId,
        final String userId, final String targetEntityId,
        final ContentData data, List<String> groupIds) throws TaskException {
        Task task = super.getTask(taskId);
        if (log.isInfoEnabled()) {
            log.info("begin task operation " + operation + ",taskId=" + taskId + ", userId=" + userId);
        }
        Map<String, Object> params = getTaskParams(data);
        switch (operation) {
            case Complete: {
                humanTaskEventRegistry.postEvent(HumanTaskEvents.ON_COMPLETE,task, params);
                break;
            }
        }
        super.taskOperation(operation, taskId, userId, targetEntityId, data,groupIds);
        
        if (log.isInfoEnabled()) {
            log.info("end task operation " + operation + ",taskId=" + taskId+ ", userId=" + userId);
        }
    }
    
    @SuppressWarnings("unchecked")
    private Map<String, Object> getTaskParams(ContentData contentData) {
        Map<String, Object> taskParams = null;
        if (contentData == null || contentData.getContent() == null|| contentData.getContent().length == 0) {
            return new HashMap<String, Object>();
        }
        ByteArrayInputStream bin = new ByteArrayInputStream(contentData.getContent());
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(bin);
            Object object = in.readObject();
            if (object instanceof Map) {
                taskParams = (Map<String, Object>) object;
            } else {
                taskParams = new HashMap<String, Object>();
                taskParams.put("Content", object);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return taskParams;
    }
    
    public void setHumanTaskEventRegistry(
        HumanTaskEventRegistry humanTaskEventRegistry) {
        this.humanTaskEventRegistry = humanTaskEventRegistry;
    }
    
    public HumanTaskEventRegistry getHumanTaskEventRegistry() {
        return humanTaskEventRegistry;
    }
}
