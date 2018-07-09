package com.zuokai.spring0515;

import com.zuokai.spring0515.Logger.Leval;

public class HelloProxy implements Hello {

	private HelloImpl hello;
	
	
	public HelloProxy(HelloImpl hello){
		this.hello = hello;
	}
	
	
	
	@Override
	public void seyHello(String name) {
		Logger.logger(Leval.info, name);
		hello.seyHello(name);
	}

}
