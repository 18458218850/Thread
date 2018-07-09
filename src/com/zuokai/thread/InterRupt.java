package com.zuokai.thread;

/**
 * Interrupt()方法是终止线程的，和stop()方法一样，因为stop()方法不安全，所以改用interrupt()
 * @author Administrator
 *
 */
public class InterRupt implements Runnable{
	
	int i =0;

	@Override
	public void run() {
		
		synchronized (this) {
			
			i++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
			if(Thread.currentThread().isAlive())
				System.out.println("当前线程第"+i+"次执行");
			try {
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
			System.out.println("zzz");
		}
	}
	
	public void print(){
		System.out.println(i);
	}

	public static void main(String[] args) throws InterruptedException {
		InterRupt rupt = new InterRupt();
		Thread t = new Thread(rupt,"rupt");
		t.start();
		Thread.sleep(1000);//休眠一秒，让rupt运行
		t.interrupt();//停止rupt线程
		rupt.print();//打印i
	}
}
