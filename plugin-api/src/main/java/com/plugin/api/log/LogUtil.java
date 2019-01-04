package com.plugin.api.log;

import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * 日志工具
 * 
 * @author wujf
 * @version Revision 1.0.0
 */
public class LogUtil implements Log {
    
    private Class           clazz;
    private String          logName;
    
    private Logger          log4jLog;
    private Log             commonsLog;
    
    public static URL config = null;
    
    private LogUtil() {}
    
    private LogUtil(Class clazz,int logType) {
        this.clazz = clazz;
        if (logType == 1) {
            log4jLog = Logger.getLogger(this.clazz);
        } else if (logType == 2) {
            commonsLog = LogFactory.getLog(clazz);
        }
    }
    
    private LogUtil(String logName,int logType) {
        this.logName = logName;
        if (logType == 1) {
            log4jLog = Logger.getLogger(this.logName);
        } else if (logType == 2) {
            commonsLog = LogFactory.getLog(this.logName);
        }
    }
    
    public static Log getCommonsLog(Class clazz) {
        return getCommonsLog(clazz,null);
    }
    public static Log getCommonsLog(Class clazz,String config) {
        if (config != null && !"".equals(config)) {
            PropertyConfigurator.configure(config);
        }
        return new LogUtil(clazz,2);
    }
    
    public static Log getLog(Class clazz) {
        return getLog(clazz,null);
    }
    public static Log getLog(Class clazz,String config) {
        if (config != null && !"".equals(config)) {
            PropertyConfigurator.configure(config);
        }
        return new LogUtil(clazz,1);
    }
    
    public static void main(String[] args) {
        
        org.apache.log4j.PropertyConfigurator.configure(LogUtil.class.getClassLoader().getResource("./com/demo/utils/log4j.properties"));
        
        Log log1 = LogUtil.getLog(LogUtil.class);
        Log log2 = LogUtil.getCommonsLog(LogUtil.class);
        
        log1.info("log1-info-log4j");
        log1.debug("log1-debug-log4j");
        log1.warn("log1-warn-log4j");
        
        log2.info("log2-info-commonsLog");
        log2.debug("log2-debug-commonsLog");
        log2.warn("log2-warn-commonsLog");
        
        org.apache.log4j.PropertyConfigurator.configure(LogUtil.class.getClassLoader().getResource("./com/demo/utils/log/log4j.properties"));
        
        Log log3 = LogUtil.getLog(LogUtil.class);
        Log log4 = LogUtil.getCommonsLog(LogUtil.class);
        
        log3.info("log3-info-log4j");
        log3.debug("log3-debug-log4j");
        log3.warn("log3-warn-log4j");
        
        log4.info("log4-info-commonsLog");
        log4.debug("log4-debug-commonsLog");
        log4.warn("log4-warn-commonsLog");
        
        log1.info("reload---------log1-info-log4j");
        log1.debug("reload---------log1-debug-log4j");
        log1.warn("reload---------log1-warn-log4j");
        
        log2.info("reload---------log2-info-commonsLog");
        log2.debug("reload---------log2-debug-commonsLog");
        log2.warn("reload---------log2-warn-commonsLog");
        
    }
    
    public void debug(Object arg0) {
        if (commonsLog != null) {
            commonsLog.debug(this.clazz.getName() + " - " + arg0);
        } else {
            log4jLog.debug(arg0);
        }
    }
    
    public void debug(Object arg0, Throwable arg1) {
        if (commonsLog != null) {
            commonsLog.debug(this.clazz.getName() + " - " + arg0, arg1);
        } else {
            log4jLog.debug(arg0, arg1);
        }
    }
    
    public void error(Object arg0) {
        if (commonsLog != null) {
            commonsLog.error(this.clazz.getName() + " - " + arg0);
        } else {
            log4jLog.error(arg0);
        }
    }
    
    public void error(Object arg0, Throwable arg1) {
        if (commonsLog != null) {
            commonsLog.error(this.clazz.getName() + " - " + arg0, arg1);
        } else {
            log4jLog.error(arg0, arg1);
        }
    }
    
    public void fatal(Object arg0) {
        if (commonsLog != null) {
            commonsLog.fatal(this.clazz.getName() + " - " + arg0);
        } else {
            log4jLog.fatal(arg0);
        }
    }
    
    public void fatal(Object arg0, Throwable arg1) {
        if (commonsLog != null) {
            commonsLog.fatal(this.clazz.getName() + " - " + arg0, arg1);
        } else {
            log4jLog.fatal(arg0, arg1);
        }
    }
    
    public void info(Object arg0) {
        if (commonsLog != null) {
            commonsLog.info(this.clazz.getName() + " - " + arg0);
        } else {
            log4jLog.info(arg0);
        }
    }
    
    public void info(Object arg0, Throwable arg1) {
        if (commonsLog != null) {
            commonsLog.info(this.clazz.getName() + " - " + arg0, arg1);
        } else {
            log4jLog.info(arg0, arg1);
        }
    }
    
    public void trace(Object arg0) {
        if (commonsLog != null) {
            commonsLog.trace(this.clazz.getName() + " - " + arg0);
        } else {
            log4jLog.trace(arg0);
        }
    }
    
    public void trace(Object arg0, Throwable arg1) {
        if (commonsLog != null) {
            commonsLog.trace(this.clazz.getName() + " - " + arg0, arg1);
        } else {
            log4jLog.trace(arg0, arg1);
        }
    }
    
    public void warn(Object arg0) {
        if (commonsLog != null) {
            commonsLog.warn(this.clazz.getName() + " - " + arg0);
        } else {
            log4jLog.warn(arg0);
        }
    }
    
    public void warn(Object arg0, Throwable arg1) {
        if (commonsLog != null) {
            commonsLog.warn(this.clazz.getName() + " - " + arg0, arg1);
        } else {
            log4jLog.warn(arg0, arg1);
        }
    }
    
    public boolean isDebugEnabled() {
        return commonsLog.isDebugEnabled();
    }
    
    public boolean isErrorEnabled() {
        return commonsLog.isErrorEnabled();
    }
    
    public boolean isFatalEnabled() {
        return commonsLog.isFatalEnabled();
    }
    
    public boolean isInfoEnabled() {
        return commonsLog.isInfoEnabled();
    }
    
    public boolean isTraceEnabled() {
        return commonsLog.isTraceEnabled();
    }
    
    public boolean isWarnEnabled() {
        return commonsLog.isWarnEnabled();
    }
}
