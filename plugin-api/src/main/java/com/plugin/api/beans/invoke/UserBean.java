package com.plugin.api.beans.invoke;

public class UserBean {
	
	private String userid = "1";
	private String username = "aa";
	private String password = "3435";

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	public void setUserid(String userid,String id) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String writeOut(int i, String test) {
		String aa = "用户ID号：" + this.userid + "用户名:" + this.username + "密码:"+ this.password;
		aa = aa + "传进来的参数值:" + i + "Test为:" + test;
		return aa;
	}
	
	private String getTest(int i, String test) {
		String aa = "用户ID号：" + this.userid + "用户名:" + this.username + "密码:"+ this.password;
		aa = aa + "传进来的参数值:" + i + "Test为:" + test;
		return aa;
	}
}
