package com.plugin.api.beans.sort;

/** 
* 要排序的元素对象 .
* @author wujf
*/ 
public class Cat implements Comparable{ 
    private int age; 
    private String name; 

    public Cat(int age, String name) { 
        this.age = age; 
        this.name = name; 
    } 

    public int getAge() { 
        return age; 
    } 

    public void setAge(int age) { 
        this.age = age; 
    } 

    public String getName() { 
        return name; 
    } 

    public void setName(String name) { 
        this.name = name; 
    } 


    public String toString() { 
        return "Cat{" + 
                "age=" + age + 
                ", name='" + name + '\'' + 
                '}'; 
    } 

    public int compareTo(Object o) { 
    	Cat cat = (Cat) o;
        return this.getAge() - cat.getAge(); 
    } 
}