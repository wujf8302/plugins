package com.plugin.api;
/**
 * 顺序输出，达到最大值从头在来.
 * @author wujf
 */
public class TrggUtil {
	
	private int num =0;
	private int sz =1;
	
	public TrggUtil(int sz) {
		this.sz = sz;
	}
	
    public static void main(String[] args) {
    	TrggUtil test = new TrggUtil(1);
		for (int i = 0; i < 100; i++) {
			int n = test.next();
			if(n == 1){
				System.out.println("提醒");
			}else{
				System.out.println("-----------------------" + n);
			}
		}
	}
    
    public int next() {
    	int val = (num++)+1;
    	if(val >= sz){
    		num = 0;
    	}
    	return val;
	}
}
