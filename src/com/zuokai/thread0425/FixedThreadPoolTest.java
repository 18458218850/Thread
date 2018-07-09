package com.zuokai.thread0425;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 固定容量的线程池
 * @author Administrator
 *
 */
public class FixedThreadPoolTest {

	public static void main(String[] args) {
		ExecutorService es = Executors.newFixedThreadPool(2);//最多容纳2个线程的线程池
		
		for (int i = 0; i < 7; i++) {//创建7个线程
			int no = i;
			Runnable runnable = new Runnable(){
				@Override
				public void run() {
					
					try {
						System.out.println("into "+no);
						Thread.sleep(1000L);
						System.out.println("end "+no);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			es.execute(runnable);//线程放入线程池
		}
		es.shutdown();//执行完线程后，关闭ExecutorService,
		System.out.println("Main end!");
	}
}
