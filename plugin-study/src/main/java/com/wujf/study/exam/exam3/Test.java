package com.wujf.study.exam.exam3;

public class Test {
	public String username="";
    public void setUser(String username){
    	this.username = username;
    }
	public class T2{
		public String getUser(){
			setUser("wujianfei");
			return username;
			
		}
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
		try {
			
		} catch (NullPointerException e) {
		
		} catch (Exception es) {
		
		}
	}
}
