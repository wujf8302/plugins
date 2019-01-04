package com.plugin.jbpm.service;

/**
 * 人工任务事件.
 */
public interface HumanTaskEvents {
    /** 人工任务创建. */
    public static final String ON_CREATED  = "onCreated";
    /** 人工任务完成. */
    public static final String ON_COMPLETE = "complete";
}
