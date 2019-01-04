package com.plugin.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * 读取键盘输入.
 * @author wujf
 */
public class KeyboardUtil {

	public String readKeyboardImput() throws IOException {
		InputStream is = System.in;
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String input = br.readLine();
		return input;
	}
}
