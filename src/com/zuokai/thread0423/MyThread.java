package com.zuokai.thread0423;

public class MyThread implements Runnable{

	@Override
	public void run() {
		int i = 1/0;
	}
	
	public static void main(String[] args) {
		MyThread mt = new MyThread();
		Thread t = new Thread(mt,"MyThread");
		t.setUncaughtExceptionHandler(new ThreadExceptionDeal());//设置未定义的异常处理方法
		
		t.start();
	}

}
