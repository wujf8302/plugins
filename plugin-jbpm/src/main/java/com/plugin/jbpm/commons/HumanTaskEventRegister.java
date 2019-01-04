/*
 * Copyright ? 2008，北京福富软件技术股份有限公司
 * All Rights Reserved.
 *
 * 文件名称： HumanTaskTaskEventRegister.java
 * 摘   要：
 * 作   者： miaoj
 * 创建时间： 2011-8-10 下午03:05:39
 */
package com.plugin.jbpm.commons;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.plugin.jbpm.service.HumanTaskEventService;

/**
 * 人工任务注册器（抽象类，重写regist方法）.
 */
public abstract class HumanTaskEventRegister implements InitializingBean {
	
	@Autowired
    private HumanTaskEventService humanTaskEventService;

	public abstract void regist();

	/**
	 * 注册事件.
	 */
    public void afterPropertiesSet() throws Exception {
        regist();
    }
    
    public HumanTaskEventService getHumanTaskEventService() {
		return humanTaskEventService;
	}

	public void setHumanTaskEventService(HumanTaskEventService humanTaskEventService) {
		this.humanTaskEventService = humanTaskEventService;
	}
}
