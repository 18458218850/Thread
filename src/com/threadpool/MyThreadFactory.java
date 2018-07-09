package com.threadpool;

import java.util.concurrent.ThreadFactory;

/**
 * 线程工厂类
 * @author lijh
 *
 */
public class MyThreadFactory implements ThreadFactory{

	@Override
	public Thread newThread(Runnable r) {
		return new Thread(r);
	}

	public static void main(String[] args) {
		MyThreadFactory mtf = new MyThreadFactory();
		mtf.newThread(new T()).start();;
	}
}

class T implements Runnable{

	@Override
	public void run() {
		System.out.println("My Thread");
	}
	
	
}
