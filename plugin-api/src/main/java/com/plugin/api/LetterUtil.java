package com.plugin.api;

import java.util.ArrayList;
import java.util.List;
/**
 * 英文字母工具类.
 * @author wujf
 */
public class LetterUtil {
	
	private int n = 26;
	private List<String> letters = new ArrayList<String>();

	public static void main(String args[]) {
		LetterUtil letterUtil = new LetterUtil();
		List<String> letters = letterUtil.getLetters();
		for (int i = 0; i < letters.size(); i++) {
			String letter = letters.get(i);
			letter = letter.toUpperCase();
			
			System.out.println(letter);
		}
	}

	public LetterUtil() {
		for (int i = (int) 'a'; i < 'a' + n; i++) {
			letters.add(String.valueOf((char)i));
		}
	}
	
	public List<String> getLetters() {
		return letters;
	}
	public void setLetters(List<String> letters) {
		this.letters = letters;
	}
}