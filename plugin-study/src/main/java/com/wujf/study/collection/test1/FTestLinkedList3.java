package com.wujf.study.collection.test1;

import java.util.LinkedList;

/**
* 一个测试 LinkedList 类 的例子. 练习LinkedList类中方法的使用;
*
* @author fj
*/
public class FTestLinkedList3{
	
	//入口方法; 注"//"开始的注释不是文档化的注释,但也是必须,可增加程序的可读性;
	@SuppressWarnings("unchecked")
	public static void main(String args[]){
		
		System.out.println("**********************\n\n");

		LinkedList myList=new LinkedList(); //创建链表对象
		
		myList.add("is");	//在键表最后添加节点
		myList.add("a");
		int number=myList.size(); //获取链表中节点总个数
		System.out.println("现在键表中有"+number+"个节点");
		for(int i=0;i<number;i++){
			String temp=(String)myList.get(i);
			System.out.println("第"+i+"节点中的数据:"+temp);
		}
		
		System.out.println("\n");
		
		myList.addFirst("It"); //添节点到表头
		myList.addLast("door");//添节点到末尾
		number=myList.size();
		System.out.println("现在键表中有"+number+"个节点");
		for(int i=0;i<number;i++){
			String temp=(String)myList.get(i);
			System.out.println("第"+i+"节点中的数据:"+temp);
		}
		
		System.out.println("\n");
		
		myList.remove(0); //删除第一节点
		myList.remove(1); //删除第二节点
		myList.set(0,"open");
		number=myList.size();
		System.out.println("现在键表中有"+number+"个节点");
		for(int i=0;i<number;i++){
			String temp=(String)myList.get(i);
			System.out.println("第"+i+"节点中的数据:"+temp);
		}
		
		
		
		System.out.println("\n\n**********************");
	}
	
}
