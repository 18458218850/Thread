package com.zuokai.thread;

public class TestThread {

	public static void main(String[] args) {
		//new MyThread().start();
		ThreadB b = new ThreadB();
		new Thread(b).start();
		System.out.println("this is main thread!");
		System.out.println("得到当前线程的优先级"+Thread.currentThread().getPriority()+"得到当前线程的名字"+Thread.currentThread().getName());
		
	}
}
