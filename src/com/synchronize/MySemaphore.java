package com.synchronize;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
/**
 * Semaphore同步器会规定一个阀值来限制对资源的访问，使用资源时，线程最多不能超出这个阀值
 * 场景:当前驾校有2辆车，有5个学员练车，每次只有2个学员可以练车，
 * 其他学员需要等待，当有学员练完车了，可以让给下一个学员练车
 * @author lijh
 *
 */
public class MySemaphore {
	
	public static void main(String[] args) {
		//最多两个线程访问资源
		Semaphore sp = new Semaphore(2);
		//创建一个容纳5个线程的线程池
		ExecutorService pool = Executors.newFixedThreadPool(5);
		Driver d = new Driver(sp);
		Car c1 = new Car(d);
		Car c2 = new Car(d);
		Car c3 = new Car(d);
		Car c4 = new Car(d);
		Car c5 = new Car(d);
		pool.execute(c1);
		pool.execute(c2);
		pool.execute(c3);
		pool.execute(c4);
		pool.execute(c5);
		
		pool.shutdown();//关闭线程池
	}

}
//车
class Car implements Runnable{
	
	private Driver d;
	
	public Car(Driver d){
		this.d = d;
	}

	@Override
	public void run() {
		d.driverCar();
	}
}
//司机
class Driver{
	//同步计数器
	private Semaphore sp;
	
	public Driver(Semaphore sp){
		this.sp = sp;
	}
	//学员练车
	public void driverCar(){
		try {
			sp.acquire();//从此信号量获取一个许可，在提供一个许可前一直将线程阻塞，否则线程被中断。
			System.out.println(Thread.currentThread().getName()+" start");
			TimeUnit.SECONDS.sleep(2);
			System.out.println(Thread.currentThread().getName()+" end");
			sp.release();//释放一个许可，将其返回给信号量。
			//Thread.currentThread().interrupt();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
