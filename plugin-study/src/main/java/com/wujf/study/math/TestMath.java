package com.wujf.study.math;

import java.text.NumberFormat;

/**
 * Math������ѧ������ʹ��.�����ָ�ʽ��
 * @author fj
 */
public class TestMath {

	public static void main(String[] args) {

		int in1 = 5, in2 = -2;
		double do1 = 23.4, do2 = 23.5;

		// --------Math������ѧ������ʹ��
		System.out.println("PI: " + Math.PI);
		System.out.println("E: " + Math.E);

		System.out.println("����ֵ: " + Math.abs(in2));
		System.out.println("ƽ����: " + Math.sqrt(in1));
		System.out.println("2��4�η�: " + Math.pow(2, 4));

		System.out.println("��������ȡ��(23.4): " + Math.round(do1));
		System.out.println("��������ȡ��(23.5): " + Math.round(do2));

		System.out.println("��С��ȡС��(23.4): " + Math.floor(do1));
		System.out.println("��С��ȡС��(23.5): " + Math.floor(do2));

		System.out.println("��С��ȡ����(23.4): " + Math.ceil(do1));
		System.out.println("��С��ȡ����(23.5): " + Math.ceil(do2));

		//
		// --------java.text.NumberFormat��ʹ��
		java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(3);// ������������3λ.
		// nf.setMaximumIntegerDigits(3);
		nf.setMaximumFractionDigits(2);// С���������2λ.
		// nf.setMinimumFractionDigits(2);
		String str11 = nf.format(Math.PI);
		System.out.println(str11);

		//
		// --------java.text.DecimalFormat��ʹ��
		java.text.DecimalFormat df = new java.text.DecimalFormat("##.00");
		String str12 = df.format(Math.PI);
		System.out.println(str12);
		System.out.println("===============");
		paifeibi();
		System.out.println(3 / 14.00);
		System.out.println(new Double(12L));
	}

	public static void paifeibi() {
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMaximumIntegerDigits(3);
		nf.setMaximumFractionDigits(2);
		double d1 = new Double(12L);
		Long l = 5L;
		double d = l / d1;
		System.out.println(nf.format(d));
	}

}
