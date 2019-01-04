package com.plugin.api;

import java.lang.reflect.Field;   
import java.lang.reflect.Method;   
import java.lang.reflect.Modifier;   
import java.util.HashMap;   
import java.util.Map;   

import org.apache.log4j.Logger;

public class VOUtils {    
    
    private static Logger log = Logger.getLogger(VOUtils.class);

	/**
	 * 将bean转成map
	 * @param entity
	 * @return
	 */
    public static Map<String, String> beanToMap(Object entity) {   
        Class c = entity.getClass();   
        Object fieldValue = null;   
        String fieldName = null;   
        Field[] fields = c.getDeclaredFields();   
        Map<String, String> fieldMap = new HashMap<String, String>();   
        for (Field field : fields) {   
            fieldName = field.getName();   
            if (field.getModifiers() == Modifier.PUBLIC) {   
                try {   
                    fieldValue = field.get(entity);   
                } catch (Exception e) {   
                    e.printStackTrace();   
                }   
            } else {   
                fieldValue = invokeGet(entity, fieldName);   
            }   
            fieldMap.put(fieldName, fieldValue == null ? "" : fieldValue.toString());   
        }   
        return fieldMap;   
    }   
   
    public static Map<String, Object> beanToMap(Object entity,String packageName) {   
        Class c = entity.getClass();   
        Object fieldValue = null;   
        String fieldName = null;   
        Field[] fields = c.getDeclaredFields();   
        Map<String, Object> fieldMap = new HashMap<String, Object>();   
        for (Field field : fields) {   
            fieldName = field.getName();   
            if (field.getModifiers() == Modifier.PUBLIC) {   
                try {   
                    fieldValue = field.get(entity);   
                } catch (Exception e) {   
                    e.printStackTrace();   
                }   
            } else {   
                fieldValue = invokeGet(entity, fieldName);   
            }   
            if (fieldValue != null && fieldValue.getClass().getName().startsWith(packageName)) {   
                fieldValue = beanToMap(fieldValue, packageName);   
            }   
            fieldMap.put(fieldName, fieldValue);   
        }   
        return fieldMap;   
    }   
  
    private static Object invokeGet(Object entity, String fieldName) {   
        try {   
            Method method = entity.getClass().getMethod("get" + StringUtil.firstToUpper(fieldName));   
            return method.invoke(entity);   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
        return null;   
    }   
  
    public static String beanToWhereSql(Object entity) {   
        StringBuffer ret = new StringBuffer(" where 1=1");   
        Map<String, Object> map = beanToMap(entity, "cn.org.jshuwei.j2ee.util");   
        for (Map.Entry<String, Object> entry : map.entrySet()) {   
            Object value = entry.getValue();   
            String key = entry.getKey();   
            if (value != null) {   
                ret.append(" and ").append(key).append("=").append(value);   
            }   
        }   
        return ret.toString();   
    }   
} 
