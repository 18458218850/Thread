package com.zuokai.thread0427;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;

/**
 * 自定义线程池
 * @author lijh
 *
 */
public class MyThreadPool{
	
	public static ExecutorService newMyThreadPool(int corePoolSize,int maximumPoolSize,int keepAliveTime){
		//corePoolSize 核心线程数量  maximumPoolSize 线程最大数量  keepAliveTime 线程空闲时，保持存活时间  unit 保持存活时间的单位  workQueue 队列
		//用给定的初始参数和默认的线程工厂及被拒绝的执行处理程序创建新的 ThreadPoolExecutor。
		//ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue)
		
		//handler 饱和策略
		//用给定的初始参数和默认的线程工厂创建新的 ThreadPoolExecutor。
		//ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler)
		
		//threadFactory 线程工厂
		//用给定的初始参数和默认被拒绝的执行处理程序创建新的 ThreadPoolExecutor
		//ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory)
		
		//用给定的初始参数创建新的 ThreadPoolExecutor
		//ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) 
		
		
		//handler 当队列和线程池都满了，说明线程池处于饱和状态，那么必须采取一种策略还处理新提交的任务
		//AbortPolicy:直接抛出异常，默认情况下采用这种策略
		//CallerRunsPolicy:只用调用者所在线程来运行任务
		//DiscardOldestPolicy:丢弃队列里最近的一个任务，并执行当前任务
		//DiscardPolicy:不处理，丢弃掉
		
		
		//workQueue 用于保存等待执行的任务的阻塞队列
		//ArrayBlockingQueue:是一个基于数组结构的有界阻塞队列，按FIFO原则进行排序
		//LinkedBlockingQueue:一个基于链表结构的阻塞队列，吞吐量高于ArrayBlockingQueue。静态工厂方法Excutors.newFixedThreadPool()使用了这个队列
		//SynchronousQueue: 一个不存储元素的阻塞队列。每个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，
		//吞吐量高于LinkedBlockingQueue，静态工厂方法Excutors.newCachedThreadPool()使用了这个队列
		//PriorityBlockingQueue:一个具有优先级的无限阻塞队列。
		
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5),new MyRejectedExecutionHandler());
		return threadPoolExecutor;
		
	}
	
}

class MyRejectedExecutionHandler implements RejectedExecutionHandler{

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		DiscardPolicy dp = new DiscardPolicy();
		executor.setRejectedExecutionHandler(dp);
		System.out.println("线程池和任务队列已经饱和，此线程被抛弃");
	}
	
}
