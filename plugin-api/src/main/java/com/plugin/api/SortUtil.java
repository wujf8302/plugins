package com.plugin.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.apache.log4j.Logger;

public class SortUtil {
    
    private static Logger log = Logger.getLogger(SortUtil.class);
	
	public static List<String> sort(List<String> list,String regex,int sortIndex){

		if(list != null && list.size() > 0){
			for (int i = 0; i < list.size() - 1; i++) {
				for (int j = 0; j < list.size() - i - 1; j++) {// 比较两个整数
	                Object o1 = list.get(j);
	                Object o2 = list.get(j + 1);
	                if(o1 != null && o2 != null){
	                	if(o1 instanceof String && o2 instanceof String){
	                		String v1 = (String)o1;
	                		String v2 = (String)o2;
	                		
	                		boolean b = true;
	                		if(StringUtil.isNotEmpty(v1) && StringUtil.isNotEmpty(v2)){
		                		String[] cells1 = v1.split(regex);//列值
		                		String[] cells2 = v2.split(regex);//列值
		                		
		                		if(sortIndex <= (cells1.length-1)){
		                			if(cells1 != null && cells1 != null && cells1.length == cells2.length){
			                			String c1 = cells1[sortIndex];
			                			String c2 = cells2[sortIndex];
			                			
			                			if(StringUtil.isNotEmpty(c1) && StringUtil.isNotEmpty(c2)){
			                				if(StringUtil.isDigit(c1) && StringUtil.isDigit(c2)){//是数字【数字或浮点】
					                			try {
					                				Double d1 = Double.parseDouble(c1);
							                		Double d2 = Double.parseDouble(c2);
							                		if (DoubleUtil.compareTo(d1, d2) > 0) {
							                			String temp = v1;
							        					list.set(j, v2);
							        					list.set(j + 1, temp);
							        				}
												} catch (Exception e) {
													log.error("",e);
												}
												b = false;
					                		}
			                			}
			                		}
		                		}
	                		}
	                		
	                		if(b){
	                			if (v1.compareTo(v2) > 0) {
		                			String temp = v1;
		        					list.set(j, v2);
		        					list.set(j + 1, temp);
		        				}
	                		}
	                	}
	                }
				}
			}
		}
		return list;
	}
	
    //-------------------------------------------------------
	
	/**
	 * List集合排序.
	 * 排序输出：小到大
	 * Short Integer Long Float Double String
	 */
	public static List sort(List list){
		if(list != null && list.size() > 0){
			for (int i = 0; i < list.size() - 1; i++) {
				for (int j = 0; j < list.size() - i - 1; j++) {// 比较两个整数
	                Object o1 = list.get(j);
	                Object o2 = list.get(j + 1);
	                if(o1 != null && o2 != null){
	                	if(o1 instanceof Short && o2 instanceof Short){
	                		Short v1 = (Short)o1;
	                		Short v2 = (Short)o2;
	                		if (v1 > v2) {
	                			Short temp = v1;
	        					list.set(j, v2);
	        					list.set(j + 1, temp);
	        				}
	                	}else if(o1 instanceof Integer && o2 instanceof Integer){
	                		Integer v1 = (Integer)o1;
	                		Integer v2 = (Integer)o2;
	                		if (v1 > v2) {
	        					Integer temp = v1;
	        					list.set(j, v2);
	        					list.set(j + 1, temp);
	        				}
	                	}else if(o1 instanceof Long && o2 instanceof Long){
	                		Long v1 = (Long)o1;
	                		Long v2 = (Long)o2;
	                		if (v1 > v2) {
	                			Long temp = v1;
	        					list.set(j, v2);
	        					list.set(j + 1, temp);
	        				}
	                	}else if(o1 instanceof Double && o2 instanceof Double){
	                		Double v1 = (Double)o1;
	                		Double v2 = (Double)o2;
	                		if (DoubleUtil.compareTo(v1, v2) > 0) {
	                			Double temp = v1;
	        					list.set(j, v2);
	        					list.set(j + 1, temp);
	        				}
	                	}else if(o1 instanceof Float && o2 instanceof Float){
	                		Float v1 = (Float)o1;
	                		Float v2 = (Float)o2;
	                		if (DoubleUtil.compareTo(v1, v2) > 0) {
	                			Float temp = v1;
	        					list.set(j, v2);
	        					list.set(j + 1, temp);
	        				}
	                	}else if(o1 instanceof String && o2 instanceof String){
	                		String v1 = (String)o1;
	                		String v2 = (String)o2;
	                		boolean b = true;
	                		if(StringUtil.isNotEmpty(v1) && StringUtil.isNotEmpty(v2)){
	                			if(StringUtil.isDigit(v1) && StringUtil.isDigit(v2)){//是数字【数字或浮点】
		                			try {
		                				Double d1 = Double.parseDouble(v1);
				                		Double d2 = Double.parseDouble(v2);
				                		if (DoubleUtil.compareTo(d1, d2) > 0) {
				                			String temp = v1;
				        					list.set(j, v2);
				        					list.set(j + 1, temp);
				        				}
									} catch (Exception e) {
										log.error("",e);
									}
									b = false;
		                		}
	                		}
	                		
	                		if(b){
	                			if (v1.compareTo(v2) > 0) {
		                			String temp = v1;
		        					list.set(j, v2);
		        					list.set(j + 1, temp);
		        				}
	                		}
	                	}
	                }
				}
			}
		}
		return list;
	}
	
    //-------------------------------------------------------
	
	public static List reverse(List list){
		if(list != null && list.size() > 0){
			Collections.reverse(list);
		}
		return list;
	}
    public static List sort(List list, Comparator comparator) {
    	if(list != null && list.size() > 0){
    		Collections.sort(list, comparator);
		}
		return list;
    }
    
    //-------------------------------------------------------
    
	public static void print(List<?> list){
		System.out.println("排序后的结果是：");
		if(list != null && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				Object o = list.get(i);
				if(o != null){
					if(o instanceof Short){
						Short v = (Short)o;
						System.out.println(v);
					}else if(o instanceof Integer){
						Integer v = (Integer)o;
						System.out.println(v);
					}else if(o instanceof Long){
						Long v = (Long)o;
						System.out.println(v);
						
					}else if(o instanceof Double){
						Double v = (Double)o;
						System.out.println(v);
					}else if(o instanceof Float){
						Float v = (Float)o;
						System.out.println(v);
						
					}else if(o instanceof String){
						String v = (String)o;
						System.out.println(v);
					}
				}
				
			}
		}
	}
	
    //-------------------------------------------------------
    /**
     * 数组排序.
     */
    public void sort(int[] data) {
        int temp;
        for (int i = 0; i < data.length; i++) {
            for (int j = data.length - 1; j > i; j--) {
                if (data[i] > data[j]) {
                    temp = data[i];
                    data[i] = data[j];
                    data[j] = temp;
                }
            }
        }
    }
    public void sort(int[] data, String type) { // 定义两个参数,数组首地址与数组大小
        int temp;
        if ("asc".equals(type)) {
            for (int i = 0; i < data.length; i++) { // 冒泡,降序:
                for (int j = data.length - 1; j > i; j--) {
                    if (data[i] > data[j]) { // 每个数都比较n次,如果data[i]>data[j]成立,则交换两个数
                        temp = data[i];
                        data[i] = data[j];
                        data[j] = temp;
                    }
                }
            }
        } else if ("desc".equals(type)) {
            for (int i = 0; i < data.length; i++) { // 冒泡,降序:
                for (int j = data.length - 1; j > i; j--) {
                    if (data[i] < data[j]) { // 每个数都比较n次,如果data[i]>data[j]成立,则交换两个数
                        temp = data[i];
                        data[i] = data[j];
                        data[j] = temp;
                    }
                }
            }
        }
    }
    public void sort(List<Integer> data, String type) { // 定义两个参数,数组首地址与数组大小
        Integer temp;
        if ("asc".equals(type)) {
            for (int i = 0; i < data.size(); i++) { // 冒泡,降序:
                for (int j = data.size() - 1; j > i; j--) {
                    if (data.get(i) > data.get(j)) { // 每个数都比较n次,如果data[i]>data[j]成立,则交换两个数
                        temp = data.get(i);
                        data.set(i, data.get(j));
                        data.set(j, temp);
                    }
                }
            }
        } else if ("desc".equals(type)) {
            for (int i = 0; i < data.size(); i++) { // 冒泡,降序:
                for (int j = data.size() - 1; j > i; j--) {
                    if (data.get(i) < data.get(j)) { // 每个数都比较n次,如果data[i]>data[j]成立,则交换两个数
                        temp = data.get(i);
                        data.set(i, data.get(j));
                        data.set(j, temp);
                    }
                }
            }
        }
    }
    
    //-------------------------------------------------------
    
    /**
     * 集合排序
     */
    public static class SortObjectUtil {
        
        private static Logger log = Logger.getLogger(SortObjectUtil.class);
        
        public static void main(String[] args) {
            List list = new ArrayList();
            
            SortObjectUtil.sort(list); // 第一种方式排序方法
            
            Collections.sort(list, new aaa()); // 第二种方式排序方法
            SortObjectUtil.sort(list, new Comparator() { // 第二种方式排序方法
                    public int compare(Object o1, Object o2) {
                        Student s1 = (Student) o1;
                        Student s2 = (Student) o2;
                        return s1.english - s2.english;
                    }
                });
            
            // Set myTreeSet = new TreeSet(new aaa()); //第三种方式排序方法
            Set myTreeSet = new TreeSet(new Comparator() { // 第三种方式排序方法
                    public int compare(Object o1, Object o2) {
                        Student s1 = (Student) o1;
                        Student s2 = (Student) o2;
                        return s1.english - s2.english;
                    }
                });
            
            Student st1, st2, st3, st4;
            st1 = new Student(90, "zhan ying");
            st2 = new Student(66, "wang heng");
            st3 = new Student(86, "Liu qing");
            st4 = new Student(76, "yage ming");
            
            Student st5 = new Student(86, "wang");
            
            myTreeSet.add(st1); // 将对象加入集合对象set中
            myTreeSet.add(st2);
            myTreeSet.add(st3);
            myTreeSet.add(st4);
            
            myTreeSet.add(st5);
            
            Iterator it = myTreeSet.iterator(); // 用迭代器遍历输出
            while (it.hasNext()) {
                Student st = (Student) it.next();
                System.out.println("" + st.name + "    " + st.english);
            }
        }
        
        /**
         * 对集合进行排序
         */
        public static void sort(List list) {
            Collections.sort(list);
        }
        
        /**
         * 对集合进行排序
         */
        public static void sort(List list, Comparator comparator) {
            Collections.sort(list, comparator);
        }
        
        /**
         * 反向排序
         */
        public static void reverse(List list) {
            Collections.reverse(list);
        }
        
        // 实现接口 Comparable 中的 compareTo 方法
        public static class Student implements Comparable {
            private int    english;
            private String name;
            
            public Student() {
                
            }
            
            public Student(int english, String name) {
                this.english = english;
                this.name = name;
            }
            
            public int compareTo(Object b) {
                Student st = (Student) b;
                int temp = this.english - st.english;
                if (temp == 0)
                    temp = 1;
                return temp;
            }
        }
        
        // 实现接口 Comparator 中的 compare 方法
        public static class aaa implements Comparator {
            public int compare(Object o1, Object o2) {
                Student s1 = (Student) o1;
                Student s2 = (Student) o2;
                int temp = s1.english - s2.english;
                if (temp == 0)
                    temp = 1;
                return temp;
            }
        }
    }
    
    //-------------------------------------------------------
    
    /**
     * 利用二分比较法, 快速找到元素插入位置
     */
    public static class Test {
        
        private int getBisearchElement(double dou[], int size) {
            if (size == 0) {
                size = dou.length;
            }
            if (size == 1) {
                return 0;
            }
            return size / 2;
        }
        
        private double getDouble() throws IOException {
            InputStreamReader reader = new InputStreamReader(System.in);
            BufferedReader input = new BufferedReader(reader);
            String s = input.readLine();
            return Double.parseDouble(s);
        }
        
        /**
         * 比如:
         * d1=0.12951463 d2=0.08425 1
         * d1=0.12951463 d2=0.2516 -1
         */
        private int compare(double d1, double d2) {
            return Double.compare(d1, d2);
        }
        
        private double getDoubleElement(double dou1[], int size) {
            return dou1[size];
        }
        
        /**
         * 作用:利用二分比较法,快速找到元素插入位置
         * 参数:dou1 数组;d2 插入元素;startlocation 前次数组endlocation下标;endlocation
         * 数组endlocation下标
         * 具体说明:第一步：判断两个Double元素哪个比较大,结果为1或者-1.
         * 首先分析结果为1的情况:
         * 无非就是挨靠与无挨靠的问题.举例说明数组[0.05550627,0.065414764,0.07849772...],0.05550627
         * ,0.065414764
         * 是挨靠,0.05550627和0.065414764是无挨靠.概念清楚了,那么根据什么知道它们有无挨靠？
         * 依据就是形参startlocation
         * 和endlocation.如果是无挨靠,那么就要进行如下公式：当前数组(endlocation)下标-前次数组(endlocation)
         * 下标=插入元素
         * 适合的范围;插入元素适合的范围/2=从适合的范围的中间开始.
         */
        private int compare(double dou1[], double d2, int startlocation,
            int endlocation) {
            int size = 0;
            double d1 = getDoubleElement(dou1, endlocation);
            int icomparser = compare(d1, d2);
            if (icomparser == 1) {// d1大,d2属于左边 第一步
                if (startlocation == endlocation - 1) {// 它们俩是压着
                    if (startlocation == 0) {
                        if (compare(getDoubleElement(dou1, startlocation), d2) == 1) {
                            size = 0;
                        } else {
                            size = 1;
                        }
                    } else {
                        size = endlocation;
                    }
                    this.startlocation = endlocation;
                } else {
                    int newsize = endlocation - startlocation;
                    newsize = newsize / 2;
                    size = newsize + startlocation;
                }
            } else if (icomparser == -1) {// d1小,d2属于右边 第一步
                int newsize = dou1.length - endlocation;
                if (newsize == 1) {// 等于1,说明已经到了最后一个元素
                    if (compare(getDoubleElement(dou1, endlocation), d2) == 1) {
                        size = endlocation + 1;
                    } else {
                        size = endlocation;
                    }
                } else {
                    newsize = newsize / 2;
                }
                size = newsize + endlocation;
                this.startlocation = endlocation;
            } else if (icomparser == 0) {
                size = endlocation;
                this.startlocation = endlocation;
            }
            return size;
        }
        
        // 插入位置
        public int location(Double d2) throws IOException {
            int dou1length = this.dou1length - 1;
            endlocation = getBisearchElement(dou1, 0);
            while (endlocation <= dou1length) {
                endlocation = compare(dou1, d2, startlocation, endlocation);
                if (startlocation == endlocation || endlocation == 0) {
                    break;
                }
            }
            return endlocation;
        }
        
        // 插入位置
        public void insertlocation(int location, double d1) throws Exception {
            if (location == dou1length) {
                int i = size;
                int length = location - i;
                while (i >= 2) {
                    dou1[length] = dou1[length + 1];
                    i--;
                    length = location - i;
                }
                dou1[location - 1] = d1;
                
            } else {
                int i = location;
                int length = location - i;
                int ilength = location - 1;
                while (length != ilength) {
                    dou1[length] = dou1[length + 1];
                    i--;
                    length = location - i;
                }
                dou1[location - 1] = d1;
            }
        }
        
        private static int    startlocation = 0, endlocation = 0;
        private static int    size          = 6;
        private static double dou1[]        = new double[size];
        
        private static int    dou1length    = 0;
        
        static {
            dou1length = dou1.length;
        }
        
        public static void main(String[] args) throws Exception {
            Test test = new Test();
            double d2 = test.getDouble();
            int location = test.location(d2);
            test.insertlocation(location, d2);
            startlocation = 0;
            endlocation = 0;
            d2 = test.getDouble();
            location = test.location(d2);
            test.insertlocation(location, d2);
            startlocation = 0;
            endlocation = 0;
            d2 = test.getDouble();
            location = test.location(d2);
            test.insertlocation(location, d2);
            startlocation = 0;
            endlocation = 0;
            d2 = test.getDouble();
            location = test.location(d2);
            test.insertlocation(location, d2);
            startlocation = 0;
            endlocation = 0;
            d2 = test.getDouble();
            location = test.location(d2);
            test.insertlocation(location, d2);
            startlocation = 0;
            endlocation = 0;
            for (int i = 0; i < dou1length; i++) {
                System.out.print(dou1[i] + ",");
            }
        }
    }
}
