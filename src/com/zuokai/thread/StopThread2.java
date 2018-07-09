package com.zuokai.thread;

public class StopThread2 implements Runnable{

	@Override
	public void run() {
		boolean flag = true;
		while(flag){
			
			System.out.println(Thread.currentThread().getName());
			try {
				Thread.sleep(100);
				
			} catch (InterruptedException e) {
				//e.printStackTrace();
				break;
			}
			if(Thread.currentThread().isAlive()){
				flag=!flag;
				System.out.println("StopThread2 stop");
			}
		}
		
	}

	
	public static void main(String[] args) {
		Thread thread = new Thread(new StopThread2(),"StopThread2");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("main start");
		thread.start();
		System.out.println("StopThread2 start");
		//当线程调用次方法后，线程将销毁，在run()方法中可以调用 isAlive()方法来判断线程是否销毁
		thread.interrupt();
		System.out.println(thread.isAlive());
		System.out.println(thread.isInterrupted());
	}
}
