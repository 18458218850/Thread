package com.zuokai.thread0423;

/**
 * 线程安全测试
 * @author Administrator
 *
 */
public class ThreadSafetyTest extends Thread{
	
	private ThreadSafety ts;
	
	public ThreadSafetyTest(ThreadSafety ts){
		this.ts = ts;
	}
	
	public void run(){
		ts.add();
	}
	
	public static void main(String[] args) throws InterruptedException {
		ThreadSafety t = new ThreadSafety();
		for (int i = 0; i < 5; i++) {
			ThreadSafetyTest tst = new ThreadSafetyTest(t);
			tst.start();
		}
		Thread.sleep(250);
		t.print();
	}

}
