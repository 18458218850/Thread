package com.zuokai.thread0424;

import java.util.concurrent.locks.ReentrantLock;


/**
 * Lock锁
 * @author Administrator
 *
 */
public class ThreadLock implements Runnable{
	private ReentrantLock rl = new ReentrantLock();
	@Override
	public void run() {
		try {
			//rl.lock();
			System.out.println("Thread name is"+Thread.currentThread().getName());
			Thread.sleep(1000);
			System.out.println("Thread name is"+Thread.currentThread().getName());
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}finally {
			//rl.unlock();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		ThreadLock tl = new ThreadLock();
		Thread t = new Thread(tl,"123");
		Thread t1 = new Thread(tl,"456");
		t.start();
		t1.start();
		
		Thread.sleep(3000);
	}
	
}
