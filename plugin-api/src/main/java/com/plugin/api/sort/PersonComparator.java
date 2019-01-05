package com.plugin.api.sort;

import java.util.Comparator; 

import com.plugin.api.beans.sort.Person;

/** 
* Person类的排序接口.
* @author wujf
*/ 
public class PersonComparator implements Comparator{ 
    /** 
     * 排序接口算法实现 
     * @return 比较结果的大小 
     */ 
    public int compare(Object o1, Object o2) {
    	Person person1 = (Person)o1;
    	Person person2 = (Person)o2;
    	int temp = person1.getAge() - person2.getAge();
    	if (temp == 0){
			temp = 1;
    	}
		return temp;
    } 
}
