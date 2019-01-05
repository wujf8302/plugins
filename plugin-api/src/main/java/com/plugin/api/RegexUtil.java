package com.plugin.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import com.plugin.api.ConstantUtil;
/**
 * 正则表达式.
 * @author wujf
 */
public class RegexUtil {
	
	 private static Logger log = Logger.getLogger(RegexUtil.class);
	 
	public static void main(String[] args) {
		String param = "18950126057";
		Boolean b = RegexUtil.verify(param, ConstantUtil.Regex.Verify.mobileRegex);
		if(b== null){
			System.out.println("参数为空!");
		}else {
			if(b.booleanValue()==true){
				System.out.println(b);
			}else {
				System.out.println(b);
			}
		}
		
		System.out.println(match("123adsad", "\\d+"));
	}
	
	/***
	 * 根据指定的正则表达式校验参数
	 * @param param 参数
	 * @param regex 正则表达式
	 * @return 参数为空 返回null 匹配返回true 不匹配返回false
	 */
	public static Boolean verify(String param,String regex) {
		if(param == null || "".equals(param)){
	    	return null;
	    }
		return new Boolean(param.matches(regex));
	}
	
	/**
	 * 正则验证
	 */
	public static Boolean verifyPattern(String param,String rexp) {
		Pattern regex = Pattern.compile(rexp);
		Matcher matcher = regex.matcher(param);
		return new Boolean(matcher.matches());
	}

	/**
	 * 18位身份证验证
	 * @param cardNum 18位身份证
	 * @return 验证成功返回true
	 */
	public static boolean validate18IDCard(String cardNum) {
		if (cardNum.length() == 18) {
			String tempStr = cardNum.substring(0, 17);
			String sourceCheckCode = cardNum.substring(17, 18);
			String checkCode = "";
			int[] a = new int[17];
			int i = 0;
			try {
				while (i < 17) {
					a[i] = Integer.parseInt(tempStr.substring(i, i + 1));
					i++;
				}
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			int mod = (a[0] * 7 + a[1] * 9 + a[2] * 10 + a[3] * 5 + a[4] * 8
					+ a[5] * 4 + a[6] * 2 + a[7] * 1 + a[8] * 6 + a[9] * 3
					+ a[10] * 7 + a[11] * 9 + a[12] * 10 + a[13] * 5 + a[14]
					* 8 + a[15] * 4 + a[16] * 2) % 11;
			switch (mod) {
			case 10:
				checkCode = "2";
				break;
			case 9:
				checkCode = "3";
				break;
			case 8:
				checkCode = "4";
				break;
			case 7:
				checkCode = "5";
				break;
			case 6:
				checkCode = "6";
				break;
			case 5:
				checkCode = "7";
				break;
			case 4:
				checkCode = "8";
				break;
			case 3:
				checkCode = "9";
				break;
			case 2:
				checkCode = "x";
				break;
			case 1:
				checkCode = "0";
				break;
			case 0:
				checkCode = "1";
				break;
			}
			if(sourceCheckCode.equals(checkCode)) {
				log.info(sourceCheckCode + ":" + checkCode);
				return true;
			}
		}else if(cardNum.length()==15){
			validate15CardId(cardNum);
		}else{
			log.info("你输入的身份证号码有误！");
			return false;
		}
		return false;
	}

	/**
	 * 15位身份证验证(只做了数字验证)
	 * @param cardId 15位身份证
	 * @return 验证成功返回true:
	 */
	public static boolean validate15CardId(String cardId) {
		int i = 0;
		try {
			while (i < 15) {
				if (!Character.isDigit(cardId.charAt(i))) {
					return false;
				}
				i++;
			}
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
		}
		return true;
	}

	public static String getCardId15ToCardId18(String num) {
		int[] arrInt = new int[] { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,8, 4, 2 };
		String[] arrCh = new String[] { "1", "0", "X", "9", "8", "7", "6", "5","4", "3", "2" };
		int nTemp = 0, i;
		num = num.substring(0, 6) + "19" + num.substring(6, num.length() - 6);
		for (i = 0; i < 17; i++) {
			nTemp += Integer.parseInt(num.substring(i, 1)) * arrInt[i];
		}
		num += arrCh[nTemp % 11];
		return num;
	}

    /**
     * 出生年月与身份证对应验证
     * @param birthday 出生年月日(纯数字)
     * @param cardId 身份证号
     * @return
     */
	public static boolean validateCardToBirthday(String birthday,String cardId) {
		if (!cardId.substring(6, 14).equals(birthday)) {
			return false;
		}
		return true;
	}

	/**
	 * 验证比较身份证中性别
	 * @param sex     0,男1,女
	 * @param cardId  身份证号
	 * @return
	 */
	public static boolean validateCardToSex(int sex, String cardId) {
		int a = Integer.parseInt(cardId.substring(16, 17)) / 2 == 0 ? 1 : 0;
		if (a == sex) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断时间date1是否在时间date2之前(时间格式 2005-4-21 16:16:34)
	 * @param date1 时间
	 * @param date2 时间
	 * @return
	 */
	public static boolean isDateBefore(String date1, String date2) {
		DateFormat df = DateFormat.getDateTimeInstance();
		try {
			return df.parse(date1).before(df.parse(date2));	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 判断时间是否是本月的ch1-ch2日之间
	 * @param dateTime 时间
	 * @param ch1
	 * @param ch2
	 * @return
	 */
	public static boolean getDateTime(String dateTime,int ch1,int ch2){
		//dateTime="20080110164811"; //当前年月日时分秒的组合
		Date now = new Date();
		String day="";
		if(dateTime!=null){
			if(dateTime.length()>7){
				day=dateTime.substring(6, 8);
			}
		}else{
			SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			String dateString = outFormat.format(now);
			day=dateString;
		}
		Integer time=new Integer(day);
		if(time.intValue()>=ch1&&time.intValue()<=ch2){
			return true;
		}
		return false;
	}

	//-----------------------------------------------

	public static String getMatchData(String pageData, String regex, String arg) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
		Matcher matcher = pattern.matcher(pageData);
		if (matcher.find()) {
			String strContent1 = matcher.group();
			Matcher matcher1 = pattern.matcher(strContent1);
			strContent1 = matcher1.replaceAll(arg);
			return strContent1;
		} else {
			return "";
		}
	}

	public static List getMatchDataList(String data, String regex, String arg) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
		Matcher matcher = pattern.matcher(data);
		List dataList = new ArrayList();
		String strContent;
		for (; matcher.find(); dataList.add(strContent)) {
			strContent = matcher.group();
			Matcher matcher1 = pattern.matcher(strContent);
			strContent = matcher1.replaceAll(arg);
		}

		return dataList;
	}

	public static boolean contain(String str, String regex) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.find();
	}

	public static boolean match(String str, String regex) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 运用正则提取网页中的内容 这个方法是提取title的内容 
	 */
	public String parseTitle(String context){   
        String result="";   
        String patWhTitle="<\\s*?(title)\\s*?>[\\s\\S]*?</\\s*?(title)\\s*?>";   
        String patTitle="</?\\s*?(title)\\s*?>";   
        java.util.regex.Pattern titlePattern = java.util.regex.Pattern.compile(patWhTitle,2);   
        Matcher titleMatcher=titlePattern.matcher(context);   
        java.util.regex.Pattern titlePattern2= java.util.regex.Pattern.compile(patTitle, 2);   
          
        while(titleMatcher.find())   {   
            String title=titleMatcher.group();   
            Matcher titleMatcher2=titlePattern2.matcher(title);   
            result+="["+titleMatcher2.replaceAll("")+"]";   
        }   
        return result;   
    }   
  
   /**
    * 运用正则提取网页中的内容 这个方法是提取超链接的内容 
    */
    public String parseA(String context)   {   
        String result="";   
        String patWhA="<[a]\\s+?[^>]*?>[^<]+?</[a]\\s*?>";   
        String patA="</?\\s*?[a][\\s\\S]*?>";   
        java.util.regex.Pattern aPattern=java.util.regex.Pattern.compile(patWhA,2);   
        Matcher aMat=aPattern.matcher(context);   
        java.util.regex.Pattern aPattern2=java.util.regex.Pattern.compile(patA,2);   
        while(aMat.find()){   
            String a=aMat.group();   
            Matcher mat=aPattern2.matcher(a);   
            result+="["+mat.replaceAll("")+"]";   
        }   
        return result;   
    }
    
	//-----------------------------------------------
    
    public static class Tools{
        /** 
        * 类简介: 使用正则表达式验证数据或提取数据,类中的方法全为静态的 
        * 主要方法:   
        *    1. isHardRegexpValidate(String source, String regexp)      区分大小写敏感的正规表达式批配
        *    2. isSoftRegexpValidate(String source, String regexp)      不区分大小写的正规表达式批配  
        *    3. getHardRegexpMatchResult(String source, String regexp)  返回许要的批配结果集(大小写敏感的正规表达式批配)   
        *    4. getSoftRegexpMatchResult(String source, String regexp)  返回许要的批配结果集(不区分大小写的正规表达式批配)   
        *    5  getHardRegexpArray(String source, String regexp)        返回许要的批配结果集(大小写敏感的正规表达式批配) 
        *    6. getSoftRegexpMatchResult(String source, String regexp)  返回许要的批配结果集(不区分大小写的正规表达式批配) 
        *    7. getBetweenSeparatorStr(final String originStr,final char leftSeparator,final char rightSeparator)  得到指定分隔符中间的字符串的集合 

         1. 匹配图象;                      
         2  匹配email地址;                    
         3  匹配匹配并提取url ;                         
         4  匹配并提取http ;
         5. 匹配日期                       
         6  匹配电话;                               
         7  匹配身份证                                       
         8  匹配邮编代码
         9. 不包括特殊字符的匹配 (字符串中不包括符号 数学次方号^ 单引号' 双引号" 分号; 逗号, 帽号: 数学减号- 右尖括号> 左尖括号< 反斜杠\ 即空格,制表符,回车符等
         10 匹配非负整数（正整数 + 0)                                         
         11 匹配不包括零的非负整数（正整数 > 0)
         12 匹配正整数                                                                      
         13 匹配非正整数（负整数 + 0）                                                
         14 匹配负整数;                                                                      
         15 匹配整数 ;
         16 匹配非负浮点数（正浮点数 + 0）                                 
         17 匹配正浮点数
         18 匹配非正浮点数（负浮点数 + 0）                                 
         19 匹配负浮点数;                          
         20 匹配浮点数;                                                                      
         21 匹配由26个英文字母组成的字符串;   
         22 匹配由26个英文字母的大写组成的字符串                 
         23 匹配由26个英文字母的小写组成的字符串 
         24 匹配由数字和26个英文字母组成的字符串;                   
         25 匹配由数字、26个英文字母或者下划线组成的字符串;
         */
    	
    	public static final Set SEPARATOR_SET = new TreeSet();  //保放有四组对应分隔符
    	public static HashMap regexpHash = new HashMap();       // 存放各种正规表达式(以key->value的形式)
    	public static List matchingResultList = new ArrayList();//存放各种正规表达式(以key->value的形式)
    	
    	/**
    	 * 添加正规表达式 (以key->value的形式存储)
    	 * @param regexpName 该正规表达式名称 `
    	 * @param regexp该正规表达式内容
    	 */
    	public void putRegexpHash(String regexpName, String regexp) {
    		regexpHash.put(regexpName, regexp);
    	}

    	/**
    	 * 得到正规表达式内容 (通过key名提取出value[正规表达式内容])
    	 * @param regexpName正规表达式名称
    	 * @return 正规表达式内容
    	 */
    	public String getRegexpHash(String regexpName) {
    		if (regexpHash.get(regexpName) != null) {
    			return ((String) regexpHash.get(regexpName));
    		} else {
    			log.info("在regexpHash中没有此正规表达式");
    			return "";
    		}
    	}

    	/**
    	 * 清除正规表达式存放单元
    	 */
    	public void clearRegexpHash() {
    		regexpHash.clear();
    		return;
    	}

    	/**
    	 * 大小写敏感的正规表达式批配
    	 * @param source 批配的源字符串
    	 * @param regexp 批配的正规表达式
    	 * @return 如果源字符串符合要求返回真,否则返回假 如: Regexp.isHardRegexpValidate("ygj@suncer.com.cn",email_regexp) 返回真
    	 */
    	public static boolean isHardRegexpValidate(String source, String regexp) {
    		try {
    			PatternCompiler compiler = new Perl5Compiler();// 用于定义正规表达式对象模板类型
    			PatternMatcher matcher = new Perl5Matcher();// 正规表达式比较批配对象
    			org.apache.oro.text.regex.Pattern hardPattern = compiler.compile(regexp);// 实例大小大小写敏感的正规表达式模板
    			return matcher.contains(source, hardPattern);// 返回批配结果
    		} catch (MalformedPatternException e) {
    			e.printStackTrace();
    		}
    		return false;
    	}
    }
}
