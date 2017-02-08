package com.yangyang.corejava.securt;

import org.apache.commons.codec.binary.Base64;

public class Base64Test {

	public static void main(String[] args) {
		byte[] bytes = new byte[]{1,2,3};
		String str = new String(Base64.encodeBase64(bytes));
		System.out.println(str);
	}

}
