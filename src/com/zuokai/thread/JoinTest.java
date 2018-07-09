package com.zuokai.thread;

import java.util.concurrent.TimeUnit;

/**
 * join 等待该线程终止 
 * 当在主线程当中执行到t1.join()方法时，就认为主线程应该把执行权让给t1,直到t1执行结束，主线程才能在执行。 
 * @author Administrator
 *
 */
public class JoinTest implements Runnable{

	@Override
	public void run() {
		try {
			TimeUnit.SECONDS.sleep(3);
			System.out.println("zzz");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		JoinTest jt = new JoinTest();
		
		Thread t = new Thread(jt);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("this is main thread");
	}

}
