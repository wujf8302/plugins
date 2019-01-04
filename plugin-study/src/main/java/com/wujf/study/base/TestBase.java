package com.wujf.study.base;

import java.text.DecimalFormat;

/**
 * 测试八大包装类的使用及 类型转换.
 */
public class TestBase {
	
	public static void main(String[] args) {

		boolean bo1 = true;
		char ch1 = 'a';
		byte by1 = 3;
		// Byte
		short sh1 = 3;
		// Short
		int in1 = 123;
		long lo1 = 123456789L;
		// Long
		float fl1 = 32.3F;
		double do1 = 323.32D;

		// -------1基本类型转化成对象.
		Boolean boObj = new Boolean(bo1);
		Character chObj = new Character(ch1);
		Integer inObj = new Integer(in1);
		Float flObj = new Float(fl1);
		Double doObj = new Double(do1);

		// -------2. 对象转化成基本类型.
		int in2 = inObj.intValue();
		double do2 = doObj.doubleValue();
		float fl2 = flObj.floatValue();

		// -------3.基本类型转化成字符串.
		String tmp21 = String.valueOf(fl1);

		// -------4. 将字符转化成基本数据类型.
		String str1 = "456";
		int in3 = Integer.parseInt(str1);
		float fl3 = Float.parseFloat(str1);
		double do3 = Double.parseDouble(str1);

		// -------5.将String转化成包装对象.
		Integer in2Obj = new Integer(str1);

		// -------6. 将包装对象转化成String对象.
		String str2 = in2Obj.toString();
	}
	
	public static void doubleDemo() {
		Double d = 10.0;
        
	    DecimalFormat df =  new DecimalFormat("##############0.00");
        String ss = df.format(d);
        System.out.println(ss);
        if(ss.indexOf("-")==-1){
        	
        }else{
        	System.out.println("支付金额不能为负数!");
        }
	    Double dd = d*100;
	    System.out.println(dd);
	    Long l = dd.longValue();
	    if(l==0L){
	    	System.out.println("支付金额不能为零!");
	    }
	}
}
