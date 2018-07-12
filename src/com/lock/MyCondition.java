package com.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyCondition {
	
	
	public static void main(String[] args){
		List<Integer> list = new ArrayList<Integer>(5);
		//int [] a = new int[5];
		
		ReentrantLock rt = new ReentrantLock();
		Condition producerCondi = rt.newCondition();
		Condition consumerCondi = rt.newCondition();
		
		Producer p1 = new Producer(list,producerCondi,consumerCondi,rt);
		//Producer p2 = new Producer(list,producerCondi,consumerCondi,rt);
		
		Consumer c1 = new Consumer(list,producerCondi,consumerCondi,rt);
		
		ExecutorService pool = Executors.newFixedThreadPool(3);
		pool.execute(p1);
		//pool.execute(p2);
		pool.execute(c1);
		
		
		//pool.shutdown();
	}

}

/**
 * 生产者
 * @author lijh
 *
 */
class Producer implements Runnable{
	
	private List<Integer> list;
	private Lock lock;
	private Condition producerCondi;
	private Condition consumerCondi;
	
	public Producer(List<Integer> list,Condition producerCondi,Condition consumerCondi,Lock lock){
		this.list = list;
		this.producerCondi = producerCondi;
		this.consumerCondi = consumerCondi;
		this.lock = lock;
	}

	@Override
	public void run() {
		lock.lock();
		while(list.size() < 5){//
			Random r = new Random();
			int i=0;
			list.add(i = r.nextInt(20));
			System.out.println("数据"+i+"插入成功!");
		}
		
		try {
			System.out.println("list已经满了，生产者进入等待状态");
			
			producerCondi.await();
			System.out.println("调用await()方法后，什么时候执行这个？？？");
			consumerCondi.signalAll();
			System.out.println("Producer");
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
			
	}
}

class Consumer implements Runnable{
	private List<Integer> list;
	private Lock lock;
	private Condition producerCondi;
	private Condition consumerCondi;
	
	public Consumer(List<Integer> list,Condition producerCondi,Condition consumerCondi,Lock lock){
		this.list = list;
		this.producerCondi = producerCondi;
		this.consumerCondi = consumerCondi;
		this.lock = lock;
	}
	
	@Override
	public void run() {
		lock.lock();
		while(list.size() >0){
			System.out.println(list.remove(0));
		}
		
		try {
			System.out.println("list为空，消费者进入等待状态");
			producerCondi.signalAll();
			consumerCondi.await();
			System.out.println("Consumer");
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
		
	}
	
}