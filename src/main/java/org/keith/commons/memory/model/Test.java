package org.keith.commons.memory.model;


public class Test {

	private final String s1;
	private static final String s2;
	
	public Test() {
		s1 = "2";
	}
	
	static {
		s2 = "2";
	}
	
	// 类级别，不可重写
//	@Override
	public static void m1() {
		
	}
}
