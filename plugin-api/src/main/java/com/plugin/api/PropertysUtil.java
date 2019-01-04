package com.plugin.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultAttribute;

public class PropertysUtil {
	
	private static Logger log = Logger.getLogger(PropertysUtil.class);

	private static Map propertys = new HashMap();
	
	private static String defaultFileName = "propertys.xml";//propertys.properties propertys.xml
	
	public static void main(String[] args) {
	    printAllProperty();
			
		/*
		//测试获取静态或动态变量
		printProperty("period");
		printStaticProperty("sourcepath");
		try {
			Thread.sleep(20000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("----------------------");
		printProperty("period");
		printStaticProperty("sourcepath");
	    */
		
		//printDatoSource("oracle46");
		
		//printModule("userModule");
		//printModuleDatoSource("userModule","oracle127");
	}

	//-----------------------------------------------------------------
	private static void printKeyValue(String key, Object val) {
		System.out.println(key + " - " + val);
	}
	
	private static void printMap(Map map){
		if (map != null && map.size() > 0) {
			Set set = map.keySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				printKeyValue(key, map.get(key));
			}
		}
	}
	
	public static void printAllProperty(){
		printMap(getPropertysMap());
	}
	
	public static void printProperty(String key){
		printKeyValue(key,getProperty(key));
	}
	
	public static void printStaticProperty(String key){
		printKeyValue(key,getStaticProperty(key));
	}
	
	public static void printDatoSource(String datoSourceName){
		printMap(getDatoSource(datoSourceName));
	}
	
	public static void printModule(String moduleName){
        printMap(getModule(moduleName));
	}
	
    public static void printModuleDatoSource(String moduleName,String datoSourceName){
        printMap(getModuleDatoSource(moduleName,datoSourceName));
	}
	//-----------------------------------------------------------------
	/**
	 * 默认 动态
	 */
	public static Map getPropertysMap(){
		return getPropertysMap(defaultFileName,null);
	}
	
	/**
	 * 默认 静态
	 */
	public static Map getStaticPropertysMap(){
		return getPropertysMap(defaultFileName,"static");
	}
	
	/**
	 * 外部 动态
	 */
	public static Map getPropertysMap(String fileName){
		return getPropertysMap(fileName,null);
	}
	
	/**
	 * 外部 静态
	 */
	public static Map getStaticPropertysMap(String fileName){
		return getPropertysMap(fileName,"static");
	}
	
	//-----------------------------------------------------------------	
	/**
	 * 最底层方法
	 */
	private static Map getPropertysMap(String fileName,String type){
		Map map = null;
		
		if("static".equals(type)){//静态
			map = (Map)propertys.get(fileName);
			if(map == null){
				map = getPropertys(fileName);
				propertys.put(fileName, map);
			}
		}else{//动态
			map = getPropertys(fileName);
		}
		
		return map;
	}
	
	/**
	 * 获取指定的配置文件
	 * propertys.xml 或 config.properties
	 */
	private synchronized static Map getPropertys(String fileName) {
		
		if(fileName == null || "".equals(fileName)){
			fileName = defaultFileName;
		}
		
		if(isXMLFile(fileName)){
			return readXML(fileName);
		}
		
		return readProperties(fileName);
	}
	//-----------------------------------------------------------------
	/**
	 * 默认
	 */
	public static Object getObject(String key) {
		Object val = "";
		Map map = getPropertysMap();
		if(map != null){
			val = map.get(key);
		}
		return val;
	}
	
	/**
	 * 默认
	 */
	public static Object getStaticObject(String key) {
		Object val = "";
		Map map = getStaticPropertysMap();
		if(map != null){
			val = map.get(key);
		}
		return val;
	}
	
	//-----------------------------------------------------------------
	/**
	 * 默认 获取动态的属性
	 */
	public static String getProperty(String key){
		return (String)getObject(key);
	}
	
	/**
	 * 默认 获取动态的DatoSource
	 */
	public static Map getDatoSource(String datoSourceName){
		return (Map)getObject(datoSourceName);
	}
	
	/**
	 * 默认 获取动态的Module
	 */
	public static Map getModule(String moduleName){
		return (Map)getObject(moduleName);
	}
	
	/**
	 * 默认 获取动态的Module DatoSource
	 */
	 public static Map getModuleDatoSource(String moduleName,String datoSourceName){
		 return (Map)getModule(moduleName).get(datoSourceName);
	 }
	 
	 /**
	  * 默认 获取静态的属性
	  */
	 public static String getStaticProperty(String key){
		 return (String)getStaticObject(key);
	 }
	
	/**
	 * 默认 获取静态的DatoSource
	 */
	public static Map getStaticDatoSource(String datoSourceName){
		return (Map)getStaticObject(datoSourceName);
	}
	
	/**
	 * 默认 获取静态的Module
	 */
	public static Map getStaticModule(String moduleName){
		return (Map)getStaticObject(moduleName);
	}
	 
	 /**
	 * 默认 获取静态的Module DatoSource
	 */
	 public static Map getStaticModuleDatoSource(String moduleName,String datoSourceName){
		 return (Map)getStaticModule(moduleName).get(datoSourceName);
	 }
	 
	//-----------------------------------------------------------------
	//类型转换
	//---
	public static int getInt(Map propertyMap,String key){
		Object o = propertyMap.get(key);
		if(o instanceof String){
			return Integer.valueOf((String)o).intValue();
		}
		return ((Integer)o).intValue();
	}
	public static int getInt(String key){
		return Integer.valueOf(getProperty(key)).intValue();
	}
	public static int getStaticInt(String key){
		return Integer.valueOf(getStaticProperty(key)).intValue();
	}
	
	//---
	
	public static String getString(Map propertyMap,String key){
		return (String)propertyMap.get(key);
	}
	public static String getString(String key){
		return getProperty(key);
	}
	public static String getStaticString(String key){
		return getStaticProperty(key);
	}
	
	//---
	
	public static boolean getBoolean(Map propertyMap,String key){
		return Boolean.valueOf((String)propertyMap.get(key)).booleanValue();
	}
	public static boolean getBoolean(String key){
		return Boolean.valueOf(getProperty(key)).booleanValue();
	}
	public static boolean getStaticBoolean(String key){
		return Boolean.valueOf(getStaticProperty(key)).booleanValue();
	}
	
	//---
	
	public static long getLong(Map propertyMap,String key){
		Object o = propertyMap.get(key);
		if(o instanceof String){
			return Long.valueOf((String)o).longValue();
		}
		return ((Long)o).longValue();
	}
	public static long getLong(String key){
		return Long.valueOf(getProperty(key)).longValue();
	}
	public static long getStaticLong(String key){
		return Long.valueOf(getStaticProperty(key)).longValue();
	}
	
	//---
	
	public static short getShort(Map propertyMap,String key){
		Object o = propertyMap.get(key);
		if(o instanceof String){
			return Short.valueOf((String)o).shortValue();
		}
		return ((Short)o).shortValue();
	}
	public static short getShort(String key){
		return Short.valueOf(getProperty(key)).shortValue();
	}
	public static short getStaticShort(String key){
		return Short.valueOf(getStaticProperty(key)).shortValue();
	}
	
	//---
	
	public static Date getDate(Map propertyMap,String key,String format){
		Object o = propertyMap.get(key);
		if(o instanceof String){
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			try {
				simpleDateFormat.parse((String)o);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	public static Date getDate(String key,String format){
		String s = getProperty(key);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			simpleDateFormat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public static Date getStaticDate(String key,String format){
		String s = getStaticProperty(key);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			simpleDateFormat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	//-----------------------------------------------------------------
	
	/**
	 * 判断是否是xml文件
	 */
	private static boolean isXMLFile(String fileName){
		if(fileName != null && !"".equals(fileName) && fileName.endsWith(".properties")){
			return false;
		}
		return true;
	}
	
	 /**
	 * 根据key获取属性(解析xml)
	 */
	private static String gutAttribute(Element element,String key) {
		String val = "";
		for (Iterator it = element.attributeIterator(); it.hasNext();) {
			DefaultAttribute attribute1 = (DefaultAttribute) it.next();
			if (key.equals(attribute1.getName())) {
				val = attribute1.getValue();
				break;
			}
		}
		return val;
	}

	/**
	 * 属性(解析xml)
	 */
	private static void putAttribute(Element element, Map map) {
		for (Iterator it = element.attributeIterator(); it.hasNext();) {
			DefaultAttribute attribute = (DefaultAttribute) it.next();
			map.put(attribute.getName(), attribute.getValue());
		}
	}

	/**
	 * 成员(解析xml)
	 */
	private static void putElement(Element element, Map map) {
		String key = "";
		String value = "";
		for (Iterator configIterator = element.elementIterator(); configIterator.hasNext();) {

			Element property = (Element) configIterator.next();
			// ---
			if ("property".equals(property.getName())) {

				for (Iterator it = property.attributeIterator(); it.hasNext();) {

					DefaultAttribute attribute = (DefaultAttribute) it.next();
					if ("name".equals(attribute.getName())) {
						key = attribute.getValue();
					} else if ("value".equals(attribute.getName())) {
						value = attribute.getValue();
					} else {
						map.put(attribute.getName(), attribute.getValue());
					}
				}
				map.put(key, value);

			} else if ("datasource".equals(property.getName())) {

				Map dataSourceMap = new HashMap();
				String datasourceId = gutAttribute(property,"id");//根据key获取属性
				if (datasourceId != null && !"".equals(datasourceId)) {
					putElement(property, dataSourceMap);  //成员
					map.put(datasourceId, dataSourceMap);
				}
			}
			// ---
		}
	}

	/**
	 * 读取xml
	 */
	private static Map readXML(String fileName) {

		Map propertysMap = new HashMap();
		SAXReader xmlReader = new SAXReader();

		String path = getFilePath(fileName);
		if(path == null || "".equals(path)){
			log.error(fileName + " file not find");
			return propertysMap;
		}
		
		Document doc = null;
		try {
			doc = xmlReader.read(path);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (doc != null) {

			Element root = doc.getRootElement();
			
			String id = gutAttribute(root,"id");//一级 属性
			
			Iterator rootIterator = root.elementIterator();
			while (rootIterator.hasNext()) {

				Element rootElement = (Element) rootIterator.next();//一级 成员

				if ("global".equals(rootElement.getName())) {

					putAttribute(rootElement, propertysMap);//二级 属性
					putElement(rootElement, propertysMap);  //二级 成员

				} else if ("modules".equals(rootElement.getName())) {//一级 成员
					
					putAttribute(rootElement, propertysMap);//二级 属性
					
					for (Iterator rootElementIterator = rootElement.elementIterator(); rootElementIterator.hasNext();) {
						
						Element element = (Element) rootElementIterator.next();
						
						if ("module".equals(element.getName())) { //二级 成员
							Map moduleMap = new HashMap();
							
							String moduleId = gutAttribute(element,"id");//二级 属性 key
							
							if (moduleId != null && !"".equals(moduleId)) {
								
								putAttribute(element, moduleMap);//二级 属性
								moduleMap.remove("id");
								putElement(element, moduleMap);  //二级 成员  
								
								propertysMap.put(moduleId, moduleMap);
							}
						}
					}
				}
			}
		}

		return propertysMap;
	}

	public static Properties getProperties(File file) {
		Properties props = new Properties();
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			props.load(fileInputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
   	        try {
	   	        if(fileInputStream != null){
	 				fileInputStream.close();
	 			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return props;
	}
	
	/**
	 * 获取资源文件
     * @param path="./config/config.ini"
     */
	public static Properties getProperties(String path) {
		return getProperties(new File(path));
	}
	
	/**
	 * 将信息写入资源文件
     */
	public void storeProperties(String fileName,Properties properties){
		storeProperties(fileName,properties,null);
	}
	public void storeProperties(File file,Properties properties){
		if(file != null){
			storeProperties(file.getAbsolutePath(),properties,null);
		}
	}
	public void storeProperties(String fileName,Map<String,String> map){
		storeProperties(fileName,null,map);
	}
	public void storeProperties(String fileName,Properties properties,Map<String,String> map){
		String path = getFilePath(fileName);
		if(path == null || "".equals(path)){
			log.error(fileName + " file not find");
			return;
		}
		if(properties == null){
			properties = getProperties(path);
		}
		
		if(map != null && map.size() > 0){
			Set<String> keySet = map.keySet();
			for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				properties.setProperty(key,(String)map.get(key));
			}
		}

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(path);
			properties.store(out,"");
		} catch (Exception e) {
			log.error("",e);
		}finally{
			try {
				if(out != null){
					out.close();
				}
				if(properties != null){
					 properties.clone();
				}
			} catch (Exception e) {
				log.error("",e);
			}
			out = null;
			properties = null;
		}
	}
	
	/**
	 * 获取配置文件下的所有参数 config.properties
	 */
	private static Map readProperties(String fileName) {
		/*
		1、使用java.util.Properties类的load()方法
		示例： InputStream in = lnew BufferedInputStream(new FileInputStream(name));
		      Properties p = new Properties();
		      p.load(in);
		 
		2、使用java.util.ResourceBundle类的getBundle()方法
		示例： ResourceBundle rb = ResourceBundle.getBundle(name, Locale.getDefault());
		 
		3、使用java.util.PropertyResourceBundle类的构造函数
		示例： InputStream in = new BufferedInputStream(new FileInputStream(name));
		      ResourceBundle rb = new PropertyResourceBundle(in);
		 
		4、使用class变量的getResourceAsStream()方法
		示例： InputStream in = JProperties.class.getResourceAsStream(name);
		      Properties p = new Properties();
		        p.load(in);
		 
		5、使用class.getClassLoader()所得到的java.lang.ClassLoader的getResourceAsStream()方法
		 示例： InputStream in = JProperties.class.getClassLoader().getResourceAsStream(name);
		       Properties p = new Properties();
		       p.load(in);
		 
		6、使用java.lang.ClassLoader类的getSystemResourceAsStream()静态方法
		示例： InputStream in = ClassLoader.getSystemResourceAsStream(name);
		      Properties p = new Properties();
		      p.load(in);
		 
		补充
		 
		Servlet中可以使用javax.servlet.ServletContext的getResourceAsStream()方法
		 示例：InputStream in = context.getResourceAsStream(path);
		      Properties p = new Properties();
		      p.load(in);

		 */
		
		Map paramMap = new HashMap();
		
		String path = getFilePath(fileName);
		if(path == null || "".equals(path)){
			log.error(fileName + " file not find");
			return paramMap;
		}
		
		Properties properties = getProperties(new File(getFilePath(fileName)));
		Set set = properties.keySet();
        for(Iterator iterator = set.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			paramMap.put(key, properties.get(key));
		}
        if(properties != null){
			 properties.clone();
		}
		properties = null;
        
	    return paramMap;
	}
	
	private static String getFilePath(String fileName){
		return PathUtil.getPath(PropertysUtil.class, fileName);
	}
}
