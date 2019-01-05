package com.plugin.api;

import java.math.BigDecimal;
import java.math.MathContext;
/**
 * 
 * @author wujf
 */
public class MathUtil {
	
	/**
	 * 浮点数格式化
	 */
	public static void main(String[] args) {

		// MathContext mathContext = new MathContext(3);
		BigDecimal b1 = new BigDecimal(1.233);
		BigDecimal b2 = new BigDecimal(1.33);

		System.out.println(b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).doubleValue());

		// MathContext mathContext = new MathContext(4);
		// System.out.println(b1.multiply(b2));
		// System.out.println(b1.negate());

		MathContext mathContext = new MathContext(2 + 2);
		System.out.println((b1.add(b2, mathContext)).doubleValue());

	}
}
