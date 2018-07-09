package com.queue;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 链表阻塞队列测试，存储和获取都可能会阻塞队列，生产者和消费者使用两把锁
 * @author lijh
 *
 */
public class MyLinkedBlockingQueue {
	
	public static void main(String[] args) throws InterruptedException {
		//ArrayBlockingQueue<String> abq = new ArrayBlockingQueue<String>(10);
		LinkedBlockingQueue<String> abq = new LinkedBlockingQueue<String>(5);
		//三个生产者
		Producer p1 = new Producer(abq);
		Producer p2 = new Producer(abq);
		Producer p3 = new Producer(abq);
		//一个消费者
		Consumer queue1 = new Consumer(abq);
		//生成一个缓存线程池
		ExecutorService pool = Executors.newCachedThreadPool();
		
		pool.execute(p1);
		pool.execute(p2);
		pool.execute(p3);
		pool.execute(queue1);
		
		//当前线程睡眠10秒，等待线程池中的线程执行
		TimeUnit.SECONDS.sleep(10);
		
		//调用方法停止线程
		p1.myStop();
		p2.myStop();
		p3.myStop();
		
		TimeUnit.SECONDS.sleep(10);
		//启动一次顺序关闭，执行以前提交的任务，但不接受新任务
		pool.shutdown();
	}
	
}

/**
 * 消费者
 * @author lijh
 *
 */
class Consumer implements Runnable{
	//注入队列
	//private ArrayBlockingQueue<String> abq;
	private LinkedBlockingQueue<String> abq;
	
	private Boolean flag = true;
	
	/*public Consumer(ArrayBlockingQueue<String> abq){
		this.abq = abq;
	}*/
	
	public Consumer(LinkedBlockingQueue<String> abq){
		this.abq = abq;
	}
	
	@Override
	public void run() {
		System.out.println("启动消费者线程");
		while(flag){
			try {
				// 获取并移除此队列的头部，在指定的等待时间前等待可用的元素（如果有必要）。
				String date = abq.poll(2, TimeUnit.SECONDS);
				if(date != null){
					System.out.println("消费者得到数据 "+date);
					//休眠一秒
					TimeUnit.SECONDS.sleep(1);
				}else{//没有获取到数据,则退出循环
					//flag = false;
					System.out.println("没有可获取的数据");
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
		
	}
	
	
}

/**
 * 生产者
 * @author lijh
 *
 */
class Producer implements Runnable{

	//注入队列
	//private ArrayBlockingQueue<String> abq;
	private LinkedBlockingQueue<String> abq;
	//线程执行标准为
	boolean flag = true;
	
	/*public Producer(ArrayBlockingQueue<String> abq){
		this.abq = abq;
	}*/
	public Producer(LinkedBlockingQueue<String> abq){
		this.abq = abq;
	}
	
	public void myStop() {
		flag = false;
	}
	
	@Override
	public void run() {
		Random r = new Random();
		System.out.println("启动生产者线程");
		while(flag){
			System.out.println("开始生产数据"+ Thread.currentThread().getName());
			try {
				TimeUnit.SECONDS.sleep(1);
				// 将指定元素插入此队列中，在到达指定的等待时间前等待可用的空间（如果有必要）。
				if(!abq.offer("date "+r.nextInt(50), 2, TimeUnit.SECONDS)){
					System.out.println("放入数据失败。。。");
					myStop();
				}else{
					System.out.println("数据生产成功"+ Thread.currentThread().getName());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				//如果出现异常，则终止线程
				Thread.currentThread().interrupt();
			}
			
		}
	}
	
}


