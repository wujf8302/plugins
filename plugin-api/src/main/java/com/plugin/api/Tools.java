package com.plugin.api;

import java.io.File;
import org.apache.log4j.Logger;

public class Tools {
	
	private static Logger log = Logger.getLogger(Tools.class);
	
	/** 
	 * jdk6.0下的磁盘使用情况例子  
	 */  
	public static class Diskfree { 
		
	    public static void main(String[] args) {   
	        File[] roots = File.listRoots();//获取磁盘分区列表
	        for (int i = 0; i < roots.length; i++) {
	        	File file = roots[i];
	        	/*
        	    System.out.println(file.getPath()+"信息如下:");   
	            System.out.println("空闲未使用 = " + file.getFreeSpace()/1024/1024/1024+"G");//空闲空间   
	            System.out.println("已经使用 = " + file.getUsableSpace()/1024/1024/1024+"G");//可用空间   
	            System.out.println("总容量 = " + file.getTotalSpace()/1024/1024/1024+"G");//总空间   
	            System.out.println(); 
	            */
			}   
	    }   
	}
	
	public static class OperationUtil {
		/**
		 * 对一个已排序的数组，判断里面是否有两个唯一的元素，即这两个元素跟其他元素都不一样。
		 */
		public static class Search {
			public static void main(String[] args) {
				int[] a = { 3, 3, 3, 4, 4, 5, 6, 6, 6, 7, 7, 8, 8, 8, 9 };

				search(a);
				search2(a);
			}
			// 好理解，但比较次数多
			public static void search(int[] a) {
				int count = 0;

				if (a[0] != a[1])
					count++;
				if (a[a.length - 2] != a[a.length - 1])
					count++;
				if (count < 2) {
					for (int i = 1; i < a.length - 1; i++) {
						if (a[i] != a[i - 1] && a[i] != a[i + 1]) {
							count++;
							if (count == 2)
								break;
						}
					}
				}
				if (count >= 2)
					System.out.println("Find!");
				else
					System.out.println("Not find!");
			}
			// 比较次数少一点
			public static void search2(int[] a) {
				int count = 0;

				for (int i = 0; i < a.length - 1;) {
					if (a[i] != a[i + 1]) {
						if (i > 0 && a[i] != a[i - 1])
							count++;
						else if (i == 0)
							count++;

						if (i < a.length - 2 && a[i + 1] != a[i + 2])
							count++;
						else if (i == a.length - 2)
							count++;

						if (count == 2)
							break;
					}

					i = i + 2;
				}
				// 数组a有奇数个元素时，比较最后两个元素是否相同
				if (a.length % 2 != 0 && a[a.length - 1] != a[a.length - 2])
					count++;
				if (count >= 2)
					System.out.println("Find!");
				else
					System.out.println("Not find!");
			}
		}
	}
}
