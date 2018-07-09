package com.zuokai.thread;


public class StopThread implements Runnable{
	private int i=0;

    private int j=0;
    
	@Override
	public void run() {

		synchronized (this) {//增加同步锁，确保线程安全
            ++i;
          
                //休眠10秒,模拟耗时操作
                try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					//e.printStackTrace();
					System.out.println("zzz");
				}
        	   ++j;
           System.out.println(Thread.currentThread().isInterrupted());
            
        }
		
		
	}
	public void print(){
        System.out.println("i="+i+" j="+j);
    }
	
	public static void main(String[] args) {
		System.out.println("main is start");
		StopThread stopThread = new StopThread();
		Thread thread = new Thread(stopThread,"stopThread");
		
		thread.start();
		System.out.println("StopThread is start");
		System.out.println(thread.isInterrupted());
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		System.out.println("StopThread is stop");
		thread.interrupt();
		System.out.println(thread.getState());
		stopThread.print();
	}

}
