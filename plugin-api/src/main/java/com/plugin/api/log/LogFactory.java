package com.plugin.api.log;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.util.Hashtable;

import javax.xml.parsers.FactoryConfigurationError;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * 日志工厂(短信助手).
 * @author wujf
 */
public class LogFactory{

    private static Hashtable instances = new Hashtable();

	public static void main(String[] args) {
		
		Log log1 = LogFactory.getLog(LogFactory.class);
		Logger log2 = Logger.getLogger(LogFactory.class);
		
		log1.info("============wujf1");
		log2.info("============wujf2");
	}
     
    public static synchronized Log getLog(Class clazz){
        Object obj = instances.get(clazz);
        if(obj == null){
            obj = new Log4jImpl(clazz);
            instances.put(clazz, obj);
        }
        return (Log)obj;
    }

    public static synchronized Log getLog(String logName){
        Object obj = instances.get(logName);
        if(obj == null){
            obj = new Log4jImpl(logName);
            instances.put(logName, obj);
        }
        return (Log)obj;
    }
    
    //=====================================================
    
    /**
     * 初始化日志
     */
    public static class LogSynch extends Thread{

        boolean running;
        File logFile;
        long cfgLastModified;

        public LogSynch(){
            super("LogSynch");
            running = true;
            logFile = null;
            cfgLastModified = System.currentTimeMillis();
            super.setDaemon(true);
            ClassLoader cl = getClass().getClassLoader();
            URL fileUrl = cl.getResource("log4j.xml");
            try{
                logFile = new File(URLDecoder.decode(fileUrl.getPath(), System.getProperty("file.encoding")));
            }catch(UnsupportedEncodingException uee){
                uee.printStackTrace();
                logFile = new File(fileUrl.getPath());
            }
        }

        public void stopThread(){
            running = false;
        }

        public void updateLogConfig()throws MalformedURLException, FactoryConfigurationError{
            long newTime = logFile.lastModified();
            if(newTime == 0L || cfgLastModified == 0L)
                System.err.println(logFile + " file does not exist!");
            else
            if(newTime > cfgLastModified)
                resetConfig();
            cfgLastModified = newTime;
        }

        public void resetConfig()throws MalformedURLException, FactoryConfigurationError{
            LogManager.resetConfiguration();
            DOMConfigurator.configure(logFile.toURL());
        }

        public void run(){
            while(running) 
                try{
                    Thread.sleep(60000L);
                    updateLogConfig();
                }catch(Exception e){
                    e.printStackTrace();
                }
        }
    }
    
    //=====================================================
    public interface Log{

        public abstract boolean isDebugEnabled();

        public abstract boolean isErrorEnabled();

        public abstract boolean isFatalEnabled();

        public abstract boolean isInfoEnabled();

        public abstract boolean isTraceEnabled();

        public abstract boolean isWarnEnabled();

        public abstract void trace(Object obj);

        public abstract void trace(Object obj, Throwable throwable);

        public abstract void debug(Object obj);

        public abstract void debug(Object obj, Throwable throwable);

        public abstract void info(Object obj);

        public abstract void info(Object obj, Throwable throwable);

        public abstract void warn(Object obj);

        public abstract void warn(Object obj, Throwable throwable);

        public abstract void error(Object obj);

        public abstract void error(Object obj, Throwable throwable);

        public abstract void infoAndAlert(Object obj, Throwable throwable, int i, int j);

        public abstract void warnAndAlert(Object obj, Throwable throwable, int i, int j);

        public abstract void errorAndAlert(Object obj, Throwable throwable, int i, int j);

        public abstract void infoAndAlert(Object obj, Throwable throwable, int i, int j, String s, int k);

        public abstract void warnAndAlert(Object obj, Throwable throwable, int i, int j, String s, int k);

        public abstract void errorAndAlert(Object obj, Throwable throwable, int i, int j, String s, int k);

        public abstract void clearAlert(Object obj, int i, int j);

        public abstract void clearAlert(Object obj, int i, int j, String s, int k);
    }

    //=====================================================
    public static class Log4jImpl implements Log, Serializable {

    	public static final long serialVersionUID = 0x800343L;
    	private static final boolean is12;
    	private static final String FQCN;
    	private transient Logger logger;
    	private String name;
    	//private static UdpAlarm udpAlarm = null;
    	private static String alertHost = null;
    	private static int alertPort = 9999;
    	private static LogSynch logSynch;

    	static {
    		is12 = org.apache.log4j.Priority.class.isAssignableFrom(org.apache.log4j.Level.class);
    		FQCN = Log4jImpl.class.getName();
    		logSynch = null;
    		try {
    			logSynch = new LogSynch();
    			logSynch.start();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		//initUDPClient();//告警日志
    	}
    	
    	public Log4jImpl(String name) {
    		logger = null;
    		this.name = null;
    		logger = Logger.getLogger(name);
    	}

    	public Log4jImpl(Class clazz) {
    		logger = null;
    		name = null;
    		logger = Logger.getLogger(clazz);
    	}

    	private static void initUDPClient(String host,int port) {
    		try {
    			String cfgVal = host;
    			if (cfgVal != null){
    				alertHost = host;
    			}else{
    				System.out.println("告警服务器IP未配置");
    			}
    			cfgVal = String.valueOf(port);
    			if (cfgVal != null){
    				alertPort = port;
    			}else{
    				System.out.println("告警服务器UDP端口未配置");
    			}
    		} catch (Throwable e) {
    			e.printStackTrace();
    		}
    		/*
    		try {
    			udpAlarm = new UdpAlarm();
    		} catch (Throwable e) {
    			e.printStackTrace();
    		}
    		*/
    	}

    	public Logger getLogger() {
    		if (logger == null){
    			logger = Logger.getLogger(name);
    		}
    		return logger;
    	}
    	
    	public void trace(Object message) {
    		if (is12){
    			getLogger().log(FQCN, Level.DEBUG, message, null);
    		}else{
    			getLogger().log(FQCN, Level.DEBUG, message, null);
    		}
    	}

    	public void trace(Object message, Throwable t) {
    		if (is12){
    			getLogger().log(FQCN, Level.DEBUG, message, t);
    		}else{
    			getLogger().log(FQCN, Level.DEBUG, message, t);
    		}
    	}

    	public void debug(Object message) {
    		if (is12){
    			getLogger().log(FQCN, Level.DEBUG, message, null);
    		}else{
    			getLogger().log(FQCN, Level.DEBUG, message, null);
    		}
    	}

    	public void debug(Object message, Throwable t) {
    		if (is12){
    			getLogger().log(FQCN, Level.DEBUG, message, t);
    		}else{
    			getLogger().log(FQCN, Level.DEBUG, message, t);
    		}
    	}

    	public void info(Object message) {
    		if (is12){
    			getLogger().log(FQCN, Level.INFO, message, null);
    		}else{
    			getLogger().log(FQCN, Level.INFO, message, null);
    		}
    	}

    	public void info(Object message, Throwable t) {
    		if (is12){
    			getLogger().log(FQCN, Level.INFO, message, t);
    		}else{
    			getLogger().log(FQCN, Level.INFO, message, t);
    		}
    	}

    	public void warn(Object message) {
    		if (is12){
    			getLogger().log(FQCN, Level.WARN, message, null);
    		}else{
    			getLogger().log(FQCN, Level.WARN, message, null);
    		}
    	}

    	public void warn(Object message, Throwable t) {
    		if (is12){
    			getLogger().log(FQCN, Level.WARN, message, t);
    		}else{
    			getLogger().log(FQCN, Level.WARN, message, t);
    		}
    	}

    	public void error(Object message) {
    		if (is12){
    			getLogger().log(FQCN, Level.ERROR, message, null);
    		}else{
    			getLogger().log(FQCN, Level.ERROR, message, null);
    		}
    	}

    	public void error(Object message, Throwable t) {
    		if (is12){
    			getLogger().log(FQCN, Level.ERROR, message, t);
    		}else{
    			getLogger().log(FQCN, Level.ERROR, message, t);
    		}
    	}

    	public void fatal(Object message) {
    		if (is12){
    			getLogger().log(FQCN, Level.FATAL, message, null);
    		}else{
    			getLogger().log(FQCN, Level.FATAL, message, null);
    		}
    	}

    	public void fatal(Object message, Throwable t) {
    		if (is12){
    			getLogger().log(FQCN, Level.FATAL, message, t);
    		}else{
    			getLogger().log(FQCN, Level.FATAL, message, t);
    		}
    	}

    	public boolean isDebugEnabled() {
    		return getLogger().isDebugEnabled();
    	}

    	public boolean isErrorEnabled() {
    		if (is12){
    			return logger.isEnabledFor(Level.ERROR);
    		}else{
    			return getLogger().isEnabledFor(Level.ERROR);
    		}
    	}

    	public boolean isFatalEnabled() {
    		if (is12){
    			return getLogger().isEnabledFor(Level.FATAL);
    		}else{
    			return getLogger().isEnabledFor(Level.FATAL);
    		}
    	}

    	public boolean isInfoEnabled() {
    		return getLogger().isInfoEnabled();
    	}

    	public boolean isTraceEnabled() {
    		return getLogger().isDebugEnabled();
    	}

    	public boolean isWarnEnabled() {
    		if (is12){
    			return logger.isEnabledFor(Level.WARN);
    		}else{
    			return logger.isEnabledFor(Level.WARN);
    		}
    	}

    	public void infoAndAlert(Object message, Throwable t, int source,int errorNo) {
    		infoAndAlert(message, t, source, errorNo, alertHost, alertPort);
    	}

    	public void warnAndAlert(Object message, Throwable t, int source,int errorNo) {
    		warnAndAlert(message, t, source, errorNo, alertHost, alertPort);
    	}

    	public void errorAndAlert(Object message, Throwable t, int source,
    			int errorNo) {
    		errorAndAlert(message, t, source, errorNo, alertHost, alertPort);
    	}

    	public void infoAndAlert(Object message, Throwable t, int source,int errorNo, String host, int port) {
    		send(1, source, errorNo, message.toString(), host, port);
    		info("[" + errorNo + "]" + message.toString(), t);
    	}

    	public void warnAndAlert(Object message, Throwable t, int source,int errorNo, String host, int port) {
    		send(2, source, errorNo, message.toString(), host, port);
    		warn("[" + errorNo + "]" + message.toString(), t);
    	}

    	public void clearAlert(Object message, int source, int errorNo) {
    		clearAlert(message.toString(), source, errorNo, alertHost, alertPort);
    	}

    	public void clearAlert(Object message, int source, int errorNo,String host, int port) {
    		send(5, source, errorNo, message.toString(), host, port);
    		info(message.toString());
    	}

    	public void errorAndAlert(Object message, Throwable t, int source,int errorNo, String host, int port) {
    		send(4, source, errorNo, message.toString(), host, port);
    		error("[" + errorNo + "]" + message.toString(), t);
    	}

    	public void send(int priority, int moduleType, int errorNo, String errMsg,String destHost, int destPort) {
    		/*
    		try {
    			udpAlarm.send(priority, moduleType, errorNo, errMsg, destHost,destPort);
    		} catch (Exception e) {
    			getLogger().error("发送UDP告警时出错:",e);
    		}
    		*/
    	}
    }
    

    public static class UdpAlarm{

        private DatagramSocket ds;
        private static final int HEAD_LENGTH = 12;
        private static final int MSG_TYPE = 1;
        private static int seq = 1;

        public UdpAlarm()throws SocketException{
            ds = null;
            ds = new DatagramSocket();
        }

        public void send(int priority, int moduleType, int errorNo, String errMsg, String destHost, int destPort)throws IOException{
            if(destHost == null)
                throw new NullPointerException("发送告警时，host ip为空");
            if(destPort < 50 || destPort > 0x10000)
                throw new IllegalArgumentException("告警目的端口要在50-65536之间");
            int msgLength = 0;
            byte error[] = (byte[])null;
            if(errMsg != null){
                try{
                    error = errMsg.getBytes("GBK");
                }catch(UnsupportedEncodingException ue){
                    error = errMsg.getBytes();
                }
                msgLength = error.length;
            }
            int packLength = 28 + msgLength;
            ByteBuffer buf = ByteBuffer.allocate(packLength);
            buf.putInt(packLength);
            buf.putInt(priority);
            buf.putInt(moduleType);
            buf.putInt(errorNo);
            buf.put(error);
            DatagramPacket dp = new DatagramPacket(buf.array(), packLength, InetAddress.getByName(destHost), destPort);
            ds.send(dp);
        }

        private static synchronized int getSeq(){
            if(seq >= 0x7fffffff){
                seq = 1;
            }
            return seq++;
        }

        public void close(){
            if(ds != null){
                ds.close();
            }
        }
    }
}
