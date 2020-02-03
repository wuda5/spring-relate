package com.luban.test;

public class testkk {
	public static void main(String[] args) {
		StringBuffer s1 = new StringBuffer("hello");
		new StringBuilder("").toString();
		String s2 = "hello";

		System.out.println(s2 == s1.toString());
		System.out.println(s1.equals(s2));
	}
}
