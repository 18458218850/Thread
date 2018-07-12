package com.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁
 * @author lijh
 *
 */
public class MyReentrantLock {
	
	public static void main(String[] args) throws InterruptedException {
		
		Worker1 worker1 = new Worker1(1);
		Worker1 worker2 = new Worker1(2);
		
		Thread t1 = new Thread(worker1,"t1");
		Thread t2 = new Thread(worker2,"t2");
		
		t1.start();
		t2.start();
		//t1.join();//main线程等待t1线程结束
		//t2.join();//main线程等待t2线程结束
		TimeUnit.SECONDS.sleep(3);
		t2.interrupt();//中断线程t2，t2释放掉本身的锁，所有t1会获取到rt2然后执行下去
		System.out.println("main 线程执行完成!");
	}
}

class Worker1 implements Runnable{

	static ReentrantLock rt1 = new ReentrantLock();
	static ReentrantLock rt2 = new ReentrantLock();
	private int i;
	public Worker1(int i){
		this.i = i;
	}
	@Override
	public void run() {
		try{
			if(i==1){
				//rt1.lock();
				rt1.lockInterruptibly();
				TimeUnit.SECONDS.sleep(1);
				//rt2.lock();
				rt2.lockInterruptibly();
			}else{
				//rt2.lock();
				rt2.lockInterruptibly();
				TimeUnit.SECONDS.sleep(1);
				//rt1.lock();
				rt1.lockInterruptibly();
			}
			System.out.println(Thread.currentThread().getName()+" 线程执行结束!");
		} catch (InterruptedException e) {
			//e.printStackTrace();
		}finally {
			System.out.println(Thread.currentThread().getName()+" 线程释放锁");
			if(rt1.isHeldByCurrentThread())//查询当前线程是否持有此锁
				rt1.unlock();
			if(rt2.isHeldByCurrentThread())//查询当前线程是否持有此锁
			rt2.unlock();
		}
	}
	
}
