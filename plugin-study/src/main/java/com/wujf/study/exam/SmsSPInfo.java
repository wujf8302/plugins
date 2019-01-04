package com.wujf.study.exam;

import java.util.ResourceBundle;

public class SmsSPInfo {
    public static String USERNAME;
    public static String PASSWORD;
    public static String MTURL;
    
    public static void loadConfigure() {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("xmsmsconfig");
            
            USERNAME = bundle.getString("userName");
            PASSWORD = bundle.getString("password");
            MTURL = bundle.getString("MTUrl");
        } catch (Exception e) {
            System.err.println("获取配置信息异常"+e.getMessage());
            USERNAME = "fzkr";
            PASSWORD = "0591kairui";
            MTURL = "http://218.5.72.112:8080/biz/index.jsp";
        }
    }
}
