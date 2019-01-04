package com.wujf.study.collection.old2;

import java.util.Stack;

/**
 * 一个测试 Stack 类 的例子. 测试各种 stack 中的各种方法; 
 * push()压栈 , pop()弹栈 , peek() , empty()是否为空, search()查位置位.
 * 
 * @author fj
 */
public class CTestStack1 {

	// 入口方法; 注"//"开始的注释不是文档化的注释,但也是必须,可增加程序的可读性;
	public static void main(String args[]) {

		Stack myStack = new Stack();

		myStack.push(new Integer(1));
		myStack.push(new Integer(2));
		myStack.push(new Integer(3));
		myStack.push(new Integer(4));
		myStack.push(new Integer(5));
		myStack.push(new Integer(6));

		System.out.println(myStack.search(new Integer(6)));

		System.out.println("**********************\n\n");
		while (!(myStack.empty())) {
			Integer temp = (Integer) myStack.pop();
			System.out.println("" + temp.toString());
		}
		System.out.println("\n\n**********************");
	}
}
