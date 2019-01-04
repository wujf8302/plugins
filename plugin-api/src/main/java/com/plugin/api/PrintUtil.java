package com.plugin.api;

import java.lang.reflect.Method;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.plugin.api.log.LogUtil;

/**
 * @author wujf
 * @version Revision 1.0.0
 */
public class PrintUtil {
    
    public String           dateFormat   = "yyyy-MM-dd HH:mm:ss";
    public String           doubleFormat = "############0.00";
    public final static URL url = PathUtil.getURL("log4j.properties");
    
    private Log             commonsLog;
    private Logger          log4jLog;
    
    private static void init() {
        try {
            if (url != null) {
                PropertyConfigurator.configure(url);
            } else {
                System.err.println("找不到日志配置文件");
            }
        } catch (Exception e) {
            System.err.println("找不到日志配置文件,异常: " + e.getMessage());
        }
    }
    
    private PrintUtil() {
        
    }
    
    private PrintUtil(Logger log, boolean isLoadLog) {
        if (isLoadLog) {
            init();
        }
        this.log4jLog = log;
        if (this.log4jLog == null) {
            System.err.println("请指定log4jLog对象");
        }
    }
    
    private PrintUtil(Log log, boolean isLoadLog) {
        if (isLoadLog) {
            init();
        }
        this.commonsLog = log;
        if (this.commonsLog == null) {
            System.err.println("请指定commonsLog对象");
        }
    }
    
    public static PrintUtil getInstance() {
        return new PrintUtil();
    }
    
    public static PrintUtil getInstance(Logger log) {
        return getInstance(log, false);
    }
    
    public static PrintUtil getInstance(Logger log, boolean isLoadLog) {
        return new PrintUtil(log, isLoadLog);
    }
    
    public static PrintUtil getInstance(Log log) {
        return getInstance(log, false);
    }
    
    public static PrintUtil getInstance(Log log, boolean isLoadLog) {
        return new PrintUtil(log, isLoadLog);
    }
    
    public static void main(String[] args) {
        PrintUtil.getInstance().print("key", "val");
        PrintUtil.getInstance(Logger.getLogger(PrintUtil.class)).print("key",
            "val");
        
        PrintUtil.getInstance(LogFactory.getLog("undefined"), true).print(
            "key", "val");
        
        PrintUtil.getInstance(Logger.getLogger(PrintUtil.class))
            .bufferPrint("key1", "val1").bufferPrint("key2", "val2").print();
        
        PrintUtil.getInstance(LogUtil.getLog(PrintUtil.class))
            .bufferPrint("key3", "val3").bufferPrint("key4", "val4").print();
    }
    
    public void print(Object s) {
        if (this.commonsLog != null) {
            this.commonsLog.info(s);
        } else if (this.log4jLog != null) {
            this.log4jLog.info(s);
        } else {
            System.out.print(s);
        }
    }
    
    public void println(Object s) {
        if (this.commonsLog != null) {
            this.commonsLog.info(s);
        } else if (this.log4jLog != null) {
            this.log4jLog.info(s);
        } else {
            System.out.println(s);
        }
    }
    
    // --------------------------------------------
    public BufferPrint getBufferPrintInstance() {
        return new BufferPrint(this.commonsLog, this.log4jLog);
    }
    
    public class BufferPrint {
        
        private StringBuffer bf = new StringBuffer();
        private BufferPrint  bufferPrint;
        
        private Log          commonsLog;
        private Logger       log4jLog;
        
        public BufferPrint(Log commonsLog, Logger log4jLog) {
            this.commonsLog = commonsLog;
            this.log4jLog = log4jLog;
        }
        
        // 基础方法
        public BufferPrint bufferPrint(String key, Object val, Type type) {
            return bufferPrint(key, val, null, type);
        }
        
        private BufferPrint toString(String key, Object val, String format) {
            return bufferPrint(key, val, format, null);
        }
        
        private BufferPrint bufferPrint(String key, Object val, String format,
            Type type) {
            String msg = getKeyVal(key, val, format, type);
            if (msg != null && !"".equals(msg)) {
                append(msg);
            } else {
                bf.append("[" + getKeyVal(key, val) + "],");// 直接输出
            }
            return bufferPrint;
        }
        
        private void append(String msg) {
            bf.append("[" + msg + "],");
        }
        
        // 格式化输出
        public BufferPrint bufferPrint(String msg) {
            append(msg);
            return bufferPrint;
        }
        
        // 格式化输出
        public BufferPrint bufferPrint(String key, float val, String format) {
            return toString(key, new Float(val), format);
        }
        
        public BufferPrint bufferPrint(String key, double val, String format) {
            return toString(key, new Double(val), format);
        }
        
        public BufferPrint bufferPrint(String key, Date val, String format) {
            return toString(key, (Date) val, format);
        }
        
        public BufferPrint bufferPrint(String key, Calendar val, String format) {
            return toString(key, (Calendar) val, format);
        }
        
        public BufferPrint bufferPrint(String key, Object val) {
            return toString(key, val, null);
        }
        
        // 数组或集合
        public BufferPrint bufferPrint(String key, Map val) {
            return toString(key, val, null);
        }
        
        public BufferPrint bufferPrint(String key, List val) {
            return toString(key, val, null);
        }
        
        public BufferPrint bufferPrint(String key, Object[] val) {
            return toString(key, val, null);
        }
        
        public BufferPrint bufferPrint(String key, short[] val) {
            return toString(key, val, null);
        }
        
        public BufferPrint bufferPrint(String key, int[] val) {
            return toString(key, val, null);
        }
        
        public BufferPrint bufferPrint(String key, long[] val) {
            return toString(key, val, null);
        }
        
        public BufferPrint bufferPrint(String key, float[] val) {
            return toString(key, val, null);
        }
        
        public BufferPrint bufferPrint(String key, double[] val) {
            return toString(key, val, null);
        }
        
        public BufferPrint bufferPrint(String key, boolean[] val) {
            return toString(key, val, null);
        }
        
        public BufferPrint bufferPrint(String key, String[] val) {
            return toString(key, val, null);
        }
        
        public BufferPrint bufferPrint(String key, StringBuffer[] val) {
            return toString(key, val, null);
        }
        
        // -------------------------------------
        // 基本数据类型
        public BufferPrint bufferPrint(String key, short val) {
            return bufferPrint(key, new Short(val));
        }
        
        public BufferPrint bufferPrint(String key, Short val) {
            return toString(key, val, null);
        }
        
        public BufferPrint bufferPrint(String key, int val) {
            return bufferPrint(key, new Integer(val));
        }
        
        public BufferPrint bufferPrint(String key, Integer val) {
            return toString(key, val, null);
        }
        
        public BufferPrint bufferPrint(String key, long val) {
            return bufferPrint(key, new Long(val));
        }
        
        public BufferPrint bufferPrint(String key, Long val) {
            return toString(key, val, null);
        }
        
        public BufferPrint bufferPrint(String key, float val) {
            return bufferPrint(key, new Float(val));
        }
        
        public BufferPrint bufferPrint(String key, Float val) {
            return toString(key, val, null);
        }
        
        public BufferPrint bufferPrint(String key, double val) {
            return bufferPrint(key, new Double(val));
        }
        
        public BufferPrint bufferPrint(String key, Double val) {
            return toString(key, val, null);
        }
        
        public BufferPrint bufferPrint(String key, boolean val) {
            return bufferPrint(key, new Boolean(val));
        }
        
        public BufferPrint bufferPrint(String key, Boolean val) {
            return toString(key, val, null);
        }
        
        public BufferPrint bufferPrint(String key, String val) {
            return toString(key, val, null);
        }
        
        public BufferPrint bufferPrint(String key, StringBuffer val) {
            return toString(key, val, null);
        }
        
        public BufferPrint bufferPrint(String key, Date val) {
            return toString(key, (Date) val, null);
        }
        
        public BufferPrint bufferPrint(String key, Calendar val) {
            return toString(key, (Calendar) val, null);
        }
        
        public String toString() {
            String s = bf.toString();
            if (s != null && s.endsWith(",")) {
                s = s.substring(0, s.length() - 1);
            }
            return s;
        }
        
        public void print() {
            if (this.commonsLog != null) {
                this.commonsLog.info(toString());
            } else if (this.log4jLog != null) {
                this.log4jLog.info(toString());
            } else {
                System.out.print(toString());
            }
        }
        
        public void println() {
            if (this.commonsLog != null) {
                this.commonsLog.info(toString());
            } else if (this.log4jLog != null) {
                this.log4jLog.info(toString());
            } else {
                System.out.println(toString());
            }
        }
        
        public void setBufferPrint(BufferPrint bp) {
            this.bufferPrint = bp;
        }
    }
    
    public BufferPrint bufferPrint(String key, short val) {
        return bufferPrint(key, new Short(val));
    }
    
    public BufferPrint bufferPrint(String key, int val) {
        return bufferPrint(key, new Integer(val));
    }
    
    public BufferPrint bufferPrint(String key, long val) {
        return bufferPrint(key, new Long(val));
    }
    
    public BufferPrint bufferPrint(String key, float val) {
        return bufferPrint(key, new Float(val));
    }
    
    public BufferPrint bufferPrint(String key, double val) {
        return bufferPrint(key, new Double(val));
    }
    
    public BufferPrint bufferPrint(String key, boolean val) {
        return bufferPrint(key, new Boolean(val));
    }
    
    public BufferPrint bufferPrint(String key, Object val) {
        BufferPrint bp = getBufferPrintInstance();
        bp.setBufferPrint(bp);
        return bp.toString(key, val, null);
    }
    
    public BufferPrint bufferPrint(String key, Object val, String format) {
        BufferPrint bp = getBufferPrintInstance();
        bp.setBufferPrint(bp);
        return bp.toString(key, val, format);
    }
    
    public BufferPrint bufferPrint(String key, Object val, Type type) {
        return bufferPrint(key, val, null, type);
    }
    
    public BufferPrint bufferPrint(String key, Object val, String format,
        Type type) {
        BufferPrint bp = getBufferPrintInstance();
        bp.setBufferPrint(bp);
        return bp.bufferPrint(key, val, format, type);
    }
    
    public BufferPrint bufferPrint(String key) {
        BufferPrint bp = getBufferPrintInstance();
        bp.setBufferPrint(bp);
        return bp.bufferPrint(key);
    }
    
    // --------------------------------------------
    public void print(String key, Object val) {
        println(getKeyVal(key, val, null, null));
    }
    
    public void print(String key, Object val, String format) {
        println(getKeyVal(key, val, format, null));
    }
    
    public void print(String key, Object val, Type type) {
        print(getKeyVal(key, val, null, type));
    }
    
    public void print(String key, Object val, String format, Type type) {
        print(getKeyVal(key, val, format, type));
    }
    
    public void print(StringBuffer bf) {
        if (bf != null) {
            print(bf.toString());
        }
    }
    
    // --------------------------------------------
    public void printLine(String v, int n) {
        if (n > 0 && v != null) {
            StringBuffer bf = new StringBuffer();
            for (int i = 0; i < n; i++) {
                bf.append(v);
            }
            print(bf.toString());
        }
    }
    
    public void printlnLine(String v, int n) {
        if (n > 0 && v != null) {
            StringBuffer bf = new StringBuffer();
            for (int i = 0; i < n; i++) {
                bf.append(v);
            }
            println(bf.toString());
        }
    }
    
    public void println() {
        println("");
    }
    
    public void printClassName(Object o) {
        print(o.getClass().getName());
    }
    
    public void printlnClassName(Object o) {
        println(o.getClass().getName());
    }
    
    // --------------------------------------------
    public void println(String key, Object val) {
        println(getKeyVal(key, val, null, null));
    }
    
    public void println(String key, Object val, String format) {
        println(getKeyVal(key, val, format, null));
    }
    
    public void println(String key, Object val, Type type) {
        println(getKeyVal(key, val, null, type));
    }
    
    public void println(String key, Object val, String format, Type type) {
        println(getKeyVal(key, val, format, type));
    }
    
    public void println(StringBuffer bf) {
        if (bf != null) {
            println(bf.toString());
        }
    }
    
    // --------------------------------------------
    /**
     * 获取空提示语
     */
    public String getEmptyMsg(String key) {
        return key + " is null or empty";
    }
    
    public void printEmpty(String key) {
        print(getEmptyMsg(key));
    }
    
    public void printlnEmpty(String key) {
        println(getEmptyMsg(key));
    }
    
    // ---
    public void printlnMap(String keys, Map map, Type type) {
        if (map != null && map.size() > 0) {
            Set set = map.keySet();
            if (type == null) {
                for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                    String key = (String) iterator.next();
                    println(key, map.get(key));
                }
            } else {
                for (Iterator iterator = set.iterator(); iterator.hasNext();) {
                    String key = (String) iterator.next();
                    Object val = map.get(key);
                    if (val instanceof Map) {
                        println(key, map.get(key), type);
                    } else if (val instanceof Set) {
                        println(key, map.get(key), type);
                    } else if (val instanceof List) {
                        println(key, map.get(key), type);
                    } else if (val instanceof short[]) {
                        println(key, map.get(key), type);
                    } else if (val instanceof int[]) {
                        println(key, map.get(key), type);
                    } else if (val instanceof long[]) {
                        println(key, map.get(key), type);
                    } else if (val instanceof float[]) {
                        println(key, map.get(key), type);
                    } else if (val instanceof double[]) {
                        println(key, map.get(key), type);
                    } else if (val instanceof boolean[]) {
                        println(key, map.get(key), type);
                    } else if (val instanceof Object[]) {
                        println(key, map.get(key), type);
                    } else {
                        println(key, map.get(key));
                    }
                }
            }
        } else {
            printlnEmpty(keys);
        }
    }
    
    public void printlnMap(String key, Map map) {
        if (map != null && map.size() > 0) {
            printlnMap(key, map, null);
        } else {
            if (key == null) {
                key = "Map";
            }
            printlnEmpty(key);
        }
    }
    
    public void printlnMap(Map map) {
        printlnMap(null, map);
    }
    
    // ---
    
    public void printlnBean(Object o, Type type) {
        if (o != null) {
            printlnMap("", beanToMap(o), type);
        } else {
            printlnEmpty("Object");
        }
    }
    
    public void printlnBean(Object o) {
        printlnBean(o, null);
    }
    
    // ---
    private String getKey(String key, int i) {
        return key == null ? ""
            : key + " " + (i + 1) + " ";
    }
    
    public void forPrintln(String key, short[] s, Type type) {
        if (s != null && s.length > 0) {
            println(key + " length", s.length);
            String tmp = key;
            for (int i = 0; i < s.length; i++) {
                key = getKey(tmp, i);
                if (type != null) {
                    String msg = type.getKeyVal(key, new Short(s[i]));
                    if (msg != null) {
                        println(key, msg);
                    }
                } else {
                    println(key, s[i]);
                }
            }
        } else {
            printlnEmpty(key);
        }
    }
    
    public void forPrintln(String key, int[] s, Type type) {
        if (s != null && s.length > 0) {
            println(key + " length", s.length);
            String tmp = key;
            for (int i = 0; i < s.length; i++) {
                key = getKey(tmp, i);
                if (type != null) {
                    String msg = type.getKeyVal(key, new Integer(s[i]));
                    if (msg != null) {
                        println(key, msg);
                    }
                } else {
                    println(key, s[i]);
                }
            }
        } else {
            printlnEmpty(key);
        }
    }
    
    public void forPrintln(String key, long[] s, Type type) {
        if (s != null && s.length > 0) {
            println(key + " length", s.length);
            String tmp = key;
            for (int i = 0; i < s.length; i++) {
                key = getKey(tmp, i);
                if (type != null) {
                    String msg = type.getKeyVal(key, new Long(s[i]));
                    if (msg != null) {
                        println(key, msg);
                    }
                } else {
                    println(key, s[i]);
                }
            }
        } else {
            printlnEmpty(key);
        }
    }
    
    public void forPrintln(String key, float[] s, Type type) {
        if (s != null && s.length > 0) {
            println(key + " length", s.length);
            String tmp = key;
            for (int i = 0; i < s.length; i++) {
                key = getKey(tmp, i);
                if (type != null) {
                    String msg = type.getKeyVal(key, new Float(s[i]));
                    if (msg != null) {
                        println(key, msg);
                    }
                } else {
                    println(key, s[i]);
                }
            }
        } else {
            printlnEmpty(key);
        }
    }
    
    public void forPrintln(String key, double[] s, Type type) {
        if (s != null && s.length > 0) {
            println(key + " length", s.length);
            String tmp = key;
            for (int i = 0; i < s.length; i++) {
                key = getKey(tmp, i);
                if (type != null) {
                    String msg = type.getKeyVal(key, new Double(s[i]));
                    if (msg != null) {
                        println(key, msg);
                    }
                } else {
                    println(key, s[i]);
                }
            }
        } else {
            printlnEmpty(key);
        }
    }
    
    public void forPrintln(String key, boolean[] s, Type type) {
        if (s != null && s.length > 0) {
            println(key + " length", s.length);
            String tmp = key;
            for (int i = 0; i < s.length; i++) {
                key = getKey(tmp, i);
                if (type != null) {
                    String msg = type.getKeyVal(key, new Boolean(s[i]));
                    if (msg != null) {
                        println(key, msg);
                    }
                } else {
                    println(key, s[i]);
                }
            }
        } else {
            printlnEmpty(key);
        }
    }
    
    public void forPrintln(String key, String[] s, Type type) {
        if (s != null && s.length > 0) {
            println(key + " length", s.length);
            String tmp = key;
            for (int i = 0; i < s.length; i++) {
                key = getKey(tmp, i);
                if (type != null) {
                    String msg = type.getKeyVal(key, s[i]);
                    if (msg != null) {
                        println(key, msg);
                    }
                } else {
                    println(key, s[i]);
                }
            }
        } else {
            printlnEmpty(key);
        }
    }
    
    private void forPrintln(String key, Object[] os, Type type) {
        key = (key == null || "".equals(key)) ? "Object[]"
            : key;
        if (os != null && os.length > 0) {
            println(key + " length", os.length);
            String tmp = key;
            for (int i = 0; i < os.length; i++) {
                key = getKey(tmp, i);
                if (type != null) {
                    String msg = type.getKeyVal(key, os[i]);
                    if (msg != null) {
                        println(key, msg);
                    }
                } else {
                    println(key, os[i]);
                }
            }
        } else {
            printlnEmpty(key);
        }
    }
    
    public void forPrintln(String key, List s, Type type) {
        key = (key == null || "".equals(key)) ? "List"
            : key;
        if (s != null && s.size() > 0) {
            println(key + " size", s.size());
            String tmp = key;
            for (int i = 0; i < s.size(); i++) {
                key = getKey(tmp, i);
                if (type != null) {
                    String msg = type.getKeyVal(key, s.get(i));
                    if (msg != null) {
                        println(key, msg);
                    }
                } else {
                    println(key, s.get(i));
                }
            }
        } else {
            printlnEmpty(key);
        }
    }
    
    public void forPrintln(String key, Set s, Type type) {
        key = (key == null || "".equals(key)) ? "Set"
            : key;
        if (s != null && s.size() > 0) {
            println(key + " size", s.size());
            String tmp = key;
            int i = 0;
            for (Iterator iterator = s.iterator(); iterator.hasNext();) {
                Object val = (Object) iterator.next();
                key = getKey(tmp, i);
                i++;
                if (type != null) {
                    String msg = type.getKeyVal(key, val);
                    if (msg != null) {
                        println(key, msg);
                    }
                } else {
                    println(key, val);
                }
            }
        } else {
            printlnEmpty(key);
        }
    }
    
    public void printlnArray(Object val, Type type) {
        printlnArray(null, val, type);
    }
    
    public void printlnArray(String key, Object val) {
        printlnArray(key, val, null);
    }
    
    public void printlnArray(String key, Object val, Type type) {
        if (val instanceof List) {
            forPrintln(key, (List) val, type);
        } else if (val instanceof Set) {
            forPrintln(key, (Set) val, type);
        } else if (val instanceof Map) {
            printlnMap(key, (Map) val, type);
        } else if (val instanceof short[]) {
            forPrintln(key, (short[]) val, type);
        } else if (val instanceof int[]) {
            forPrintln(key, (int[]) val, type);
        } else if (val instanceof long[]) {
            forPrintln(key, (long[]) val, type);
        } else if (val instanceof float[]) {
            forPrintln(key, (float[]) val, type);
        } else if (val instanceof double[]) {
            forPrintln(key, (double[]) val, type);
        } else if (val instanceof boolean[]) {
            forPrintln(key, (boolean[]) val, type);
        } else if (val instanceof String[]) {
            forPrintln(key, (String[]) val, type);
        } else if (val instanceof Object[]) {
            forPrintln(key, (Object[]) val, type);
        }
    }
    
    public interface Type {
        public String getKeyVal(String key, Object val);
    }
    
    public String getKeyVal(String key, Object val) {
        return key + " - " + val;
    }
    
    private String getKeyVal(String key, Object val, String format, Type type) {
        
        if (val == null) {// 如果值为null
            return getEmptyMsg(key);
        } else {
            if (type != null) {
                return type.getKeyVal(key, val);// list
                
            } else {
                if (val instanceof Short) {
                    return getKeyVal(key,
                        String.valueOf(((Short) val).shortValue()));
                } else if (val instanceof Integer) {
                    return getKeyVal(key,
                        String.valueOf(((Integer) val).intValue()));
                } else if (val instanceof Long) {
                    return getKeyVal(key,
                        String.valueOf(((Long) val).longValue()));
                } else if (val instanceof Float) {
                    if (format != null && !"".equals(format)) {
                        return getKeyVal(key, format((Float) val, format));
                    } else {
                        return getKeyVal(key,
                            String.valueOf(((Float) val).floatValue()));
                    }
                } else if (val instanceof Double) {
                    if (format != null && !"".equals(format)) {
                        return getKeyVal(key, format((Double) val, format));
                    } else {
                        return getKeyVal(key,
                            String.valueOf(((Double) val).doubleValue()));
                    }
                } else if (val instanceof Boolean) {
                    return getKeyVal(key,
                        String.valueOf(((Boolean) val).booleanValue()));
                } else if (val instanceof String) {
                    return getKeyVal(key, (String) val);
                } else if (val instanceof StringBuffer) {
                    return getKeyVal(key, ((StringBuffer) val).toString());
                } else if (val instanceof Date) {
                    if (format != null && !"".equals(format)) {
                        return getKeyVal(key, format((Date) val, format));
                    } else {
                        return getKeyVal(key, (Date) val);
                    }
                } else if (val instanceof Calendar) {
                    if (format != null && !"".equals(format)) {
                        return getKeyVal(key, format((Calendar) val, format));
                    } else {
                        return getKeyVal(key, (Calendar) val);
                    }
                } else if (val instanceof Map) {
                    return getKeyVal(key, (Map) val);
                    
                } else if (val instanceof List) {
                    return getKeyVal(key, (List) val);
                    
                } else if (val instanceof short[]) {
                    return getKeyVal(key, (short[]) val);
                    
                } else if (val instanceof int[]) {
                    return getKeyVal(key, (int[]) val);
                    
                } else if (val instanceof long[]) {
                    return getKeyVal(key, (long[]) val);
                    
                } else if (val instanceof float[]) {
                    return getKeyVal(key, (float[]) val);
                    
                } else if (val instanceof double[]) {
                    return getKeyVal(key, (double[]) val);
                    
                } else if (val instanceof boolean[]) {
                    return getKeyVal(key, (boolean[]) val);
                } else if (val instanceof Object[]) {
                    return getKeyVal(key, (Object[]) val);
                } else {
                    return getKeyVal(key, val);
                }
            }
        }
    }
    
    // --------------------------------------------
    public void println(String key, boolean val) {
        println(key, String.valueOf(val));
    }
    
    public void print(String key, boolean val) {
        print(key, String.valueOf(val));
    }
    
    public void println(String key, short val) {
        println(key, String.valueOf(val));
    }
    
    public void print(String key, short val) {
        print(key, String.valueOf(val));
    }
    
    public void println(String key, int val) {
        println(key, String.valueOf(val));
    }
    
    public void print(String key, int val) {
        print(key, String.valueOf(val));
    }
    
    public void println(String key, long val) {
        println(key, String.valueOf(val));
    }
    
    public void print(String key, long val) {
        print(key, String.valueOf(val));
    }
    
    public void println(String key, float val) {
        println(key, String.valueOf(val));
    }
    
    public void println(String key, float val, String format) {
        println(key, format(val, format));
    }
    
    public void print(String key, float val) {
        print(key, String.valueOf(val));
    }
    
    public void print(String key, float val, String format) {
        print(key, format(val, format));
    }
    
    public void println(String key, double val) {
        println(key, String.valueOf(val));
    }
    
    public void println(String key, double val, String format) {
        println(key, format(val, format));
    }
    
    public void print(String key, double val) {
        print(key, String.valueOf(val));
    }
    
    public void print(String key, double val, String format) {
        print(key, format(val, format));
    }
    
    public void println(String key, Date val, String format) {
        println(key, format(val, format));
    }
    
    public void println(String key, Calendar val, String format) {
        println(key, format(val, format));
    }
    
    /**
     * 首字母变成大写
     * 
     * @return
     */
    public static String firstToUpper(String fieldName) {
        String s = "";
        if (fieldName != null && fieldName.length() >= 1) {
            String tmp1 = fieldName.substring(0, 1);
            String tmp2 = fieldName.substring(1);
            s = tmp1.toUpperCase() + tmp2;
        }
        
        return s;
    }
    
    /**
     * 首字母变成小写
     * 
     * @return
     */
    public static String firstToLower(String fieldName) {
        String s = "";
        if (fieldName != null && fieldName.length() >= 1) {
            String tmp1 = fieldName.substring(0, 1);
            String tmp2 = fieldName.substring(1);
            s = tmp1.toLowerCase() + tmp2;
        }
        
        return s;
    }
    
    public static String firstCut(String v, String c) {
        return v.substring(c.length(), v.length());
    }
    
    public static Map beanToMap(Object o) {
        Map map = new HashMap();
        try {
            Class c = o.getClass();
            Method m[] = c.getDeclaredMethods();
            for (int i = 0; i < m.length; i++) {
                String s = m[i].getName();
                if (s.indexOf("get") == 0) {
                    s = firstToLower(firstCut(s, "get"));
                    map.put(s, m[i].invoke(o, new Object[0]));
                } else if (s.indexOf("is") == 0) {
                    s = firstToLower(firstCut(s, "is"));
                    map.put(s, m[i].invoke(o, new Object[0]));
                } else if (s.indexOf("has") == 0) {
                    s = firstToLower(firstCut(s, "has"));
                    map.put(s, m[i].invoke(o, new Object[0]));
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return map;
    }
    
    // ---
    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }
    
    public static Calendar getCalendar(long millis) {
        Calendar calendar = getCalendar();
        calendar.setTimeInMillis(millis);
        return calendar;
    }
    
    public static Calendar getCalendar(Date date) {
        Calendar calendar = getCalendar();
        calendar.setTime(date);
        return calendar;
    }
    
    public static String format(Calendar calendar, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(calendar.getTime());
    }
    
    public static String format(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
    
    public static String format(double d, String format) {
        DecimalFormat df = new DecimalFormat();
        return df.format(d);
    }
    
    public static String format(Double d, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(d.doubleValue());
    }
    
    public static String format(float f, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(f);
    }
    
    public static String format(Float f, String format) {
        DecimalFormat df = new DecimalFormat(format);
        return df.format(f.floatValue());
    }
}
