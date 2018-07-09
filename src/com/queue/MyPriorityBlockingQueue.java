package com.queue;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 优先级阻塞队列,存储元素时不会阻塞，因为是一个无界队列，获取元素时，当队列为空时，会阻塞
 * @author lijh
 *
 */
public class MyPriorityBlockingQueue {
	
	public static void main(String[] args) throws InterruptedException {
		PriorityBlockingQueue<User> pbq = new PriorityBlockingQueue<User>();
		
		Producer2 p1 = new Producer2(pbq);
		
		Producer2 p2 = new Producer2(pbq);
		
		Consumer2 c1 = new Consumer2(pbq);
		
		ExecutorService pool = Executors.newFixedThreadPool(3);
		pool.execute(p1);
		pool.execute(p2);
		pool.execute(c1);
		
		//main线程睡眠10秒，等待线程池中的线程执行
		TimeUnit.SECONDS.sleep(10);
		
		TimeUnit.SECONDS.sleep(10);
		//启动一次顺序关闭，执行以前提交的任务，但不接受新任务
		pool.shutdown();
		
	}

}

class User implements Comparable<User>{

	//优先级
	private Integer priority;
	//用户名
	private String username;
	
	public User(Integer priority,String username){
		this.priority = priority;
		this.username = username;
	}
	
	//优先级排序方法
	@Override
	public int compareTo(User o) {
		
		return this.priority.compareTo(o.getPriority());
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}

/**
 * 生产者
 * @author lijh
 *
 */
class Producer2 implements Runnable{

	boolean flag=true;
	
	private PriorityBlockingQueue<User> pbq;
	
	public Producer2(PriorityBlockingQueue<User> pbq){
		this.pbq = pbq;
	}
	
	@Override
	public void run() {
		Random r = new Random();
		int i=1;
		while(flag){
			pbq.put(new User(r.nextInt(20),"李雷"+i));
			System.out.println("生产数据成功");
			i++;
			if(i>5){//大于5的时候终止,两个生产者线程，一共会产生10个元素
				flag=false;
			}
		}
	}
	
}

/**
 * 消费者
 * @author lijh
 *
 */
class Consumer2 implements Runnable{

	boolean flag = true;
	
	private PriorityBlockingQueue<User> pbq;
	
	public Consumer2(PriorityBlockingQueue<User> pbq){
		this.pbq = pbq;
	}
	
	@Override
	public void run() {
		while(flag){
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			User user = pbq.poll();
			if(user == null){
				System.out.println("没有元素可以获取");
				flag = false;
			}else{
				System.out.println(user.getUsername()+"的优先级为"+user.getPriority());
			}
		}
	}
	
	
}