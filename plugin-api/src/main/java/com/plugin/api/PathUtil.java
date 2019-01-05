package com.plugin.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.plugin.api.FileUtil;
import com.plugin.api.StringUtil;
/**
 * 
 * @author wujf
 */
public class PathUtil {
    
    private static Logger      log      = Logger.getLogger(PathUtil.class);
    private static ClassLoader classLoader;
    private static PathUtil    instance = null;
    private static ResourcesFactory resourcesFactory = null;
    
    public static void main(String[] args) {
        //System.out.println(getResource("log4j.xml"));
        // System.out.println(getResource("files/img/sq.png"));
        // System.out.println(getResource("conf/axis2-01.xml"));
        
    }
    
    private PathUtil() {
        
    }
    
    public static ClassLoader getClassLoader() {
        if (classLoader != null) {
            return classLoader;// getInstance().getClassLoader()
        } else {
            return Thread.currentThread().getContextClassLoader();
        }
    }
    
    public static void setClassLoader(ClassLoader classLoader) {
        PathUtil.classLoader = classLoader;
    }
    
    public static PathUtil getInstance() {
        if (instance == null) {
            instance = new PathUtil();
        }
        return instance;
    }
    
    public static ResourcesFactory getResourcesFactory() {
        if (resourcesFactory != null) {
            return resourcesFactory;
        } else {
            return new ResourcesFactory();
        }
    }
    
    public static URL getResource(String fileName) {
        URL url = null;
        try {
            ClassLoader loader = getClassLoader();
            url = loader.getResource(fileName);
        } catch (Exception e) {
            log.error("", e);
        }
        
        return url;
    }
    
    /*
     * 查找单个文件
     */
    public static String getWebPath(String fileName) {
        String webRoot = getWebRoot();
        if (webRoot != null && !"".equals(webRoot)) {
            String path = webRoot + fileName;
            if (isExist(path)) {// 当前目录
                return path;
            }
            // 其他目录
            File file = new File(webRoot);
            if (file.exists() && file.isDirectory()) {
                return find(file, fileName);// 在工程根目录下检索，有多个返回第一个
            }
        }
        return null;
    }
    
    // -----------------------------------------------
    /*
     * 查找全部文件
     */
    public static List getWebPaths(String fileName) {
        String webRoot = getWebRoot();
        List list = new ArrayList();
        if (webRoot != null && !"".equals(webRoot)) {
            File file = new File(webRoot);
            if (file.exists() && file.isDirectory()) {
                find(file, fileName, list);
            }
        }
        return list;
    }
    
    public static List getPaths(String fileName) {
        String classesRoot = getClassesRoot();
        List list = new ArrayList();
        if (classesRoot != null && !"".equals(classesRoot)) {
            File file = new File(classesRoot);
            if (file.exists() && file.isDirectory()) {
                find(file, fileName, list);
            }
        }
        return list;
    }
    
    // -----------------------------------------------
    
    /**
     * 要搜索的文件与当前类在同一个文件夹下
     * 搜索路径 当前目录 src目录 其他目录
     */
    public static String getPath(String fileName) {
        return getPath(PathUtil.class, fileName);
    }
    
    public static Reader getReader(String fileName) {
        return getReader(PathUtil.class, fileName);
    }
    
    public static URL getURL(String fileName) {
        return getURL(PathUtil.class, fileName);
    }
    
    public static InputStream getInputStream(String fileName) {
        return getInputStream(PathUtil.class, fileName);
    }
    
    public static File getFile(String fileName) {
        return getFile(PathUtil.class, fileName);
    }
    
    public static Reader getReader(Class clazz, String fileName) {
        return new InputStreamReader(getInputStream(clazz, fileName));
    }
    
    public static URL getURL(Class clazz, String fileName) {
        String path = getPath(clazz, fileName);
        if (path != null && !"".equals(path)) {
            try {
                return new URL("file:" + path);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static InputStream getInputStream(Class clazz, String fileName) {
        try {
            return new FileInputStream(getPath(clazz, fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static File getFile(Class clazz, String fileName) {
    	String path = getPath(clazz, fileName);
    	if(path == null || "".equals(path)){
    		return null;
    	}
        return new File(path);
    }
    
    /**
     * jar所在lib目录查找，没找到在上层WEB-INF 或  classes下找.
     */
    private static String jar(Class clazz, String fileName,String path){
        
	   	String val = "";
	   	int index = path.indexOf(".jar!");
	   	if(index != -1){
	   		//E:/wujf/stock/tomcat-6.0.35/webapps/stock/WEB-INF/lib/
		   	String libPath = "";
		   	String temp = path.substring(0,index);
		   	int index2 = temp.lastIndexOf("/");
		   	if(index2 != -1){
		   		libPath = temp.substring(0,index2);
		   	}
		   	
		    //System.out.println("4-------------lib path: " + libPath);
		   	
		   	File libFile = new File(libPath);
		       if (libFile.exists() && libFile.isDirectory()) {
		       	//E:/wujf/stock/tomcat-6.0.35/webapps/stock/WEB-INF/lib目录下检索，有多个返回第一个
		           val = find(libFile, fileName);
		       }
		       
		   	if(val == null || "".equals(val)){
		   		String webinfPath = "";
		   		libPath = libPath.replace("\\", "/");
		   		
		   		if(libPath.endsWith("/lib")){
		   			//E:/wujf/stock/tomcat-6.0.35/webapps/stock/WEB-INF/
		   			webinfPath = libPath.substring(0,libPath.length()-"/lib".length());
		       	}
		   		
		   		if(webinfPath != null && !"".equals(webinfPath)){
		   			
		   			//E:/wujf/stock/tomcat-6.0.35/webapps/stock/WEB-INF/classes
		       		String classesPath = webinfPath + File.separator + "classes";
		       		File classesFile = new File(classesPath);
		               if (classesFile.exists() && classesFile.isDirectory()) {
		               	//E:/wujf/stock/tomcat-6.0.35/webapps/stock/WEB-INF/classes目录下检索，有多个返回第一个
		                   val = find(classesFile, fileName);
		               }
		               
		               if(val == null || "".equals(val)){
		               	//E:/wujf/stock/tomcat-6.0.35/webapps/stock/WEB-INF
		               	File webinfFile = new File(webinfPath);
		                   if (webinfFile.exists() && webinfFile.isDirectory()) {
		                   	//E:/wujf/stock/tomcat-6.0.35/webapps/stock/WEB-INF目录下检索，有多个返回第一个
		                       val = find(webinfFile, fileName);
		                   }
		                   
		                   //E:/wujf/stock/tomcat-6.0.35/webapps/stock
		                   if(val == null || "".equals(val)){
		                   	//E:/wujf/stock/tomcat-6.0.35/webapps/stock/目录下检索，有多个返回第一个
		                       val = find(webinfFile.getParentFile(), fileName);
		                   }
		               } 
		       	}
		   	}
	   	}
	   	
	   	return val;
    }

    /**
     * 搜索路径 当前目录 src目录 其他目录
     */
    public static String getPath(Class clazz, String fileName) {
    	if (isExist(fileName)) {
	        return fileName;
	     }
    	
        // 当前目录
    	String currentPath = getCurrentPath(clazz);
    	
    	//System.out.println("1-------------" + currentPath);
    	
    	if(currentPath != null && !"".equals(currentPath)){
    		 String path = currentPath + fileName;
    	     if (isExist(path)) {
    	        return path;
    	     }
    	}
    	
        //E:/wujf/stock/tomcat-6.0.35/webapps/stock/WEB-INF/lib/plugin-api-1.0.0-SNAPSHOT.jar!/com/plugin/api/
    	currentPath = jar(clazz,fileName,currentPath);//jar所在lib目录查找，没找到在上层WEB-INF 或  classes下找
        
        //System.out.println("2-------------" + currentPath);
        
        // classes目录及在jar包里找
        URL url = getResource(fileName);
        //jar:file:/E:/wujf/stock/repository/com/plugin/plugin-stockmodel/1.0.0/plugin-stockmodel-1.0.0.jar!/config.properties
        if(url != null){
            String path = url2path(url);
            
            //System.out.println("3-------------" + path);
            
            if(path != null && !"".equals(path)){
        	   	if(path.indexOf(".jar!") != -1){
        	   		return path;
        	   	}
        	   	
        	   	if (isExist(path)) {
                    return path;
                }
            } 
        }else{
        	fileName = fileName.trim();
        	fileName = fileName.replace("\\", "/");
        	if(fileName.startsWith("/")){
        		fileName = fileName.substring(1,fileName.length());
        	}
        	url = getResource(fileName);
        	
            if(url != null){
                String path = url2path(url);
                
                //System.out.println("4-------------" + path);
                
                if(path != null && !"".equals(path)){
            	   	if(path.indexOf(".jar!") != -1){
            	   		return path;
            	   	}
            	   	
            	   	if (isExist(path)) {
                        return path;
                    }
                } 
            }
        }
        
        // src目录
        String basepath = getBasepath(clazz);
        if(basepath != null && !"".equals(basepath)){
            String path = basepath + fileName;
            if (isExist(path)) {
                return path;
            }
        }
        
        // src目录
        try {
            File file = getResourceAsFile(fileName);
            String path = file.getAbsolutePath();
            if (isExist(path)) {
                return path;
            }
        } catch (IOException e) {}
        
        //---
        // maven目录
        if(basepath != null && !"".equals(basepath)){
        	String val = "";
            //E:/wujf/plugins/com-plugin/plugin-stock/target/classes/
            File file = new File(basepath);
            if (file.exists() && file.isDirectory()) {
                val = find(file, fileName);// 在工程根目录下检索，有多个返回第一个
            }
            if(val == null || "".equals(val)){
                int index2 = basepath.indexOf("/target/");
                if(index2 != -1){
                	//E:/wujf/plugins/com-plugin/plugin-stock/src
                	basepath = basepath.substring(0,index2) + "/src";
                }
                file = new File(basepath);
                if (file.exists() && file.isDirectory()) {
                    val = find(file, fileName);// 在工程根目录下检索，有多个返回第一个
                }
            }
            return val;
        }
        
        return "";
    }
    
	/**
	 * 读取文件
	 * 文本格式例: id,name,age,date
	 */
	public static List<String> loadFile(String path,String charset){
		return loadFile(PathUtil.class,path,charset);
	}
    public static List<String> loadFile(Class clazz,String path,String charset){
    	if(path != null && !"".equals(path)){
    		path = getPath(clazz,path);
    		if(path != null && !"".equals(path)){
    			String temp = ".jar!";
	       		 //E:/wujf/stock/repository/com/plugin/plugin-stockmodel/1.0.0/plugin-stockmodel-1.0.0.jar!/data/cc/gzgg.txt
	       		int index = path.indexOf(temp);
	       	   	if(index != -1){
	       	   		path = path.substring(index+temp.length(),path.length());
	       	   		path = path.replace("\\", "/");
	       	   		if(!path.startsWith("/")){//路径起始位置不包含/
	       	   			path = "/" + path;
	               	}
	       	   		return loadFile(clazz.getResourceAsStream(path),charset);//jar里读取
	       	   	}else{
	       	   		return FileUtil.readFile(new File(path),charset);//路径读取
	       	   	}
    		}
    	}
    	return (new ArrayList<String>());
    }
	private static List<String> loadFile(InputStream inputStream,String charset){
		try {
			return loadFile(new InputStreamReader(inputStream,charset));
		} catch (UnsupportedEncodingException e) {
			log.error("",e);
		}
		return null;
	}
	private static List<String> loadFile(InputStreamReader inputStreamReader){
		if(inputStreamReader != null){
			try {
				return (List<String>)loadFile(new BufferedReader(inputStreamReader),1);
			} catch (Exception e) {
				log.error("",e);
			}
		}
		return (new ArrayList<String>());
	}
	private static Object loadFile(BufferedReader buff,int returnType){
		List<String> list = null;
		StringBuffer sb = null;
		if(2 == returnType){
			sb = new StringBuffer();
		}else{
			list = new ArrayList<String>();
		}
		if(buff != null){
			String str = null;
			try {
				while ((str = buff.readLine()) != null) {
					if(2 == returnType){
						sb.append(StringUtil.replaceBlank(str));
					}else{
						list.add(str);
					}
				}
			} catch (Exception e) {
				log.error("",e);
			} finally {
				if (buff != null) {
					try {
						buff.close();
					} catch (IOException e) {
						System.err.println(e.getMessage());
					}
				}
			}
		}
		return list;
	}
	
	public static StringBuffer getString(String path,String charset) {
		Class clazz = PathUtil.class;
		if(path != null && !"".equals(path)){
    		path = getPath(clazz,path);
    		if(path != null && !"".equals(path)){
    			String temp = ".jar!";
	       		 //E:/wujf/stock/repository/com/plugin/plugin-stockmodel/1.0.0/plugin-stockmodel-1.0.0.jar!/data/cc/gzgg.txt
	       		int index = path.indexOf(temp);
	       	   	if(index != -1){
	       	   		path = path.substring(index+temp.length(),path.length());
	       	   		path = path.replace("\\", "/");
	       	   		if(!path.startsWith("/")){//路径起始位置不包含/
	       	   			path = "/" + path;
	               	}
	       	   		try {
	       	   		    StringBuffer sb = new StringBuffer();
	       	   		    List<String> data = loadFile(clazz.getResourceAsStream(path),charset);
	       	   			if(data != null){
	       	   				for (int i = 0; i < data.size(); i++) {
	       	   				    sb.append(StringUtil.replaceBlank(data.get(i)));
							}
	       	   			}
						return sb;//jar里读取
					} catch (Exception e) {
						e.printStackTrace();
					}
	       	   	}else{
	       	   		return FileUtil.getString(path,charset);//路径读取
	       	   	}
    		}
    	}

		return (new StringBuffer());
	}

    // -----------------------------------------------
    
    /**
     * bs工程.
     * E:/wujf/stock/tomcat-6.0.35/webapps/stock/WEB-INF/classes/
     */
    public static String getWebRoot() {
        String classesRoot = getClassesRoot();
        String s = "/WEB-INF/classes/";
        if(classesRoot != null){
        	 if (classesRoot.indexOf(s) != -1) {
                 return classesRoot.substring(0, classesRoot.indexOf(s) + 1);
             }
        }
       
        return "";
    }
      
    /**
     * cs工程.
     */
    public static String getProjectRoot() {
        String binRoot = getClassesRoot();
        String bin = "/bin/";
        if (binRoot != null && !"".equals(binRoot)) {
            return binRoot.substring(0, binRoot.indexOf(bin));
        }
        return null;
    }
    
    public static String getClassesRoot() {
        return getBasepath(PathUtil.class);
    }
    
    private static String getBasepath(Class clazz) {
        return common(clazz, "/");
    }
    
    /**
     * 获取当前目录下的基础路径
     */
    private static String getCurrentPath(Class clazz) {
        return common(clazz, "");
    }
    
    private static String common(Class clazz, String path) {
        // clazz.getResource("").toURI().getPath()
        URL url = clazz.getResource(path);
        return url2path(url);
    }
    
    public static String url2path(URL url) {
        if (url != null) {
            String path = url.getPath();
            if(path != null){
            	if(path.startsWith("/")){
                	// 【/E:/wujf/stock/tomcat-6.0.35/webapps/stock/WEB-INF/lib/xxxx.txt】
                	return path.substring(1, path.length());
                }
                if(path.startsWith("file:/")){
                	//【file:/E:/wujf/stock/tomcat-6.0.35/webapps/stock/WEB-INF/lib/xxxx.txt】
                	return path.substring("file:/".length(), path.length());
                }
            }
            
            
            return path;
        }
        return "";
    }
    
    // -----------------------------------------------
    
    /**
     * 递归遍历，查找满足后缀的文件
     * E:/wujf/stock/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/plugin-stock/WEB-INF
     */
    private static String find(File dirFile, String fileName) {
        if (dirFile.exists() && dirFile.isDirectory()) {
            File[] subFiles = dirFile.listFiles();
            for (int i = 0; i < subFiles.length; i++) {
                File subFile = subFiles[i];
                if (subFile.isDirectory()) {
                	String path = subFile.getAbsolutePath();
                    if (path.endsWith(fileName)) {
                        return path;
                    }
                     
                    path = find(subFile, fileName);
                    if (path != null && !"".endsWith(path)) {
                        return path;
                    }
                } else {
                    String path = subFile.getAbsolutePath();
                    path = path.replace("\\", "/");
                    if(fileName != null) {
                    	fileName = fileName.replace("\\", "/");
                    }
                    
                    //System.out.println(fileName + " - " + path);
                    
                    if (path.endsWith(fileName)) {
                        return path;
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * 递归遍历，查找满足后缀的文件
     */
    private static void find(File dirFile, String fileName, List list) {
        if (dirFile.exists() && dirFile.isDirectory()) {
            File[] subFiles = dirFile.listFiles();
            for (int i = 0; i < subFiles.length; i++) {
                File subFile = subFiles[i];
                if (subFile.isDirectory()) {
                    find(subFile, fileName, list);
                } else {
                    String path = subFile.getAbsolutePath();
                    
                    if (path.endsWith(fileName)) {
                        list.add(path);
                    }
                }
            }
        }
    }
    
    private static boolean isExist(String path) {
    	if(path != null && !"".equals(path)){
    		 return (new File(path)).exists();
    	}
        return false;
    }
    
    // ---
    public static URL getResourceURL(String resource) throws IOException {
        return getResourceURL(getClassLoader(), resource);
    }
    
    public static URL getResourceURL(ClassLoader loader, String resource)
        throws IOException {
        URL url = null;
        if (loader != null) {
            url = loader.getResource(resource);
        }
        if (url == null) {
            url = ClassLoader.getSystemResource(resource);
        }
        
        if (url == null) {
            throw new IOException("Could not find resource " + resource);
        } else {
            return url;
        }
    }
    
    public static InputStream getResourceAsStream(String resource)
        throws IOException {
        return getResourceAsStream(getClassLoader(), resource);
    }
    
    public static Reader getResourceAsReader(String resource)
        throws IOException {
        return new InputStreamReader(getResourceAsStream(resource));
    }
    
    public static File getResourceAsFile(ClassLoader loader, String resource)
        throws IOException {
        return new File(getResourceURL(loader, resource).getFile());
    }
    
    /**
     * 获取资源文件，包括获取jar包中的资源文件
     */
    //InputStream input = PathUtil.getResourcesUtil().getResourceAsStream("/validator.js"); 
    public static InputStream getResourceAsStream(ClassLoader loader,
        String resource) throws IOException {
        InputStream in = null;
        if (loader != null) {
            in = loader.getResourceAsStream(resource);
        }
        if (in == null) {//可获取jar包中的资源文件
            in = ClassLoader.getSystemResourceAsStream(resource);
        }
        
        if (in == null) {
            throw new IOException("Could not find resource " + resource);
        } else {
            return in;
        }
    }
    public static Reader getResourceAsReader(ClassLoader loader, String resource)
        throws IOException {
        return new InputStreamReader(getResourceAsStream(loader, resource));
    }
    
    public static File getResourceAsFile(String resource) throws IOException {
        return new File(getResourceURL(resource).getFile());
    }
    
    public static Reader getUrlAsReader(String urlString) throws IOException {
        return new InputStreamReader(getUrlAsStream(urlString));
    }
    
    public static InputStream getUrlAsStream(String urlString)
        throws IOException {
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        
        return conn.getInputStream();
    }
    
    public static Properties getResourceAsProperties(String resource)
        throws IOException {
        Properties props = new Properties();
        InputStream in = null;
        String propfile = resource;
        in = getResourceAsStream(propfile);
        props.load(in);
        in.close();
        
        return props;
    }
    
    public static Properties getResourceAsProperties(ClassLoader loader,
        String resource) throws IOException {
        Properties props = new Properties();
        InputStream in = null;
        String propfile = resource;
        in = getResourceAsStream(loader, propfile);
        props.load(in);
        in.close();
        
        return props;
    }
    
    public static Properties getUrlAsProperties(String urlString)
        throws IOException {
        Properties props = new Properties();
        InputStream in = null;
        String propfile = urlString;
        in = getUrlAsStream(propfile);
        props.load(in);
        in.close();
        
        return props;
    }
    
    public static class ResourcesFactory {
        
        private ClassLoader defaultClassLoader;
        
        private ResourcesFactory() {
        	
        }
        
        public URL getResourceURL(String resource) throws IOException {
            return getResourceURL(getClassLoader(), resource);
        }
        
        public URL getResourceURL(ClassLoader loader, String resource)
            throws IOException {
            URL url = null;
            if (loader != null) {
                url = loader.getResource(resource);
            }
            if (url == null) {
                url = ClassLoader.getSystemResource(resource);
            }
            
            if (url == null) {
                throw new IOException("Could not find resource " + resource);
            } else {
                return url;
            }
        }
        
        public InputStream getResourceAsStream(String resource)
            throws IOException {
            return getResourceAsStream(getClassLoader(), resource);
        }
        
        public InputStream getResourceAsStream(ClassLoader loader,
            String resource) throws IOException {
            InputStream in = null;
            if (loader != null) {
                in = loader.getResourceAsStream(resource);
            }
            if (in == null) {//获取jar是的资源文件
                in = ClassLoader.getSystemResourceAsStream(resource);
            }
            
            if (in == null) {
                throw new IOException("Could not find resource " + resource);
            } else {
                return in;
            }
        }
        
        public Properties getResourceAsProperties(String resource)
            throws IOException {
            Properties props = new Properties();
            InputStream in = null;
            String propfile = resource;
            in = getResourceAsStream(propfile);
            props.load(in);
            in.close();
            
            return props;
        }
        
        public Properties getResourceAsProperties(ClassLoader loader,
            String resource) throws IOException {
            Properties props = new Properties();
            InputStream in = null;
            String propfile = resource;
            in = getResourceAsStream(loader, propfile);
            props.load(in);
            in.close();
            
            return props;
        }
        
        public Reader getResourceAsReader(String resource) throws IOException {
            return new InputStreamReader(getResourceAsStream(resource));
        }
        
        public Reader getResourceAsReader(ClassLoader loader, String resource)
            throws IOException {
            return new InputStreamReader(getResourceAsStream(loader, resource));
        }
        
        public File getResourceAsFile(String resource) throws IOException {
            return new File(getResourceURL(resource).getFile());
        }
        
        public File getResourceAsFile(ClassLoader loader, String resource)
            throws IOException {
            return new File(getResourceURL(loader, resource).getFile());
        }
        
        public InputStream getUrlAsStream(String urlString) throws IOException {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            
            return conn.getInputStream();
        }
        
        public Reader getUrlAsReader(String urlString) throws IOException {
            return new InputStreamReader(getUrlAsStream(urlString));
        }
        
        public Properties getUrlAsProperties(String urlString)
            throws IOException {
            Properties props = new Properties();
            InputStream in = null;
            String propfile = urlString;
            in = getUrlAsStream(propfile);
            props.load(in);
            in.close();
            
            return props;
        }
        
        // ---
        public Class classForName(String className)
            throws ClassNotFoundException {
            Class clazz = null;
            try {
                clazz = getClassLoader().loadClass(className);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (clazz == null) {
                clazz = Class.forName(className);
            }
            
            return clazz;
        }
        
        public Object instantiate(String className)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException {
            return instantiate(classForName(className));
        }
        
        public Object instantiate(Class clazz)
            throws InstantiationException, IllegalAccessException {
            return clazz.newInstance();
        }
        
        // ---
        private ClassLoader getClassLoader() {
            if (this.defaultClassLoader != null) {
                return this.defaultClassLoader;
            } else {
                return Thread.currentThread().getContextClassLoader();
            }
        }
        
        public ClassLoader getDefaultClassLoader() {
            return this.defaultClassLoader;
        }
        
        public void setDefaultClassLoader(ClassLoader classLoader) {
            this.defaultClassLoader = classLoader;
        }
        // ---
    }
}
