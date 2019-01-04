package com.plugin.api.mode.single.two;

public class CrowFactory   {   
    static Crow crow = new Crow();   
  
    public static Crow getInstance(){   
        return crow;   
    }   
} 
