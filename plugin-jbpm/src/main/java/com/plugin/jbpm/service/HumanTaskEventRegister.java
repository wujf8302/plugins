package com.plugin.jbpm.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.plugin.jbpm.service.HumanTaskEventRegistry;

/**
 * 人工任务注册器.
 */
public abstract class HumanTaskEventRegister implements InitializingBean {
    
    @Autowired
    private HumanTaskEventRegistry humanTaskEventRegistry;//com.plugin.jbpm.service.impl.HumanTaskEventRegistryImpl
   
    public abstract void regist();
    
    public void afterPropertiesSet() throws Exception {
        regist();
    }
    
    public HumanTaskEventRegistry getHumanTaskEventRegistry() {
        return humanTaskEventRegistry;
    }
   
    public void setHumanTaskEventRegistry(HumanTaskEventRegistry humanTaskEventRegistry) {
        this.humanTaskEventRegistry = humanTaskEventRegistry;
    }
}
