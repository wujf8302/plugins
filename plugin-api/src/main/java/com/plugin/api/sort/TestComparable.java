package com.plugin.api.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import com.plugin.api.beans.sort.Cat;

/** 
*1. 实现Comparable接口排序(Bean实现Comparable接口的compareTo方法)
 public int compareTo(Cat o) { 
        return this.getAge() - o.getAge(); 
 } 
 2. List<Cat> listCat = new ArrayList<Cat>();
 3. Collections.sort(list);
 @author wujf
*/ 
public class TestComparable { 

    public static String outCollection(Collection coll) { 
        StringBuffer sb = new StringBuffer(); 
        for (Object obj : coll) { 
            sb.append(obj + "\n"); 
        } 
        System.out.println(sb.toString()); 
        return sb.toString(); 
    } 

    public static void main(String args[]) { 
    	listSort();  //List集合的排序
        arraySort(); //数组的排序 
    } 

    public static void listSort() { 
        System.out.println("ArrayList<Object>排序:"); 
        List<Cat> listCat = new ArrayList<Cat>();
        Cat cat1 = new Cat(34, "hehe"); 
        Cat cat2 = new Cat(12, "haha"); 
        //Person catx = new Person(12, "lavasoft"); 
        Cat cat3 = new Cat(23, "leizhimin"); 
        Cat cat4 = new Cat(13, "lavasoft"); 

        listCat.add(cat1); 
        listCat.add(cat2); 
        listCat.add(cat3); 
        //listCat.add(catx); 

        System.out.println("原集合为:"); 
        outCollection(listCat); 
        System.out.println("=============================="); 
        System.out.println("调用Collections.sort(List<T> list)排序："); 
        Collections.sort(listCat);  //根据元素的自然顺序对指定列表按升序进行排序
        outCollection(listCat); 
        System.out.println("=============================="); 
        System.out.println("逆序排列元素:"); 
        Collections.sort(listCat, Collections.reverseOrder()); //根据元素的自然顺序对指定列表按逆序进行排序
        outCollection(listCat); 
        System.out.println("=============================="); 
        System.out.println("再次逆序排列元素:"); 
        Collections.reverse(listCat); 
        outCollection(listCat); 
        System.out.println("=============================="); 
        System.out.println("添加一个元素后输出集合："); 
        listCat.add(cat4); 
        outCollection(listCat); 
        System.out.println("=============================="); 
        System.out.println("排列后输出:"); 
        Collections.sort(listCat); 
        outCollection(listCat); 
    } 

    /** 
     * 数组的排序 
     */ 
    public static void arraySort(){ 
        String[] strArray = new String[] {"z", "a", "C"}; 
        System.out.println("-------------数组转换为列表-------------"); 
        List<String> list = Arrays.asList(strArray); 
        outCollection(list); 

        System.out.println("-------------列表转换为数组(1)-------------"); 
        String[] strArrayNew1 = list.toArray(strArray); 
        for(String str:strArrayNew1){ 
            System.out.println(str); 
        } 
        System.out.println("-------------列表转换为数组(2)-------------"); 
        String[] strArrayNew2 = (String[]) list.toArray(); 
        for(String str:strArrayNew2){ 
            System.out.println(str); 
        } 

        System.out.println("-------------顺序排序列表-------------"); 
        Collections.sort(list); 
        outCollection(list); 

        System.out.println("-----按String实现的Comparator对象String.CASE_INSENSITIVE_ORDER排序----"); 
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER); 
        outCollection(list); 

        System.out.println("-------------倒序排序列表-------------"); 
        Collections.sort(list, Collections.reverseOrder()); 
        outCollection(list); 

        System.out.println("-----按String实现的Comparator对象String.CASE_INSENSITIVE_ORDER排序----"); 
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER); 
        outCollection(list); 

        System.out.println("-----反转列表元素的顺序------"); 
        Collections.reverse(list); 
        outCollection(list); 
    } 
}