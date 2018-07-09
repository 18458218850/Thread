package com.synchronize;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 同步计数器
 * @author lijh
 *
 */
public class MyCountDownLatch {
	 
	public static void main(String[] args) {
	     CountDownLatch doneSignal = new CountDownLatch(5);
	     //定长线程池
	     ExecutorService pool = Executors.newFixedThreadPool(1);
	     for (int i = 0; i < 5; ++i){
	    	 pool.execute(new WorkerRunnable(doneSignal, i)); 
	     } 
	    	
	     try {
	    	 //在计数器为0之前会一直等待
			 doneSignal.await();
		 } catch (InterruptedException e) {
			 e.printStackTrace();
		 } 
	     //关闭线程池
	     pool.shutdown();
	     
	     System.out.println("main 线程结束");
	}
}

class WorkerRunnable implements Runnable {
    private final CountDownLatch doneSignal;
    private final int i;
    WorkerRunnable(CountDownLatch doneSignal, int i) {
       this.doneSignal = doneSignal;
       this.i = i;
    }
    public void run() {
    	//每个线程做完自己的事情后，计数器减1
        doWork(i);
        doneSignal.countDown();
        Thread.currentThread().interrupt();
    }
    
    void doWork(int i) {
	    try {
		    TimeUnit.SECONDS.sleep(1);
		    System.out.println(doneSignal.getCount());
	    } catch (InterruptedException e) {
		    e.printStackTrace();
	    }
	}
}

