package com.plugin.api.mode.clone;
/**
1.为什么用super.clone()克隆出的不是Object对象而是当前对象？

原因：Object中的Clone()执行时使用了java的RTTI(运行时加载)机制，动态的找到正在调用clone()
方法的那个reference.根据它的大小申请内存空间，然后进行bitwise复制，将该对象的内存空间完全复制
到新空间中去，从而达到浅表复制（何谓浅表复制，如下）的目的。

2.浅表复制和深表复制
浅表复制：
父类与子类共用一个引用对象，如果父类包含的子引用对象发生改变，则这个变化也回同时出现在它的浅表复制的克隆对象中。

深表复制：
父类和子类分别拥有自己的子引用对象，子引用对象也是由父类的子引用对象复制过来的。即如果父类包含的子引用对象发生改变，
该改变不会出现在它的浅表复制的克隆对象中。

super.clone()实现的是浅表复制。 深层复制需要我们自己实现，可能需要编写复杂的代码。

5 总结：

a)浅层复制和深层复制的不同在于对象和克隆对象对引用变量的拥有不同。前者共同拥有，即复制的只是内存地址，
指向的堆中的同一个对象。后者分别拥有的引用变量，即克隆时同时克隆了引用，两个引用指向堆中的两个对象。

b)super.clone()实现的是浅层复制。

c)序列化可以实现深层复制。我们的写入到流里面的是对象的拷贝，原对象仍存在于JVM里面。Java中深克隆一个
对象，可以实现Serializable接口，然后把对象的拷贝写到流里，再从流里读回来，就可以重建对象。一般的变
量类型都实现了Serializable接口，如String/File文件等，我们自定义的对象在写入流里面时则需要实现序
列化，以保证网络传输的安全可靠。

还有一种深拷贝方法，就是将对象串行化
但是串行化却很耗时，在一些框架中，我们便可以感受到，它们往往将对象进行串行化后进行传递，耗时较多。
@author wujf
*/
//原型模式范例
public class CopyBean {
	
	public static void main(String[] args) {
		test1();
		System.out.println("浅复制(出现问题)--------------------------");
		test2();
		System.out.println("深复制----1----------------------");
		test3();
		System.out.println("深复制----2----------------------");
		test4();
	}
	
	 //深复制
	public static void test4() {
		Bean2 bean2 = new Bean2("wujf",80);
		Bean1 obj1 = new Bean1(1,2,bean2);
		Bean1 obj2 = (Bean1)obj1.deepClone();
		
		obj2.bean2.usreName = "lisi";
		obj2.bean2.age = 90;
		
		//obj2变了，但obj1也变了，证明obj1的bean2和obj2的bean2指向的是同一个对象。
		//这在我们有的实际需求中，却不是这样，因而我们需要深拷贝：
		System.out.println("obj1.bean2.usreName: " + obj1.bean2.usreName+" - obj2.bean2.usreName: " + obj2.bean2.usreName);
		System.out.println("obj1.bean2.age: " + obj1.bean2.age+" - obj2.bean2.age: " + obj2.bean2.age);
	}
	
	 //深复制
	public static void test3() {
		Bean2 bean2 = new Bean2("wujf",80);
		Bean1 obj1 = new Bean1(1,2,bean2);
		Bean1 obj2 = (Bean1)obj1.sclone();
		
		obj2.bean2.usreName = "lisi";
		obj2.bean2.age = 90;
		
		//obj2变了，但obj1也变了，证明obj1的bean2和obj2的bean2指向的是同一个对象。
		//这在我们有的实际需求中，却不是这样，因而我们需要深拷贝：
		System.out.println("obj1.bean2.usreName: " + obj1.bean2.usreName+" - obj2.bean2.usreName: " + obj2.bean2.usreName);
		System.out.println("obj1.bean2.age: " + obj1.bean2.age+" - obj2.bean2.age: " + obj2.bean2.age);
	}
	
	 //浅复制(出现问题)
	public static void test2() {
		Bean2 bean2 = new Bean2("wujf",80);
		Bean1 obj1 = new Bean1(1,2,bean2);
		Bean1 obj2 = (Bean1)obj1.qclone();
		
		obj2.bean2.usreName = "lisi";
		obj2.bean2.age = 90;
		
		//obj2变了，但obj1也变了，证明obj1的bean2和obj2的bean2指向的是同一个对象。
		//这在我们有的实际需求中，却不是这样，因而我们需要深拷贝：
		System.out.println("obj1.bean2.usreName: " + obj1.bean2.usreName+" - obj2.bean2.usreName: " + obj2.bean2.usreName);
		System.out.println("obj1.bean2.age: " + obj1.bean2.age+" - obj2.bean2.age: " + obj2.bean2.age);
	}
	
    //浅复制
	public static void test1() {
		Bean1 obj1 = new Bean1(1,2);

		Bean1 obj2 = (Bean1)obj1.qclone();
		obj2.x = 3;//成员变量　不变
		obj2.y = 4;//静态变量　变动
		
		System.out.println("obj1.x=" + obj1.x + "  obj1.y=" + obj1.y);
		System.out.println("obj2.x=" + obj2.x + "  obj2.y=" + obj2.y);
		
		obj1.x = 5;//成员变量　不变
		obj1.y = 6;//静态变量　变动
		
		System.out.println("obj1.x=" + obj1.x + "  obj1.y=" + obj1.y);
		System.out.println("obj2.x=" + obj2.x + "  obj2.y=" + obj2.y);
		
		obj2.x = 7;//成员变量　不变
		obj2.y = 8;//静态变量　变动
		
		System.out.println("obj1.x=" + obj1.x + "  obj1.y=" + obj1.y);
		System.out.println("obj2.x=" + obj2.x + "  obj2.y=" + obj2.y);
	}
}
