package com.wujf.study.exam.exam3;

public class TestImpl implements ITest{

	public float test(int w, int y) {
          System.out.println("www");
          return 0;
	}
	public static void main(String[] args) {
		TestImpl t = new TestImpl();
		t.test(1, 2);
	}
}
