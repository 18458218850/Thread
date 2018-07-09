package com.zuokai.thread0427;

import java.util.concurrent.ThreadFactory;

/**
 * 线程工厂
 * @author Administrator
 *
 */
public class ThreadFactoryTest implements ThreadFactory{

	@Override
	public Thread newThread(Runnable arg0) {
		Thread t = new Thread(arg0);
		return t;
	}
	
	public static void main(String[] args) {
		T t = new T();
		ThreadFactoryTest tft = new ThreadFactoryTest();
		tft.newThread(t).start();
	}
}

class T implements Runnable{

	@Override
	public void run() {
		System.out.println("zzz");
	}
	
}