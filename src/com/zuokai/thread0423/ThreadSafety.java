package com.zuokai.thread0423;
/**
 * 线程安全
 * @author Administrator
 *
 */
public class ThreadSafety {
	int i=0;
	
	public void add(){
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}
		i++;
		System.out.println("Thread name is "+Thread.currentThread().getName());
	}
	
	public void print(){
		System.out.println(i);
	}

}
