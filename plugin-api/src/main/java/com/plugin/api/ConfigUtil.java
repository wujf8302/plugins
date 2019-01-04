package com.plugin.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.plugin.api.beans.config.Module;

public class ConfigUtil {
	
	private static final String DEFAULT_CONFIG_NAME = "config.xml";
	private static final String MODULE_CONFIG_ELEMENT = "module-config";
	private static final String GLOBAL_PROPERTYS_ELEMENT = "global-propertys";
	private static final String PROPERTY_ELEMENT = "property";
	private static final String MODULES_ELEMENT = "modules";
	private static final String MODULE_ELEMENT = "module";
	
	private static Map instances = new HashMap(5);
	private Properties globalProp;
	private Map modulesMap;
	private String cfgFileName;
	private long cfgLastModified;
	private File pFile;

	private ConfigUtil(String fileName) {
		globalProp = null;
		modulesMap = null;
		cfgFileName = null;
		cfgLastModified = System.currentTimeMillis();
		cfgFileName = fileName;
		pFile = PathUtil.getFile(ConfigUtil.class, cfgFileName);
		
		readConfig();
	}

	public static void main(String args[]) throws Exception {
		try {
			ConfigUtil config = getInstance();
			
			String var = config.getGlobalProp("alertPort");
			System.out.println(var);

			Module module = config.getModule("smsc");
			if(module != null){
				System.out.println(module.getPort());
				System.out.println(module.getProperties("LoginMode"));
			}
			
			Module filterIp = config.getModule("filterIp");
			if(filterIp != null){
				Properties properties =  filterIp.getProperties();
				Set set = properties.keySet();
				for (Iterator iterator = set.iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					System.out.println("key:"+key);
					
				}
			}

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	public static synchronized ConfigUtil getInstance() {
		return getInstance("config.xml");
	}

	public static synchronized ConfigUtil getInstance(String name) {
		if (instances.get(name) == null) {
			ConfigUtil config = new ConfigUtil("config.xml");
			instances.put(name, config);
			return config;
		} else {
			return (ConfigUtil) instances.get(name);
		}
	}

	private void readConfig() {
		InputStream is = null;
		Document doc = null;
		try {
			if (pFile == null){
				pFile = PathUtil.getFile(ConfigUtil.class, cfgFileName);
			}
			is = new FileInputStream(pFile);
			doc = XmlTransformUtil.builerDocument(is);
			Element root = doc.getDocumentElement();
			String rootname = root.getNodeName();
			if (!"module-config".equals(rootname))
				throw new IllegalArgumentException("Error while configuring Module.  The root tag of the MODULE configuration XML document must be 'module-config'.");
			NodeList children = root.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				if (child.getNodeType() != 1)
					continue;
				if ("global-propertys".equals(child.getNodeName())) {
					globalProp = parseGlobal((Element) child);
					continue;
				}
				if ("modules".equals(child.getNodeName()))
					modulesMap = parseModules((Element) child);
			}

		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException("初始化配置文件出错:"+ e.getMessage());
		} finally {
			try {
				is.close();
				doc = null;
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public void resetConfig() {
		if (globalProp != null)
			globalProp.clear();
		if (modulesMap != null)
			modulesMap.clear();
		readConfig();
	}

	protected static Properties parseGlobal(Element globalEle) {
		NodeList children = globalEle.getChildNodes();
		Properties globalProp = new Properties();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if (node.getNodeType() != 1|| !"property".equals(node.getNodeName()))
				continue;
			String propKey = ((Element) node).getAttribute("name");
			String propValue = ((Element) node).getAttribute("value");
			if (propKey == null || propKey.equals(""))
				System.err.println("WARN: Config.parseGlobal Cause: name attribute must be not null "+ ((Element) node).toString());
			if (globalProp.getProperty(propKey) != null)
				System.err.println("WARN: Config.parseGlobal Cause: name attribute already define "+ ((Element) node).toString());
			else
				globalProp.put(propKey, propValue);
		}

		return globalProp;
	}

	protected static Map parseModules(Element modulesEle) {
		NodeList children = modulesEle.getChildNodes();
		Map tempMap = new HashMap();
		for (int i = 0; i < children.getLength(); i++) {
			Node node = children.item(i);
			if (node.getNodeType() != 1 || !"module".equals(node.getNodeName()))
				continue;
			Element tempEle = (Element) node;
			Module module = new Module();
			if (tempEle.getAttribute("id") != null)
				module.setID(tempEle.getAttribute("id"));
			if (!"".equals(tempEle.getAttribute("ip")))
				module.setIP(tempEle.getAttribute("ip"));
			if (!"".equals(tempEle.getAttribute("port")))
				module.setPort(Integer.parseInt(tempEle.getAttribute("port")));
			if (!"".equals(tempEle.getAttribute("name")))
				module.setName(tempEle.getAttribute("name"));
			NodeList paraChilden = tempEle.getChildNodes();
			Properties prop = new Properties();
			for (int j = 0; j < paraChilden.getLength(); j++) {
				Node paraNode = paraChilden.item(j);
				if (paraNode.getNodeType() == 1) {
					String key = ((Element) paraNode).getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
					String value = ((Element) paraNode).getElementsByTagName("value").item(0).getFirstChild().getNodeValue();
					prop.put(key, value);
				}
			}

			module.setProperties(prop);
			tempMap.put(module.getID(), module);
		}

		return tempMap;
	}

	public Properties getGlobalProp() {
		synchFileUpdate();
		return globalProp;
	}

	public String getGlobalProp(String key) {
		if (globalProp == null) {
			return null;
		} else {
			synchFileUpdate();
			String value = globalProp.getProperty(key);
			return value;
		}
	}

	public void setGlobalProp(Properties globalProp) {
		this.globalProp = globalProp;
	}

	public void setModulesMap(Map modulesMap) {
		this.modulesMap = modulesMap;
	}

	public Map getModulesMap() {
		synchFileUpdate();
		return modulesMap;
	}

	public Module getModule(String key) {
		Map modulesMap = getModulesMap();
		if(modulesMap == null){
			return null;
		}
		Object value = modulesMap.get(key);
		return (Module) value;
	}

	public static int getInt(String key) {
		return Integer.parseInt(getInstance().getGlobalProp(key));
	}

	public static String getString(String key) {
		return getInstance().getGlobalProp(key);
	}

	private static void checkNULL(String key, Object value) {
		if (value == null)
			throw new RuntimeException("not found key:" + key);
		else
			return;
	}

	private void synchFileUpdate() {
		long newTime = pFile.lastModified();
		if (newTime == 0L || cfgLastModified == 0L)
			System.err.println(pFile + " file does not exist!");
		else if (newTime > cfgLastModified)
			resetConfig();
		cfgLastModified = newTime;
	}
	
	public static class XmlTransformUtil{

	    public static Document builerDocument(InputStream is)throws ParserConfigurationException, FactoryConfigurationError, SAXException, IOException{
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        factory.setNamespaceAware(false);
	        factory.setIgnoringComments(true);
	        factory.setIgnoringElementContentWhitespace(false);
	        factory.setCoalescing(false);
	        factory.setExpandEntityReferences(true);
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        return builder.parse(is);
	    }

	    /**
	        <dependency>
                <groupId>crimson</groupId>
                <artifactId>crimson</artifactId>
                <version>1.1.3</version>
            </dependency>
	     */
//	    public static String docToXMLString(Document doc) {
//	        String strXML = null;
//	        try{
//	            StringWriter sw = new StringWriter();
//	            ((org.apache.crimson.tree.XmlDocument)doc).write(sw);
//	            strXML = sw.toString();
//	            sw.close();
//	        }catch(IOException ioe){
//	            ioe.printStackTrace();
//	        }catch(Exception e){
//	            e.printStackTrace();
//	        }
//	        return strXML;
//	    }

	    public static String transDOM2String(Node dom){
	        TransformerFactory tFactory = TransformerFactory.newInstance();
	        StringWriter sw = new StringWriter();
	        StreamResult sr = new StreamResult(sw);
	        String strXML = null;
	        try{
	            DOMSource domSource = new DOMSource(dom);
	            Transformer transformer = tFactory.newTransformer();
	            transformer.setOutputProperty("encoding", "GB2312");
	            transformer.transform(domSource, sr);
	            strXML = sw.toString();
	        }catch(Exception e){
	            e.printStackTrace();
	        }
	        return strXML;
	    }
	}

}
