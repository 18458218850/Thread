package com.zuokai.spring0515;

public class HelloImpl implements Hello {

	@Override
	public void seyHello(String name) {
		System.out.println("hello " + name);
	}

}
