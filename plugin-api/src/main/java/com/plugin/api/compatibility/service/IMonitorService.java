package com.plugin.api.compatibility.service;

import com.plugin.api.compatibility.beans.MonitorInfoBean;
/**
 * 获取java运行环境变量.
 * @author wujf
 * @version Revision 1.0.0
 *
 */
public interface IMonitorService {
    public MonitorInfoBean getMonitorInfoBean() throws Exception;
}
