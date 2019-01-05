package com.plugin.api;
import java.math.BigDecimal;   
import java.math.RoundingMode;   
  
/**  
 * @title 货币显示处理工具类  
 * @detail 包含以下内容：  
 * 1、四舍五入求值  
 * 2、针对不同的格式化要求：万，百万，亿等  
 * 3、会计格式的货币值：添加','符号  
 * 4、非科学计数法的货币值  
 * @author wujf  
 * @version 1.0  
 */  
public class MoneyUtil {   
	
	public static class MoneyUtilDemo {   

	    /** 大写数字 */  
	    private static final String[] NUMBERS = { "零", "壹", "贰", "叁", "肆", "伍", "陆","柒", "捌", "玖" };   
	    /** 整数部分的单位 */  
	    private static final String[] IUNIT = { "元", "拾", "佰", "仟", "万", "拾", "佰","仟", "亿", "拾", "佰", "仟", "万", "拾", "佰", "仟" };   
	    /** 小数部分的单位 */  
	    private static final String[] DUNIT = { "角", "分", "厘" };   
	  
		  /**  
		   * 得到大写金额。  
		   */  
		  public static String toChinese(String str) {   
		    str = str.replaceAll(",", "");// 去掉","   
		    String integerStr;// 整数部分数字   
		    String decimalStr;// 小数部分数字   
		  
		    // 初始化：分离整数部分和小数部分   
		    if (str.indexOf(".") > 0) {   
		      integerStr = str.substring(0, str.indexOf("."));   
		      decimalStr = str.substring(str.indexOf(".") + 1);   
		    } else if (str.indexOf(".") == 0) {   
		      integerStr = "";   
		      decimalStr = str.substring(1);   
		    } else {   
		      integerStr = str;   
		      decimalStr = "";   
		    }   
		   // integerStr去掉首0，不必去掉decimalStr的尾0(超出部分舍去)   
		    if (!integerStr.equals("")) {   
		      integerStr = Long.toString(Long.parseLong(integerStr));   
		     if (integerStr.equals("0")) {   
		       integerStr = "";   
		      }   
		   }   
		    // overflow超出处理能力，直接返回   
		    if (integerStr.length() > IUNIT.length) {   
		     System.out.println(str + ":超出处理能力");   
		      return str;   
		    }   
		  
		    int[] integers = toArray(integerStr);// 整数部分数字   
		    boolean isMust5 = isMust5(integerStr);// 设置万单位   
		    int[] decimals = toArray(decimalStr);// 小数部分数字   
		    return getChineseInteger(integers, isMust5) + getChineseDecimal(decimals);   
		 }   
	  
		  /**  
		   * 整数部分和小数部分转换为数组，从高位至低位  
		   */  
		  private static int[] toArray(String number) {   
		    int[] array = new int[number.length()];   
		    for (int i = 0; i < number.length(); i++) {   
		     array[i] = Integer.parseInt(number.substring(i, i + 1));   
		    }   
		    return array;   
		  }   
	  
		  /**  
		   * 得到中文金额的整数部分。  
		  */  
		 private static String getChineseInteger(int[] integers, boolean isMust5) {   
		   StringBuffer chineseInteger = new StringBuffer("");   
		   int length = integers.length;   
		    for (int i = 0; i < length; i++) {   
		      // 0出现在关键位置：1234(万)5678(亿)9012(万)3456(元)   
		      // 特殊情况：10(拾元、壹拾元、壹拾万元、拾万元)   
		      String key = "";
					if (integers[i] == 0) {   
		       if ((length - i) == 13)// 万(亿)(必填)   
		          key = IUNIT[4];   
		        else if ((length - i) == 9)// 亿(必填)   
		          key = IUNIT[8];   
		        else if ((length - i) == 5 && isMust5)// 万(不必填)   
		          key = IUNIT[4];   
		        else if ((length - i) == 1)// 元(必填)   
		          key = IUNIT[0];   
		       // 0遇非0时补零，不包含最后一位   
		       if ((length - i) > 1 && integers[i + 1] != 0)   
		         key += NUMBERS[0];   
		     }   
		      chineseInteger.append(integers[i] == 0 ? key   
		          : (NUMBERS[integers[i]] + IUNIT[length - i - 1]));   
		   }   
		    return chineseInteger.toString();   
		  }   
	 
		  /**  
		   * 得到中文金额的小数部分。  
		   */  
		 private static String getChineseDecimal(int[] decimals) {   
		    StringBuffer chineseDecimal = new StringBuffer("");   
		    for (int i = 0; i < decimals.length; i++) {   
		      // 舍去3位小数之后的   
		      if (i == 3)   
		        break;   
		      chineseDecimal.append(decimals[i] == 0 ? ""  
		          : (NUMBERS[decimals[i]] + DUNIT[i]));   
		   }   
		    return chineseDecimal.toString();   
		  }   
		 
		  /**  
		   * 判断第5位数字的单位"万"是否应加。  
		   */  
		  private static boolean isMust5(String integerStr) {   
		    int length = integerStr.length();   
		    if (length > 4) {   
		      String subInteger = "";   
		      if (length > 8) {   
		       // 取得从低位数，第5到第8位的字串   
		        subInteger = integerStr.substring(length - 8, length - 4);   
		      } else {   
		        subInteger = integerStr.substring(0, length - 4);   
		      }   
		     return Integer.parseInt(subInteger) > 0;   
		    } else {   
		     return false;   
		    }   
		  }   
	  
		  public static void main(String[] args) {   
		    String number = "1.23";   
		    System.out.println(number + " " + MoneyUtilDemo.toChinese(number));   
		    number = "1234567890123456.123";   
		    System.out.println(number + " " + MoneyUtilDemo.toChinese(number));   
		    number = "0.0798";   
		    System.out.println(number + " " + MoneyUtilDemo.toChinese(number));   
		    number = "10,001,000.09";   
		    System.out.println(number + " " + MoneyUtilDemo.toChinese(number));   
		    number = "01.107700";   
		    System.out.println(number + " " + MoneyUtilDemo.toChinese(number));   
		  }   
	}  

	
	
       
    /**  
     * @title 获取格式化的人民币（四舍五入）  
     * @author chanson  
     * @param money  待处理的人民币  
     * @param scale  小数点后保留的位数  
     * @param divisor 格式化值（万，百万，亿等等）  
     * @return  
     */  
    public String getFormatMoney(double money, int scale, double divisor){   
        if(divisor == 0){return "0.00";}   
        if(scale < 0){return "0.00";}   
        BigDecimal moneyBD = new BigDecimal(money);   
        BigDecimal divisorBD = new BigDecimal(divisor);   
        return moneyBD.divide(divisorBD, scale, RoundingMode.HALF_UP).toString();   
    }   
       
    /**  
     * @title 获取会计格式的人民币（四舍五入）——添加会计标识：','  
     * @author chanson  
     * @param money  待处理的人民币  
     * @param scale  小数点后保留的位数  
     * @param divisor 格式化值（万，百万，亿等等）  
     * @return  
     */  
    public String getAccountantMoney(double money, int scale, double divisor){   
        String disposeMoneyStr = getFormatMoney(money, scale, divisor);   
        //小数点处理   
        int dotPosition = disposeMoneyStr.indexOf(".");   
        String exceptDotMoeny = null;//小数点之前的字符串   
        String dotMeony = null;//小数点之后的字符串   
        if(dotPosition > 0){   
            exceptDotMoeny = disposeMoneyStr.substring(0,dotPosition);   
            dotMeony = disposeMoneyStr.substring(dotPosition);   
        }else{   
            exceptDotMoeny = disposeMoneyStr;   
        }   
        //负数处理   
        int negativePosition = exceptDotMoeny.indexOf("-");   
        if(negativePosition == 0){   
            exceptDotMoeny = exceptDotMoeny.substring(1);   
        }   
        StringBuffer reverseExceptDotMoney = new StringBuffer(exceptDotMoeny);   
        reverseExceptDotMoney.reverse();//字符串倒转   
        char[] moneyChar = reverseExceptDotMoney.toString().toCharArray();   
        StringBuffer returnMeony = new StringBuffer();//返回值   
        for(int i = 0; i < moneyChar.length; i++){   
            if(i != 0 && i % 3 == 0){   
                returnMeony.append(",");//每隔3位加','   
            }   
            returnMeony.append(moneyChar[i]);   
        }   
        returnMeony.reverse();//字符串倒转   
        if(dotPosition > 0){   
            returnMeony.append(dotMeony);   
        }   
        if(negativePosition == 0){   
            return "-" + returnMeony.toString();   
        }else{   
            return returnMeony.toString();   
        }   
    }    
       
    public static void main(String[] args) {   
        double money = -1269486459.86;   
        int scale = 2;   
        double divisor = 10000.00;   
        System.out.println("原货币值: "+money);   
        MoneyUtil util = new MoneyUtil();   
        //System.out.println("货币值: "+util.getAccountantMoney(money, scale, 1));   
        String formatMeony = util.getFormatMoney(money, scale, divisor);   
        System.out.println("格式化货币值: "+formatMeony+"万元");   
        String accountantMoney = util.getAccountantMoney(money, scale, divisor);   
        System.out.println("会计货币值: "+accountantMoney+"万元");   
        
        //==================================================
        //把数字或字符的货币转换成人民币大写
        System.out.println(changeToBig(100.5));
		System.out.println(changeToBig(11.15));
		System.out.println(changeToBig(12.01));
		System.out.println(changeToBig(12.50));
		System.out.println(changeToBig(22222.50));
		System.out.println(changeToBig(1000100.00));
		System.out.println(changeToBig(100010.00));
		System.out.println(changeToBig(3050010.55));
		System.out.println(changeToBig(300010.02));
		System.out.println(changeToBig(300010.2));
		System.out.println(changeToBig(300010.12));
		System.out.println(changeToBig(30101010.12));
		System.out.println(changeToBig(11010.12));
		System.out.println(changeToBig(58.0));
		System.out.println(changeToBig(121.31));
		System.out.println(changeToBig(Double.parseDouble(121.31 +"f")));
		
		System.out.println(changeToBig(-78.00f));
    } 
    
    /**
     * 把数字或字符的货币转换成人民币大写
     */
    public static String changeToBig(double value) {
		String strPrix="";
		if(value<0){
			strPrix="负";
			value=(-1)*value;
		}
		char[] hunit = { '拾', '佰', '仟' };
		char[] vunit = { '万', '亿' };
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };
		
		BigDecimal big = new BigDecimal(String.valueOf(value));
		String valStr = String.valueOf(big.multiply(new BigDecimal(100)).toBigInteger());
		
		// 转化成字符串
		String head = "0";
		String rail = valStr;
		if (valStr.length() > 2){
			head = valStr.substring(0, valStr.length() - 2);
		}
		if (valStr.length() > 2){
			rail = valStr.substring(valStr.length() - 2);
		}
		rail = String.valueOf(Integer.parseInt(rail));
		
		StringBuffer prefix = new StringBuffer("");// 整数部分
		StringBuffer suffix = new StringBuffer("");// 小数部分
		
		// 处理小数点前面的数
		char[] chDig = head.toCharArray();
		char zero = '0';
		byte zeroSerNum = 0;
		for (int i = 0; i < chDig.length; i++) {
			int idx = (chDig.length - i - 1) % 4;
			int vidx = (chDig.length - i - 1) / 4;
			if (chDig[i] == '0') {
				zeroSerNum++;
				if (zero == '0') {
					zero = digit[0];
				} else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix.append(vunit[vidx - 1]);
					zero = '0';
				}
				continue;
			}
			zeroSerNum = 0;
			if (zero != '0') {
				prefix.append(zero);
				zero = '0';
			}
			prefix.append(digit[chDig[i] - '0']);
			if (idx > 0){
				prefix.append(hunit[idx - 1]);
			}
			if ((idx == 0 || idx == 1) && vidx > 0) {
				prefix.append(vunit[vidx - 1]);
			}
		}
		
		if (prefix.length() > 0){
			prefix.append('圆');
		}
		
		// 处理小数点后面的数
		if (("00".equals(rail) || "0".equals(rail)) && prefix.length() > 0){
			suffix.append("整");
		}else {
			if (rail.length() == 1) {
				if (prefix.length() > 0){
					suffix.append("零").append(digit[rail.charAt(0) - '0'] + "分");
				}else{
					suffix.append(digit[rail.charAt(0) - '0'] + "分");
				}
			} else if ("0".equals(String.valueOf(rail.charAt(1)))){
				suffix.append(digit[rail.charAt(0) - '0'] + "角");
			} else {
				suffix.append(digit[rail.charAt(0) - '0'] + "角"+ digit[rail.charAt(1) - '0'] + "分");
			}
		}
		
		return (strPrix + prefix.append(suffix).toString());
	}
}  
