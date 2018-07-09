package com.zuokai.thread;



public class ThreadB implements Runnable{

	@Override
	public void run() {
		try{
			Thread.sleep(1000);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		System.out.println("This is ThreadB");
		
	}

}
