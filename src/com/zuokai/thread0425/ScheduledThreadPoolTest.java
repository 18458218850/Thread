package com.zuokai.thread0425;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 周期延迟线程池
 * @author Administrator
 *
 */
public class ScheduledThreadPoolTest {
	public static void main(String[] args) {
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(3);
		Runnable runnable = new Runnable(){
			@Override
			public void run() {
				System.out.println("zzz");
			}
		};
		//1秒后开始执行，之后每过三秒再次执行
		pool.scheduleAtFixedRate(runnable, 1, 3, TimeUnit.SECONDS);
	}
}
