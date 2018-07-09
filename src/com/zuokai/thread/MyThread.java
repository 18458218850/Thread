package com.zuokai.thread;

public class MyThread extends Thread{
	public void run(){
		//super.run();
		try{
			Thread.sleep(500);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		System.out.println("this is MyThread!");
	}

}
