package com.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延时队列，队列中的元素需要实现Comparable和Delayed，存储元素时，可以设置在队列中存放的时间，
 * DelayQueue一般适用于定时任务和缓存系统设计
 * @author lijh
 *
 */
public class MyDelayQueue {

	public static void main(String[] args) throws InterruptedException {
		final DelayQueue<Students> dq = new DelayQueue<>();
		for (int i = 0; i < 5; i++) {
			//使用随机数让队列第一个值不一定是学生0
			Students s = new Students("学生"+i,Math.round(Math.random()*10+i));
			
			// dq.put()时会调用 Delay.compareTo()来进行排序，把延时即将到期的对象放最前面
			dq.put(s);
		}
		//执行时，每次输出的内容不一样
		//dq.peek() 获取但不移除此队列的头部；如果此队列为空，则返回 null。
		//System.out.println(dq.peek().getName());
		
		/*List<Students> list = new ArrayList<>();
		//把队列中四个元素放到list中
		dq.drainTo(list, 4);
		for (Students student : list) {
			System.out.println(student.getName());
		}*/
		//休眠两秒，等待生产者把元素放入队列
		TimeUnit.SECONDS.sleep(2);
		
		for (int i = 0; i < 5; i++) {
			// poll()方法，获取队列首个元素，并在队列中删除此元素
			System.out.println(dq.poll().getName());
		}
	}
}

class Students implements Delayed{
	
	private String name;
	private Long workTime;  //交卷时间
	private Long submitTime;//延时时间
	
	public String getName(){
		return this.name+"交卷用时："+workTime+"延时时间"+submitTime;
	} 
	
	public Students(String name,Long submitTime){
		this.name = name;
		this.workTime = submitTime;
		//把6按照分钟转换成秒，也就是说，6分钟转成秒返回值为360
		//long convert = TimeUnit.SECONDS.convert(6, TimeUnit.MINUTES);
		//System.out.println(convert);
		//NANOSECONDS 纳秒   MILLISECONDS 毫秒
		//System.nanoTime()返回最准确的可用系统计时器的当前值，以毫微秒为单位。
		this.submitTime = TimeUnit.NANOSECONDS.convert(submitTime, TimeUnit.MILLISECONDS)+System.nanoTime();
	}
	
	// 比较此对象与指定对象的顺序。根据延时时间排序，延时时间越短，放在越前面
	@Override
	public int compareTo(Delayed d) {
		Students s = (Students)d;
		return submitTime>s.submitTime?1:(submitTime<s.submitTime?-1:0);
	}

	/**
	 * 延时时间与当前时间比较，如果到达延时时间，会取出元素，并从队列中删除
	 */
	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(submitTime-System.nanoTime(), unit.NANOSECONDS);
	}

}
