package com.plugin.api.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import com.plugin.api.EnterUtil;
/**
 * #debug: 显示debug, info, warn, error
 * #info: 显示info, warn, error
 * #warn: 显示warn, error
 * #error: 只显示error
 * .
 * 
 * @author wujf
 * @version Revision 1.0.0
 */
public class LogTool {
    
    private static String path = "d:/logs/";
    private String           encoding;
    
    private String           filename;
    private FileOutputStream out;
   
    private static Map       instances;
    private static String    enter = EnterUtil.enter;
    
    private LogTool() {}
    
    /**
     * 自定义日志(需要完善).
     */
    private static void test(){
        LogTool log = LogTool.getInstance();
        log.write("成功了");
    }

    public static void main(String[] args) {
        
        //test();
        
        //configure(LogUtil.class.getClassLoader().getResource("./com/demo/utils/log4j.properties"));
        
        // log4j
        Logger log4j = getLog4j(LogTool.class);
        log4j.info("log4j: " + System.currentTimeMillis());
        
        // commonsLog
        Log commonsLog = getCommons(LogTool.class);
        commonsLog.info("commonsLog: " + System.currentTimeMillis());
        
        // slf4jLog
        org.slf4j.Logger slf4jLog = getSlf4j(LogTool.class);
        slf4jLog.info("slf4jLog: " + System.currentTimeMillis());
        
        // log4j
        Logger log4jDemo = getLog4j("demo");
        log4jDemo.info("log4j demo: " + System.currentTimeMillis());
        
        // commonsLog
        Log commonsLogDemo = getCommons("demo");
        commonsLogDemo.info("commonsLog demo: " + System.currentTimeMillis());
        
        // slf4jLog
        org.slf4j.Logger slf4jLogDemo = getSlf4j("demo");
        slf4jLogDemo.info("slf4jLog demo: " + System.currentTimeMillis());
        
        //configure(LogUtil.class.getClassLoader().getResource("./com/demo/utils/log/log4j.properties"));
    }

    public static void configure(URL url){
        try {
            org.apache.log4j.PropertyConfigurator.configure(url);
        } catch (Exception e) {}
    }
    
    public static Logger getLog4j(Class clazz) {
        return getLog4j(clazz,1);
    }
    public static Logger getLog4j(String logName) {
        return getLog4j(logName,1);
    }
    private static Logger getLog4j(Object o,int logType) {
        Logger log = null;
        if(o != null){
            if(o instanceof String){
                log = (Logger) getLog((String)o,logType);
            }else{
                log = (Logger) getLog((Class)o,logType);
            }
        }
        return log;
    }
    
    
    public static Log getCommons(Class clazz) {
        return getCommons(clazz,2);
    }
    public static Log getCommons(String logName) {
        return getCommons(logName,2);
    }
    private static Log getCommons(Object o,int logType) {
        Log log = null;
        if(o != null){
            if(o instanceof String){
                log = (Log) getLog((String)o,logType);
            }else{
                log = (Log) getLog((Class)o,logType);
            }
        }
        return log;
    }
    
    public static org.slf4j.Logger getSlf4j(Class clazz) {
        return getSlf4j(clazz,3);
    }
    public static org.slf4j.Logger getSlf4j(String logName) {
        return getSlf4j(logName,3);
    }
    private static org.slf4j.Logger getSlf4j(Object o,int logType) {
        org.slf4j.Logger log = null;
        if(o != null){
            if(o instanceof String){
                log = (org.slf4j.Logger) getLog((String)o,logType);
            }else{
                log = (org.slf4j.Logger) getLog((Class)o,logType);
            }
        }
        return log;
    }
    
    public static Object getLog(Class clazz, int logType) {
        if(clazz == null){
            return getLog(LogTool.class,null,logType);
        }
        return getLog(clazz,null,logType);
    }
    
    public static Object getLog(String logName, int logType) {
        return getLog(null,logName,logType);
    }
    
    private static Object getLog(Class clazz, String logName, int logType) {
        Logger log4jLog = null;
        org.apache.commons.logging.Log commonsLog = null;
        org.slf4j.Logger slf4jLog = null;
        
        if (logType == 1) {
            if (logName != null && !"".equals(logName)) {
                log4jLog = Logger.getLogger(logName);
            } else {
                log4jLog = Logger.getLogger(clazz);
            }
            return log4jLog;
        } else if (logType == 2) {
            if (logName != null && !"".equals(logName)) {
                commonsLog = LogFactory.getLog(logName);
            } else {
                commonsLog = LogFactory.getLog(clazz);
            }
            
            return commonsLog;
        } else if (logType == 3) {
            if (logName != null && !"".equals(logName)) {
                slf4jLog = LoggerFactory.getLogger(logName);
            } else {
                slf4jLog = LoggerFactory.getLogger(clazz);
            }
            return slf4jLog;
        }
        return null;
    }
    
    public static void debug(Log commonsLog,Class clazz,Object arg0) {
        debug(commonsLog,clazz,arg0,null);
    }
    public static void debug(Log commonsLog,Class clazz,Object arg0,Throwable arg1) {
        if(arg1 != null){
            commonsLog.debug(clazz.getName() + " - " + arg0,arg1);
        }else{
            commonsLog.debug(clazz.getName() + " - " + arg0);
        }
    }
    
    public static void error(Log commonsLog,Class clazz,Object arg0) {
        error(commonsLog,clazz,arg0,null);
    }
    public static void error(Log commonsLog,Class clazz,Object arg0,Throwable arg1) {
        if(arg1 != null){
            commonsLog.error(clazz.getName() + " - " + arg0,arg1);
        }else{
            commonsLog.error(clazz.getName() + " - " + arg0);
        }
    }
    
    public static void fatal(Log commonsLog,Class clazz,Object arg0) {
        fatal(commonsLog,clazz,arg0,null);
    }
    public static void fatal(Log commonsLog,Class clazz,Object arg0,Throwable arg1) {
        if(arg1 != null){
            commonsLog.fatal(clazz.getName() + " - " + arg0,arg1);
        }else{
            commonsLog.fatal(clazz.getName() + " - " + arg0);
        }
    }
    
    public static void info(Log commonsLog,Class clazz,Object arg0) {
        info(commonsLog,clazz,arg0,null);
    }
    public static void info(Log commonsLog,Class clazz,Object arg0,Throwable arg1) {
        if(arg1 != null){
            commonsLog.info(clazz.getName() + " - " + arg0,arg1);
        }else{
            commonsLog.info(clazz.getName() + " - " + arg0);
        }
    }
    
    public static void trace(Log commonsLog,Class clazz,Object arg0) {
        trace(commonsLog,clazz,arg0,null);
    }
    public static void trace(Log commonsLog,Class clazz,Object arg0,Throwable arg1) {
        if(arg1 != null){
            commonsLog.trace(clazz.getName() + " - " + arg0,arg1);
        }else{
            commonsLog.trace(clazz.getName() + " - " + arg0);
        }
    }
    
    public static void warn(Log commonsLog,Class clazz,Object arg0) {
        warn(commonsLog,clazz,arg0,null);
    }
    public static void warn(Log commonsLog,Class clazz,Object arg0,Throwable arg1) {
        if(arg1 != null){
            commonsLog.warn(clazz.getName() + " - " + arg0,arg1);
        }else{
            commonsLog.warn(clazz.getName() + " - " + arg0);
        }
    }
    
    //==============================================
    public synchronized static LogTool getInstance() {
        return getInstance("");
    }
    public synchronized static LogTool getInstance(String branchid) {
        String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String key = today + branchid;
        if (instances == null) {
            instances = new HashMap();
            instances.put(key, new LogTool(today, branchid));
        } else if (!instances.containsKey(key)) {
            instances.put(key, new LogTool(today, branchid));
        }
        return (LogTool) instances.get(today + branchid);
    }
    private LogTool(String today, String branchid) {
        
        if (branchid == null || "".equals(branchid)) {
            filename = path + today + "/message.log";
        } else {
            filename = path + today + "/" + branchid + "/message.log";
        }
        encoding = "GBK";
    }
    public void write(Object o) {
        try {
            rollingFile();
            out.write(("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS").format(new Date()) + "]" + o.toString() + enter).getBytes(encoding));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {}
        }
    }
    /**
     * 文件长度大于5000000L重新生成一个文件.
     * @todo如果日志超过10个回滚
     */
    private synchronized void rollingFile() throws IOException {
        File file1 = new File(filename);
        if (!file1.exists()) {
            new File(file1.getParent()).mkdirs();
            file1.createNewFile();
            out = null;
        } else if (file1.length() >= 5000000L) {
            File file2;
            do {
                String ext = String.valueOf(System.currentTimeMillis());
                file2 = new File(filename + "." + ext);
            } while (file2.exists());
            
            file1.renameTo(file2);
            out = null;
            file2 = null;
        }
        out = new FileOutputStream(file1, true);
    }
    //==============================================
}
