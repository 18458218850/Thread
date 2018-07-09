package com.synchronize;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
/**
 * CyclicBarrier 允许一组线程互相等待，直到到达某个公共屏障点，然后所有线程继续往后执行。
 * CyclicBarrier 与 CountDownLatch 区别在于 CyclicBarrier 是多个线程互相等待，
 * 			CountDownLatch 是一个线程等待多个线程完成工作，等待的对象不同了
 * 场景:公司有4个人，每天早上开晨会时，大家一起去会议室，当所有人都到达会议室，会议开始。
 * 
 * @author lijh
 *
 */
public class MyCyclicBarrier {

	public static void main(String[] args) throws InterruptedException {
		
		CyclicBarrier cb = new CyclicBarrier(4,(new Meeting()));
		
		ExecutorService pool = Executors.newFixedThreadPool(4);
		Member s1 = new Member(cb);
		Member s2 = new Member(cb);
		Member s3 = new Member(cb);
		Member s4 = new Member(cb);
		
		pool.execute(s1);
		pool.execute(s2);
		pool.execute(s3);
		TimeUnit.SECONDS.sleep(5);
		//当CyclicBarrier.await超时时，会抛出异常
		pool.execute(s4);
		pool.shutdown();
	}
}
/**
 * 成员
 * @author lijh
 *
 */
class Member implements Runnable{

	private CyclicBarrier cb;
	
	public Member(CyclicBarrier cb){
		this.cb = cb;
	}
	
	@Override
	public void run() {
			try {
				Thread.sleep(1000);
				System.out.println("成员 "+Thread.currentThread().getName()+" 已经到达会议室!");
				cb.await(3,TimeUnit.SECONDS);
				//cb.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
	}
	
}
/**
 * 会议
 * @author lijh
 *
 */
class Meeting implements Runnable{

	@Override
	public void run() {
		System.out.println("会议开始!");
	}
	
}
