package com.plugin.api;
/**
 * 
 * @author wujf
 */
public class NnetworkProxy {

	public static void main(String[] args) {
		
		NnetworkProxy.proxy("true", "192.168.13.19", "7777");
		NnetworkProxy.socksProxy("true", "192.168.0.26", "1080");
		
	}
	
	//设置代理
    public static void proxy(String proxySet,String proxyHost,String proxyPort){ 
		System.getProperties().put("proxySet", proxySet);   
		System.getProperties().put("proxyHost", proxyHost);   
		System.getProperties().put("proxyPort", proxyPort);    
    }
    
    //设置socket代码  
    public static void socksProxy(String socksProxySet,String socksProxyHost,String socksProxyPort){
		System.getProperties().put("socksProxySet", socksProxySet);   
		System.getProperties().put("socksProxyHost", socksProxyHost);   
		System.getProperties().put("socksProxyPort", socksProxyPort);  
    }

}
