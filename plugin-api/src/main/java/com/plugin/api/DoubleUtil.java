package com.plugin.api;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.log4j.Logger;

/**
 * <bean:write name="order" property="totalPrice" format="#############.00" />
 * <bean:write name="order" property="createDate" format="yyyy-MM-dd HH:mm" />
 * <fmt:formatNumber value="0.00" pattern="#############0.00"/> 浮点数工具类
 */
public class DoubleUtil {

	private static Logger log = Logger.getLogger(DoubleUtil.class);
	
	public final static String DEFAULT_FORMATE0 = "###################0";//浮点格式串  
    public final static String DEFAULT_FORMATE1 = "###################0.00";//浮点格式串  
    public final static String DEFAULT_FORMATE2 = "###################0.0000";//浮点格式串  
    public final static String DEFAULT_FORMATE3 = "###################0.000000";//浮点格式串  
	
	private static final int DEF_DIV_SCALE = 10; // 默认除法运算精度

	public static void main(String[] args) {
		
		//System.out.println(compareTo(Double.parseDouble("-0.18"), new Double(0)));
		
		System.out.println(formatDouble(1098.6726));
		
		System.out.println(DoubleUtil.formatDouble(DoubleUtil.div(Double.valueOf("5390168373"),Double.valueOf(100000000),2)));
	}
	
	/**
	 * double 转 BigDecimal.
	 */
	public static BigDecimal double2BigDecimal(double d) {
		return double2BigDecimal(Double.toString(d));
	}
	/**
	 * double 转 BigDecimal.
	 */
	public static BigDecimal double2BigDecimal(String d) {
		return new BigDecimal(d);
	}
	
	/**
	 * 浮点数大小比较判断
	 */
	public static int compareTo(String sd1, String td2) {
		return compareTo(Double.parseDouble(sd1),Double.parseDouble(td2));
	}
	public static int compareTo(double sd1, double td2) {
	    BigDecimal a = BigDecimal.valueOf(sd1);
	    BigDecimal b = BigDecimal.valueOf(td2);
	    int result = a.compareTo(b);
	    /*
	    if(n == -1){//小于
		    // System.out.println("-1 sd1【" + sd1 + "】 小于< td2【" + td2 + "】");
	    }else if(n == 0){//等于
		    // System.out.println("0 sd1【" + sd1 + "】 等于= td2【" + td2 + "】");
	    }else if(n == 1){// 大于
		    //System.out.println("1 sd1【" + sd1 + "】 大于> td2【" + td2 + "】");
	    }
	    */
	    /*
		if(result == 1){
			System.out.println(sd1 + " 大于 " + td2 + " - " + result);
		}else if(result == 0){
			System.out.println(sd1 + " 等于 " + td2 + " - " + result);
		}else if(result == -1){
			System.out.println(sd1 + " 小于 " + td2 + " - " + result);
		}else{
			System.out.println(sd1 + " 未知 " + td2 + " - " + result);
		}
		*/
	    return result;
	}
	
	//成交价格,区间下限,区间上限
	public static boolean isRange(String v,String min,String max) {
		return isRange(v,min,max,DoubleUtil.DEFAULT_FORMATE1);
	}
	public static boolean isRange(double v,double min,double max) {
		return isRange(v,min,max,DoubleUtil.DEFAULT_FORMATE1);
	}
	public static boolean isRange(double v,double min,double max,String pattern) {
		boolean b = false;
		if(DoubleUtil.compareTo(max,min)>= 0){//max>=min
			if(DoubleUtil.compareTo(v,min)>= 0 && DoubleUtil.compareTo(max,v) > 0){//v>=min && max>v ==>  min<=v<max
				b = true;
			}
		}
		return b;
    }
	public static boolean isRange(String v,String min,String max,String pattern) {
		boolean b = false;
		if(DoubleUtil.compareTo(max,min)>= 0){//max>=min
			if(DoubleUtil.compareTo(v,min)>= 0 && DoubleUtil.compareTo(max,v) > 0){//v>=min && max>v ==>  min<=v<max
				b = true;
			}
		}
		return b;
    }

	public String transferFunds(String fromAccount, String toAccount,
			double amount, String currency) {

		String statusMessage = "";
		try {
			NumberFormat formatter = new DecimalFormat("###,###,###,###.00");
			statusMessage = "COMPLETED: " + currency + " "
					+ formatter.format(amount)
					+ " was successfully transferred from A/C# " + fromAccount
					+ " to A/C# " + toAccount;
		} catch (Exception e) {
			statusMessage = "BankingService.transferFunds(): EXCEPTION: "
					+ e.toString();
		}

		return statusMessage;
	}

	/**
	 * 校验浮点数
	 * @param n 金额转化成分乘以的倍数
	 */
	public static String checkDouble(Double d, Integer n) {
		if (d != null) {
			DecimalFormat df = new DecimalFormat(DEFAULT_FORMATE1);
			String s = df.format(d);
			if (s.indexOf("-") != -1) { // 能找到‘-’，说明为负数
				return "金额不能为负数!";
			}
			Double dd = d * n;// 订单总支付费用=支付金额*100 单位:分
			Long payMoney = dd.longValue();
			if (payMoney == 0L) {
				return "金额不能为零!";
			}
		} else {
			return "金额值为空!";
		}
		return "";
	}

	/**
	 * 格式化双精度数字 保留2位(具有四舍五入功能)
	 */
	public static String formatDouble(String d) {
		if(StringUtil.isEmpty(d)){
			return d;
		}
        if(d.indexOf("--") == -1){
        	return formatDouble(Double.valueOf(d));
		}
		return "";
	}
	public static String formatDouble(Double d) {
		if (d != null) {
			DecimalFormat df = new DecimalFormat(DEFAULT_FORMATE1);
			return df.format(d);
		}
		return null;
	}
	/**
	 * 格式化双精度数字 保留2位(具有四舍五入功能)
	 */
	public static String formatDouble2(String d) {
		if(StringUtil.isEmpty(d)){
			return d;
		}
		return formatDouble2(Double.valueOf(d)) ;
	}
	public static String formatDouble2(Double d) {
		if (d != null) {
			DecimalFormat df = new DecimalFormat(DEFAULT_FORMATE2);
			return df.format(d);
		}
		return null;
	}
	public static String formatDouble3(Double d) {
		if (d != null) {
			DecimalFormat df = new DecimalFormat(DEFAULT_FORMATE3);
			return df.format(d);
		}
		return null;
	}
	
	/**
	 * 格式化双精度数字
	 */
	public static String formatDouble(String d, String format) {
		if(d == null || "".equals(d)){
			return d;
		}
		return formatDouble(Double.valueOf(d),format);
	}
	public static String formatDouble(Double d, String format) {
		if (d != null) {
			DecimalFormat df = new DecimalFormat(format);
			return df.format(d);
		}
		return null;
	}

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}
	public static double add(String v1, String v2) {
		return add(Double.valueOf(v1),Double.valueOf(v2));
	}
	
	public static String adds(String[] vals){
		String total = "0";
		if(vals != null){
			for (int i = 0; i < vals.length; i++) {
				if(i == 0){//0 1
					if(StringUtil.isNotEmpty(vals[i]) && StringUtil.isNotEmpty(vals[i+1])){
						total = String.valueOf(DoubleUtil.add(vals[i],vals[i+1]));
					}
				}else if(i > 1){ //2
					if(StringUtil.isNotEmpty(vals[i])){
						total = String.valueOf(DoubleUtil.add(vals[i],total));
					}
				}
			}
		}
		return total;
	}

	/**
	 * 提供精确的减法运算(v1-v2=?)。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
	public static double sub(String v1, String v2) {
		return sub(Double.valueOf(v1),Double.valueOf(v2));
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
	public static double mul(String v1, String v2) {
		return mul(Double.valueOf(v1),Double.valueOf(v2));
	}
	
	public static String mul(double v1, double v2, int scale) {
	    String format = "###################0";//浮点格式串  
	    if(scale > 0){
	    	format = format + ".";//浮点格式串  
	    	for (int i = 0; i < scale; i++) {
	    		format = format + "0";
			}
	    }
	    return mul(v1,v2,format) ;
	}
	public static String mul(String v1, String v2, int scale) {
		return mul(Double.valueOf(v1), Double.valueOf(v2),scale);
	}
	
	public static String mul(double v1, double v2,String format) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		double d = b1.multiply(b2).doubleValue();
		return formatDouble(d,format);
	}
	public static String mul(String v1, String v2,String format) {
		return mul(Double.valueOf(v1),Double.valueOf(v2),format);
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1被除数
	 * @param v2除数
	 * @return 两个参数的商
	 */
	public static double div(String v1, String v2) {
		return div(Double.valueOf(v1), Double.valueOf(v2));
	}
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2除数
	 * @param scale表示表示需要精确到小数点以后几位
	 *            。
	 * @return 两个参数的商
	 */
	public static double div(String v1, String v2, int scale) {
		return div(Double.valueOf(v1),Double.valueOf(v2),scale);
	}
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v需要四舍五入的数字
	 * @param scale小数点后保留几位
	 * @return 四舍五入后的结果
	 */

	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 将double类型数据转换为百分比格式，并保留小数点前IntegerDigits位和小数点后FractionDigits位.
	 */
	public static String getPercentFormat(double d, int integerDigits,
			int fractionDigits) {
		NumberFormat nf = java.text.NumberFormat.getPercentInstance();
		nf.setMaximumIntegerDigits(integerDigits);// 小数点前保留几位
		nf.setMinimumFractionDigits(fractionDigits);// 小数点后保留几位
		String str = nf.format(d);
		return str;
	}
}
