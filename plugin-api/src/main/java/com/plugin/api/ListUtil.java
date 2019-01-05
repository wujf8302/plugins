package com.plugin.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;
/**
 * 
 * @author wujf
 */
public class ListUtil extends ListUtils{
	
	private static Logger log = Logger.getLogger(ListUtil.class);
	
	public static void println(List list){
		if(list != null && list.size() > 0){
			 for (int i = 0; i < list.size(); i++) {
				Object o = list.get(i);
				System.out.println(o);
			}
		}
	}
	
	/**
	 * 判断集合中是否包含key.
	 * true包含
	 * 除了String已经验证，其他类型未经验证
	 */
	public static boolean exist(List list,String key){
		boolean b = false;
		if(list != null && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				Object o = list.get(i);
				if(o != null){
					if(o instanceof String){
						String temp = (String)o;
						if(temp.indexOf(key)!= -1){
							b = true;
							break;
						}
					}else if(o instanceof Short){
						String temp = String.valueOf(o);
						if(temp.equals(key)){
							b = true;
							break;
						}
						
					}else if(o instanceof Integer){
						String temp = String.valueOf(o);
						if(temp.equals(key)){
							b = true;
							break;
						}
						
					}else if(o instanceof Long){
						String temp = String.valueOf(o);
						if(temp.equals(key)){
							b = true;
							break;
						}
						
					}else if(o instanceof Float){
						String temp = String.valueOf(o);
						if(temp.equals(key)){
							b = true;
							break;
						}
						
					}else if(o instanceof Double){
						String temp = String.valueOf(o);
						if(temp.equals(key)){
							b = true;
							break;
						}
					}
				}
			}
		}
		return b;
	}
	
	public static List<Object> map2List(Map map){
		List<Object> list = new ArrayList<Object>();
		if(map != null && map.size() > 0){
			Set keySet = map.keySet();
			for (Iterator it = keySet.iterator(); it.hasNext();) {
				String key = (String) it.next();
				list.add(map.get(key));
			}
		}
		return list;
	}
	public static List<Object> json2List(JSONObject json){
		List<Object> list = new ArrayList<Object>();
		if(json!= null && !json.isEmpty()){
			Set keySet = json.keySet();
			for (Iterator it = keySet.iterator(); it.hasNext();) {
				String key = (String) it.next();
				list.add(json.get(key));
			}
		}

		return list;
	}
	public static Object[] json2Array(JSONObject json){
		Object[] os = null;
		if(json!= null && !json.isEmpty()){
			os = new Object[json.size()];
			Set keySet = json.keySet();
			int i = 0;
			for (Iterator it = keySet.iterator(); it.hasNext();) {
				String key = (String) it.next();
				os[i++] = json.get(key);
			}
		}

		return os;
	}
	
    /**
     * 分组.
     */
    public static Map group(List list){
        Map newMap = null;
        if(list != null && list.size() > 0){
            newMap = new HashMap();
            for (Iterator it1 = list.iterator(); it1.hasNext();) {
                Map map = (Map) it1.next();
                if(map != null && map.size() > 0){
                    Set set = map.keySet();
                    for (Iterator it2 = set.iterator(); it2.hasNext();) {
                        String key = (String) it2.next();
                        List tempList = null;
                        if(newMap.get(key) != null){
                            tempList = (ArrayList)newMap.get(key);
                            tempList.add(map);
                        }else{
                            tempList = new ArrayList();
                            tempList.add(map);
                        }
                        newMap.put(key, tempList);
                    }
                }
            }
        }
        return newMap;
    }
	
	/**
	 * 计算和.
	 * <Double><Float><Long><Integer><Short>
	 */
	public static Object sum(List list){
		Object all = null;
		if(list != null && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
                Object val = list.get(i);
                if (val instanceof Short) {
                	if(i == 0){
                		all = new Short((short) 0);
                	}
                	short value =(Short)val;
                	short total =(Short)all;
                	all = total + value;
                	
	            } else if (val instanceof Integer) {
	            	if(i == 0){
                		all = new Integer((short) 0);
                	}
                	int value =(Integer)val;
                	int total =(Integer)all;
                	all = total + value;
	            	
	            } else if (val instanceof Long) {
	            	if(i == 0){
                		all = new Long((short) 0);
                	}
                	long value =(Long)val;
                	long total =(Long)all;
                	all = total + value;
	                
	            } else if (val instanceof Float) {
	            	if(i == 0){
                		all = new Float((short) 0);
                	}
	            	float value =(Float)val;
                	float total =(Float)all;
                	all = DoubleUtil.add(total, value);
	            	
	            } else if (val instanceof Double) {
	            	if(i == 0){
                		all = new Double((short) 0);
                	}
                	double value =(Double)val;
                	double total =(Double)all;
                	all = DoubleUtil.add(total, value);
	             
	            } else {
	                System.err.println("未知数据类型的相加");
	                return null;
	            }
			}
		}
		return all;
	}
	
	public static Object max(List list) {//最大
		return Collections.max(list);
	}
	
	public static Object min(List list) {//最大
		return Collections.min(list);
	}
	
	public static List<String> distinct(StringBuffer sb) {//去重
		String vals = sb.toString();
		return distinct(vals.split(","));
	}
	public static List<String> distinct(String vals) {//去重
		return distinct(vals.split(","));
	}
	public static List<String> distinct(String[] vals) {//去重
		return distinct(stringArray2List(vals));
	}
	public static List<String> distinct(List<String> list) {//去重
		return distinct(list,2);
	}
	public final static String DOUHAO = "@逗号@";
	public static List<String> distinct(List<String> list,int type) {//去重
		return distinct(list,type,null,null);
	}
	public static List<String> distinct(List<String> list,int type,String rs,String ts) {//去重
		List<String> listTemp = new ArrayList<String>();
		Iterator<String> it = list.iterator();
		boolean b = false;
		
		while (it.hasNext()) {
			String a = it.next();
			if(type == 1){
				if (listTemp.contains(a)) {
					it.remove();
				} else {
					listTemp.add(a);
				}
			}else{
				String[] as = a.split(",");
				if(as.length == 1){
					if (listTemp.contains(a)) {
						it.remove();
					} else {
						listTemp.add(a);
					}
				}else{
					it.remove();
					b = true;
					for (int i = 0; i < as.length; i++) {
						if (!listTemp.contains(as[i])) {
							listTemp.add(as[i]);
						}
					}
				}
			}
		}
		if(b){
			return listTemp;
		}
		return list;
	}
	
	public static List<String> replace(List<String> list,String oldChar,String newChar) {
		List<String> listTemp = new ArrayList<String>();
		if(list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String row = list.get(i);
				if(row != null && !"".equals(row.trim())) {
					row = row.replaceAll(oldChar, newChar);
					listTemp.add(row);
				}
			}
		}
		return listTemp;
	}
	
	public static void main(String[] args) {
		String[] ss = new String[]{"w1","s2","r4"};
		List list1 = array2List(ss);
		System.out.println(list1);
		
		Integer[] ii = new Integer[]{3,5,8};
		List list2 = array2List(ii);
		System.out.println(list2);
	}
	
	//---
	
	public static List array2List(Object vals){
		 List list = new ArrayList();
		 if(vals != null){
			 if(vals instanceof String[]){
				 String[] ss = (String[])vals;
				 for (int i = 0; i < ss.length; i++) {
					 list.add(ss[i]);
				 }
			 }else if(vals instanceof Short[]){
				 Short[] ss = (Short[])vals;
				 for (int i = 0; i < ss.length; i++) {
					 list.add(ss[i]);
				 }
			 }else if(vals instanceof Integer[]){
				 Integer[] ss = (Integer[])vals;
				 for (int i = 0; i < ss.length; i++) {
					 list.add(ss[i]);
				 }
			 }else if(vals instanceof Long[]){
				 Long[] ss = (Long[])vals;
				 for (int i = 0; i < ss.length; i++) {
					 list.add(ss[i]);
				 }
			 }else if(vals instanceof Double[]){
				 Double[] ss = (Double[])vals;
				 for (int i = 0; i < ss.length; i++) {
					 list.add(ss[i]);
				 }
			 }else if(vals instanceof Float[]){
				 Float[] ss = (Float[])vals;
				 for (int i = 0; i < ss.length; i++) {
					 list.add(ss[i]);
				 }
			 }
		 }
		 return list;
	 }
	 
	 public static Object list2Array(List dataList){
		 String dateFormate = DateUtil.DEFAULT_DATE_FORMATE;
		 String doubleFormate = DoubleUtil.DEFAULT_FORMATE1;
		 return list2Array(dataList,dateFormate,doubleFormate);
	 }
	 public static String[] list2Array(List dataList,String dateFormate,String doubleFormate){
    	String[] array = null;
    	if(dataList != null && dataList.size() > 0){
    		array = new String[dataList.size()];
    		for (int i = 0; i < dataList.size(); i++) {
    			array[i] = getValue(dataList.get(i),dateFormate,doubleFormate);
			}
    	}
    	return array;
    }
	public static String getValue(Object val,String dateFormate,String doubleFormate){
		if(StringUtil.isEmpty(dateFormate)){
			dateFormate = DateUtil.DEFAULT_DATETIME_FORMATE;
		}
		if(StringUtil.isEmpty(doubleFormate)){
			doubleFormate = DoubleUtil.DEFAULT_FORMATE1;
		}
        if(val != null && !"null".equals(val.toString())){
        	if (val instanceof Short) {
                return String.valueOf(((Short)val).shortValue());
            } else if (val instanceof Integer) {
                return String.valueOf(((Integer)val).intValue());
            } else if (val instanceof Long) {
                return String.valueOf(((Long)val).longValue());
                
            } else if (val instanceof Float) {
            	return String.valueOf(DoubleUtil.formatDouble(((Float)val).doubleValue(), doubleFormate));
            } else if (val instanceof Double) {
                return String.valueOf(DoubleUtil.formatDouble(((Double)val).doubleValue(), doubleFormate));
           
            } else if (val instanceof Boolean) {
                return String.valueOf(((Boolean)val).booleanValue());
                
            } else if (val instanceof String) {
                return String.valueOf((String)val);
            } else if (val instanceof StringBuffer) {
                return ((StringBuffer)val).toString();
                
            } else if (val instanceof Date) {
            	String temp = "";
            	try {
            		temp = String.valueOf(DateUtil.formatDate((Date)val,dateFormate));
				} catch (Exception e1) {
					try {
						dateFormate = DateUtil.DEFAULT_DATE_FORMATE;
						temp = String.valueOf(DateUtil.formatDate((Date)val,dateFormate));
					} catch (Exception e2) {
						try {
							dateFormate = DateUtil.DEFAULT_TIME_FORMATE;
							temp = String.valueOf(DateUtil.formatDate((Date)val,dateFormate));
						} catch (Exception e3) {
							try {
								dateFormate = DateUtil.DEFAULT_DATETIME_FORMATE;
								dateFormate.replaceAll("-", "/");
								temp = String.valueOf(DateUtil.formatDate((Date)val,dateFormate));
							} catch (Exception e4) {
								try {
									dateFormate = DateUtil.DEFAULT_DATE_FORMATE;
									dateFormate.replaceAll("-", "/");
									temp = String.valueOf(DateUtil.formatDate((Date)val,dateFormate));
								} catch (Exception e5) {
									log.error("未知的日期格式，请重新指日期格式串",e5);
								}
							}
						}
					}
				}
                return temp;
            } else if (val instanceof Calendar) {
                return String.valueOf(DateUtil.formatDate((Calendar)val, dateFormate));
                
            } else {
                log.error("未知数据类型的转换");
                return val.toString();
            }
        }
        return "";
	}
	 
	//---
	
    public static List stringArray2List(String[] vals){
    	List<String> dataList = new ArrayList<String>();
    	if(vals != null && vals.length > 0){
    		for (int i = 0; i < vals.length; i++) {
    			if(vals[i] != null){
    				vals[i] = vals[i].trim();
    			}
    			dataList.add(vals[i]);
			}
    	}
    	return dataList;
    }
    
    public static String[] list2StringArray(List<String> dataList){
    	String[] array = null;
    	if(dataList != null && dataList.size() > 0){
    		array = new String[dataList.size()];
    		for (int i = 0; i < dataList.size(); i++) {
    			array[i] = (String)dataList.get(i);
			}
    	}
    	return array;
    }
    
    //---
	
    public static List shortArray2List(Short[] vals){
    	List<Short> dataList = new ArrayList<Short>();
    	if(vals != null && vals.length > 0){
    		for (int i = 0; i < vals.length; i++) {
    			dataList.add(vals[i]);
			}
    	}
    	return dataList;
    }
    
    public static Short[] list2ShortArray(List<Short> dataList){
    	Short[] array = null;
    	if(dataList != null && dataList.size() > 0){
    		array = new Short[dataList.size()];
    		for (int i = 0; i < dataList.size(); i++) {
    			array[i] = (Short)dataList.get(i);
			}
    	}
    	return array;
    }
    
    //---
	
    public static List integerArray2List(Integer[] vals){
    	List<Integer> dataList = new ArrayList<Integer>();
    	if(vals != null && vals.length > 0){
    		for (int i = 0; i < vals.length; i++) {
    			dataList.add(vals[i]);
			}
    	}
    	return dataList;
    }
    
    public static Integer[] list2IntegerArray(List<Integer> dataList){
    	Integer[] array = null;
    	if(dataList != null && dataList.size() > 0){
    		array = new Integer[dataList.size()];
    		for (int i = 0; i < dataList.size(); i++) {
    			array[i] = (Integer)dataList.get(i);
			}
    	}
    	return array;
    }
    
    //---
	
    public static List longArray2List(Long[] vals){
    	List<Long> dataList = new ArrayList<Long>();
    	if(vals != null && vals.length > 0){
    		for (int i = 0; i < vals.length; i++) {
    			dataList.add(vals[i]);
			}
    	}
    	return dataList;
    }
    
    public static Long[] list2LongArray(List<Long> dataList){
    	Long[] array = null;
    	if(dataList != null && dataList.size() > 0){
    		array = new Long[dataList.size()];
    		for (int i = 0; i < dataList.size(); i++) {
    			array[i] = (Long)dataList.get(i);
			}
    	}
    	return array;
    }
    
    //---
	
    public static List doubleArray2List(Double[] vals){
    	List<Double> dataList = new ArrayList<Double>();
    	if(vals != null && vals.length > 0){
    		for (int i = 0; i < vals.length; i++) {
    			dataList.add(vals[i]);
			}
    	}
    	return dataList;
    }
    
    public static Double[] list2DoubleArray(List<Double> dataList){
    	Double[] array = null;
    	if(dataList != null && dataList.size() > 0){
    		array = new Double[dataList.size()];
    		for (int i = 0; i < dataList.size(); i++) {
    			array[i] = (Double)dataList.get(i);
			}
    	}
    	return array;
    }
    
    //---
	
    public static List floatArray2List(Float[] vals){
    	List<Float> dataList = new ArrayList<Float>();
    	if(vals != null && vals.length > 0){
    		for (int i = 0; i < vals.length; i++) {
    			dataList.add(vals[i]);
			}
    	}
    	return dataList;
    }
    
    public static Float[] list2FloatArray(List<Float> dataList){
    	Float[] array = null;
    	if(dataList != null && dataList.size() > 0){
    		array = new Float[dataList.size()];
    		for (int i = 0; i < dataList.size(); i++) {
    			array[i] = (Float)dataList.get(i);
			}
    	}
    	return array;
    }
    
    //---
	
    public static List dateArray2List(Date[] vals){
    	List<Date> dataList = new ArrayList<Date>();
    	if(vals != null && vals.length > 0){
    		for (int i = 0; i < vals.length; i++) {
    			dataList.add(vals[i]);
			}
    	}
    	return dataList;
    }
    
    public static Date[] list2DateArray(List<Date> dataList){
    	Date[] array = null;
    	if(dataList != null && dataList.size() > 0){
    		array = new Date[dataList.size()];
    		for (int i = 0; i < dataList.size(); i++) {
    			array[i] = (Date)dataList.get(i);
			}
    	}
    	return array;
    }

    /**
     * An empty unmodifiable list.
     * This uses the {@link Collections Collections} implementation 
     * and is provided for completeness.
     */
    public static final List EMPTY_LIST = Collections.EMPTY_LIST;
    
    /**
     * <code>ListUtils</code> should not normally be instantiated.
     */
    
    //-----------------------------------------------------------------------
    /**
     * Returns a new list containing all elements that are contained in
     * both given lists.
     *
     * @param list1  the first list
     * @param list2  the second list
     * @return  the intersection of those two lists
     * @throws NullPointerException if either list is null
     */
    public static List intersection(final List list1, final List list2) {
        final ArrayList result = new ArrayList();
        final Iterator iterator = list2.iterator();

        while (iterator.hasNext()) {
            final Object o = iterator.next();

            if (list1.contains(o)) {
                result.add(o);
            }
        }

        return result;
    }

    /**
     * Subtracts all elements in the second list from the first list,
     * placing the results in a new list.
     * <p>
     * This differs from {@link List#removeAll(Collection)} in that
     * cardinality is respected; if <Code>list1</Code> contains two
     * occurrences of <Code>null</Code> and <Code>list2</Code> only
     * contains one occurrence, then the returned list will still contain
     * one occurrence.
     *
     * @param list1  the list to subtract from
     * @param list2  the list to subtract
     * @return  a new list containing the results
     * @throws NullPointerException if either list is null
     */
    public static List subtract(final List list1, final List list2) {
        final ArrayList result = new ArrayList(list1);
        final Iterator iterator = list2.iterator();

        while (iterator.hasNext()) {
            result.remove(iterator.next());
        }

        return result;
    }

    /**
     * Returns the sum of the given lists.  This is their intersection
     * subtracted from their union.
     *
     * @param list1  the first list 
     * @param list2  the second list
     * @return  a new list containing the sum of those lists
     * @throws NullPointerException if either list is null
     */ 
    public static List sum(final List list1, final List list2) {
        return subtract(union(list1, list2), intersection(list1, list2));
    }

    /**
     * Returns a new list containing the second list appended to the
     * first list.  The {@link List#addAll(Collection)} operation is
     * used to append the two given lists into a new list.
     *
     * @param list1  the first list 
     * @param list2  the second list
     * @return  a new list containing the union of those lists
     * @throws NullPointerException if either list is null
     */
    public static List union(final List list1, final List list2) {
        final ArrayList result = new ArrayList(list1);
        result.addAll(list2);
        return result;
    }

    /**
     * Tests two lists for value-equality as per the equality contract in
     * {@link java.util.List#equals(java.lang.Object)}.
     * <p>
     * This method is useful for implementing <code>List</code> when you cannot
     * extend AbstractList. The method takes Collection instances to enable other
     * collection types to use the List implementation algorithm.
     * <p>
     * The relevant text (slightly paraphrased as this is a static method) is:
     * <blockquote>
     * Compares the two list objects for equality.  Returns
     * <tt>true</tt> if and only if both
     * lists have the same size, and all corresponding pairs of elements in
     * the two lists are <i>equal</i>.  (Two elements <tt>e1</tt> and
     * <tt>e2</tt> are <i>equal</i> if <tt>(e1==null ? e2==null :
     * e1.equals(e2))</tt>.)  In other words, two lists are defined to be
     * equal if they contain the same elements in the same order.  This
     * definition ensures that the equals method works properly across
     * different implementations of the <tt>List</tt> interface.
     * </blockquote>
     *
     * <b>Note:</b> The behaviour of this method is undefined if the lists are
     * modified during the equals comparison.
     * 
     * @see java.util.List
     * @param list1  the first list, may be null
     * @param list2  the second list, may be null
     * @return whether the lists are equal by value comparison
     */
    public static boolean isEqualList(final Collection list1, final Collection list2) {
        if (list1 == list2) {
            return true;
        }
        if (list1 == null || list2 == null || list1.size() != list2.size()) {
            return false;
        }

        Iterator it1 = list1.iterator();
        Iterator it2 = list2.iterator();
        Object obj1 = null;
        Object obj2 = null;

        while (it1.hasNext() && it2.hasNext()) {
            obj1 = it1.next();
            obj2 = it2.next();

            if (!(obj1 == null ? obj2 == null : obj1.equals(obj2))) {
                return false;
            }
        }

        return !(it1.hasNext() || it2.hasNext());
    }
    
    /**
     * Generates a hash code using the algorithm specified in 
     * {@link java.util.List#hashCode()}.
     * <p>
     * This method is useful for implementing <code>List</code> when you cannot
     * extend AbstractList. The method takes Collection instances to enable other
     * collection types to use the List implementation algorithm.
     * 
     * @see java.util.List#hashCode()
     * @param list  the list to generate the hashCode for, may be null
     * @return the hash code
     */
    public static int hashCodeForList(final Collection list) {
        if (list == null) {
            return 0;
        }
        int hashCode = 1;
        Iterator it = list.iterator();
        Object obj = null;
        
        while (it.hasNext()) {
            obj = it.next();
            hashCode = 31 * hashCode + (obj == null ? 0 : obj.hashCode());
        }
        return hashCode;
    }   

    //-----------------------------------------------------------------------
    /**
     * Returns a List containing all the elements in <code>collection</code>
     * that are also in <code>retain</code>. The cardinality of an element <code>e</code>
     * in the returned list is the same as the cardinality of <code>e</code>
     * in <code>collection</code> unless <code>retain</code> does not contain <code>e</code>, in which
     * case the cardinality is zero. This method is useful if you do not wish to modify
     * the collection <code>c</code> and thus cannot call <code>collection.retainAll(retain);</code>.
     * 
     * @param collection  the collection whose contents are the target of the #retailAll operation
     * @param retain  the collection containing the elements to be retained in the returned collection
     * @return a <code>List</code> containing all the elements of <code>c</code>
     * that occur at least once in <code>retain</code>.
     * @throws NullPointerException if either parameter is null
     * @since Commons Collections 3.2
     */
    public static List retainAll(Collection collection, Collection retain) {
        List list = new ArrayList(Math.min(collection.size(), retain.size()));

        for (Iterator iter = collection.iterator(); iter.hasNext();) {
            Object obj = iter.next();
            if (retain.contains(obj)) {
                list.add(obj);
            }
        }
        return list;
    }

    /**
     * Removes the elements in <code>remove</code> from <code>collection</code>. That is, this
     * method returns a list containing all the elements in <code>c</code>
     * that are not in <code>remove</code>. The cardinality of an element <code>e</code>
     * in the returned collection is the same as the cardinality of <code>e</code>
     * in <code>collection</code> unless <code>remove</code> contains <code>e</code>, in which
     * case the cardinality is zero. This method is useful if you do not wish to modify
     * <code>collection</code> and thus cannot call <code>collection.removeAll(remove);</code>.
     * 
     * @param collection  the collection from which items are removed (in the returned collection)
     * @param remove  the items to be removed from the returned <code>collection</code>
     * @return a <code>List</code> containing all the elements of <code>c</code> except
     * any elements that also occur in <code>remove</code>.
     * @throws NullPointerException if either parameter is null
     * @since Commons Collections 3.2
     */
    public static List removeAll(Collection collection, Collection remove) {
        List list = new ArrayList();
        for (Iterator iter = collection.iterator(); iter.hasNext();) {
            Object obj = iter.next();
            if (remove.contains(obj) == false) {
                list.add(obj);
            }
        }
        return list;
    }
}
