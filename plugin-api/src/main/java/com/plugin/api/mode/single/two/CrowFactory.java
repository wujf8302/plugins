package com.plugin.api.mode.single.two;
/**
 * 
 * @author wujf
 */
public class CrowFactory   {   
    static Crow crow = new Crow();   
  
    public static Crow getInstance(){   
        return crow;   
    }   
} 
