package com.plugin.api.beans.config;

import java.util.Properties;

public class Module {

	private Properties properties;
	private String ID;
	private String name;
	private String IP;
	private int port;

	public Module() {

	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String IP) {
		this.IP = IP;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getProperties(String key) {
		return properties.getProperty(key);
	}
}
