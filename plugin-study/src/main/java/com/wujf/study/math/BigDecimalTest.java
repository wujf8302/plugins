package com.wujf.study.math;

import java.math.BigDecimal;

public class BigDecimalTest {
	public static void main(String[] args) {

		// ---非精度计算---
		double d1 = 0.3D;
		double d2 = 3D;

		System.out.println("0.3*3=" + (d1 * d2));

		// ---精度计算---
		BigDecimal bd1 = new BigDecimal("0.3");
		BigDecimal bd2 = new BigDecimal("3");

		// 乘
		BigDecimal tmpBD = bd1.multiply(bd2);
		double tmp11 = tmpBD.doubleValue();
		System.out.println("0.3*03=" + tmp11);

		// 除
		double tmp21 = bd1.divide(bd2).doubleValue();
		System.out.println("0.3/03=" + tmp21);

		// 加
		double tmp31 = bd1.add(bd2).doubleValue();
		System.out.println("0.3+03=" + tmp31);

		// 减
		double tmp41 = bd1.subtract(bd2).doubleValue();
		System.out.println("0.3-03=" + tmp41);

		// 1. 四舍五入 bd3.setScale()中第一个参数是保留的小数位数.
		BigDecimal bd3 = new BigDecimal("3.1414");
		BigDecimal bd4 = bd3.setScale(3, BigDecimal.ROUND_HALF_UP);//1
		double tmp51 = bd4.doubleValue();
		System.out.println(tmp51);

		// 2. 四舍五入.
		BigDecimal bd5 = new BigDecimal("5");
		BigDecimal bd6 = new BigDecimal("3");
		BigDecimal bd7 = bd5.divide(bd6,3,BigDecimal.ROUND_HALF_UP);//2
		double tmp61 = bd7.doubleValue();
		System.out.println(tmp61);

	}

}
