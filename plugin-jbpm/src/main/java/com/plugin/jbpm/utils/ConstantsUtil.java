package com.plugin.jbpm.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 常量定义.
 * @author wujf
 */
public class ConstantsUtil {
    
    private static final Log log  = LogFactory.getLog(ConstantsUtil.class);
    
    public static String serverIpAddr;
    public static int serverPort = 9800;
    
    static{
        try {
            // 默认地址为本机地址.
            serverIpAddr = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("jbpm server ip地址获取失败.", e);
        }
    } 
    
    /**
     * 人工任务事件.
     */
    public interface HumanTaskEvents {
    	
        /** 人工任务创建. */
        public static final String ON_CREATED  = "onCreated";
        /** 人工任务完成. */
        public static final String ON_COMPLETE = "complete";
        
    }

}
