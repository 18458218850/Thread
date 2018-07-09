package com.zuokai.thread0424;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 计数器测试
 * @author Administrator
 *
 */
public class CountDownLatchTest {
	
	public static void main(String[] args) {
		CountDownLatch cdl = new CountDownLatch(3);
		Worker w1 = new Worker(cdl);
		Thread t1 = new Thread(w1);
		
		WorkerB w2 = new WorkerB(cdl);
		Thread t2 = new Thread(w2,"w2");
		
		Worker w3 = new Worker(cdl);
		Thread t3 = new Thread(w3);
		
		t1.start();
		t2.start();
		t3.start();
		
		try {
			cdl.await();//等待三个线程结束才执行后面的
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(Thread.currentThread().getName()+"执行完成");
	}
}

class Worker implements Runnable{
	
	private CountDownLatch cdl;
	public Worker(CountDownLatch cdl){
		this.cdl = cdl;
	}
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+"开始工作");
		try {
			TimeUnit.SECONDS.sleep(1);//睡眠一秒
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+"结束工作");
		cdl.countDown();//计数器减1
		//System.out.println("计数器剩余值"+cdl.getCount());
	}
	
}

class WorkerB implements Runnable{
	private CountDownLatch cdl;
	public WorkerB(CountDownLatch cdl){
		this.cdl = cdl;
	}
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+"开始工作");
		try {
			TimeUnit.SECONDS.sleep(1);//睡眠一秒
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+"结束工作");
		cdl.countDown();//计数器减1
		//System.out.println("计数器剩余值"+cdl.getCount());
	}
	
	
}
