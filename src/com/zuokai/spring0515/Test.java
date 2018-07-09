package com.zuokai.spring0515;

public class Test {
	
	public static void main(String[] args) {
		HelloImpl hl = new HelloImpl();
		hl.seyHello("test");
		
		HelloProxy hp = new HelloProxy(hl);
		hp.seyHello("test");
	}

}
