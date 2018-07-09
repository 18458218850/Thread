package com.zuokai.thread0425;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 计数信号量
 * @author Administrator
 *
 */
public class SemaphoreTest implements Runnable{
	final Semaphore s = new Semaphore(2,true);//一个FIFO的只能最多二个线程进行访问的信号量
	public static void main(String[] args) {
		SemaphoreTest st = new SemaphoreTest();
		for (int i = 0; i < 5; i++) {
			Thread t = new Thread(st);
			t.start();
		}
	}

	@Override
	public void run() {
		
		try {
			TimeUnit.SECONDS.sleep(1);
			System.out.println("线程名称"+Thread.currentThread().getName());
			s.acquire();//获取执行许可
			System.out.println("线程名称"+Thread.currentThread().getName()+"开始使用资源");
			TimeUnit.SECONDS.sleep(2);
			System.out.println("线程名称"+Thread.currentThread().getName()+"执行完成");
			s.release();//释放许可
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
