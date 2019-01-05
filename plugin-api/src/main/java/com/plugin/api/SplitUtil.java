package com.plugin.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 劈开字符串.
 * @author wujf
 */
public class SplitUtil {
    
    public static void main(String[] args) {
        List list = new ArrayList();
        list.add("a");
        list.add("b");
        
        System.out.println(join(list));
    }
    
    public static String join(List list) {
        return StringUtils.join(list, ",");
    }
    
    /**
     * 在每个空格字符处进行分解
     * 
     * @param s
     *            必选项 要被分解的 String 对象或文字 该对象不会被 split 方法修改
     * @param separator
     *            可选项 字符串或正则表达式对象，它标识了分隔字符串时使
     *            用的是一个还是多个字符。如果忽略该选项，返回包含整个字符串的单一元素数组
     * @param limit
     *            可选项 该值用来限制返回数组中的元素个数
     *            注意：结尾空字符串将被丢弃
     */
    public static String[] split(String s, String separator, int limit) {
        return s.split(separator, limit);
    }
}
