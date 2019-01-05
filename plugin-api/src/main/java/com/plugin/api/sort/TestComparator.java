package com.plugin.api.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.plugin.api.beans.sort.Person;
/** 
* 通过Comparator接口实现个性化排序测试 
* 结论:Comparator接口是一个为集合对象排序的基本算法,其中的compare方法是比较两个元素对象的比较方式.
* Java Collection框架利用这个算法实现了不同集合类型对象排序方式的统一.
* 排序针对的是确切的集合对象,当集合对象的元素发生变化时,集合内的元素不会自动重新排序. 
*/ 
/**
 * 1.写一个类实现Comparator接口的Comparator方法类
  public int compare(Person o1, Person o2) { 
        return o1.getAge() - o2.getAge(); 
  } 
  2.Set treeSetTest = new TreeSet(new UserSort());
  3.往集合中加数据
  @author wujf
 */

//List有序 set无序
public class TestComparator {
	
    public static void main(String args[]) { 
        test1(); //调用
    } 

    public static String outCollection(Collection coll) { 
        StringBuffer sb = new StringBuffer(); 
        for (Object obj : coll) { 
            sb.append(obj + "\n"); 
        } 
        System.out.println(sb.toString()); 
        return sb.toString(); 
    } 


    public static void test1() { 
        System.out.println("升序排序测试:"); 
        List<Person> listPerson = new ArrayList<Person>(); 
        Person person1 = new Person(34, "lavasoft"); 
        Person person2 = new Person(12, "lavasoft"); 
        //Person personx = new Person(12, "lavasoft"); 
        Person person3 = new Person(23, "leizhimin"); 
        Person person4 = new Person(13, "sdg"); 

        listPerson.add(person1); 
        listPerson.add(person2); 
        listPerson.add(person3); 
        //listPerson.add(personx); 

        Comparator<Person> ascComparator = new PersonComparator(); 

        System.out.println("原集合为:"); 
        outCollection(listPerson); 
        System.out.println("========================");

        System.out.println("排序后集合为:"); 
        //利用Collections类静态工具方法对集合List进行排序 
        Collections.sort(listPerson, ascComparator); 

        outCollection(listPerson); 

        System.out.println("在继续添加一个Person对象,集合为:"); 
        listPerson.add(person4); 
        outCollection(listPerson); 

        System.out.println("添加一个对象后,重新排序输出:"); 
        Collections.sort(listPerson, ascComparator); 
        outCollection(listPerson); 

        System.out.println("\n降序排序测试:"); 
        //从升序排序对象产生一个反转(降序)的排序对象 
        Comparator<Person> descComparator = Collections.reverseOrder(ascComparator); 
        System.out.println("利用反转后的排序接口对象对集合List排序并输出:"); 
        Collections.sort(listPerson, descComparator); 
        outCollection(listPerson); 
        System.out.println("========================");
        
        System.out.println("求最大最小元素测试:"); 
        Person p_max = Collections.max(listPerson, ascComparator); 
        Person p_min = Collections.min(listPerson, ascComparator); 
        System.out.println("最大元素为:" + p_max.toString()); 
        System.out.println("最小元素为:" + p_min.toString()); 
    } 
}

