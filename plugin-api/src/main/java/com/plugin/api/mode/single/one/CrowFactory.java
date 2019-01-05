package com.plugin.api.mode.single.one;
/**
 * 
 * @author wujf
 */
public class CrowFactory   {   
    static Crow crow = null;   
  
    public static Crow getInstance(){   
        if(crow == null){   
            crow = new Crow();   
        }   
        return crow;   
    }   
} 
