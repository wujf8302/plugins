package com.plugin.api.mode.decorate;
/**
 * 装饰者模式
 */
public class Main {   
  
    public static void main(String[] args) {   
  
        //咖啡的价格   
        Coffee coffee = new Coffee();   
        System.out.println(coffee.money());   
           
        //加糖咖啡的价格   
        Sugar s = new Sugar(coffee);   
        System.out.println(s.money());   
        
        //牛奶咖啡的价格   
        Milk m = new Milk(coffee);   
        System.out.println(m.money());   
           
        //牛奶加糖咖啡   
        Milk milk = new Milk(new Sugar(new Coffee()));   
        System.out.println(milk.money());   
    }   
  
}  
