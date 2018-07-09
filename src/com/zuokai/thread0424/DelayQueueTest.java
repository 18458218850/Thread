package com.zuokai.thread0424;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.DelayQueue;

/**
 * 阻塞队列测试
 * @author Administrator
 *
 */
public class DelayQueueTest {
	public static void main(String[] args) {
		final DelayQueue<Student> dq = new DelayQueue<>();
		for (int i = 0; i < 5; i++) {
			//使用随机数让队列第一个值不一定是学生0
			Student s = new Student("学生"+i,Math.round(Math.random()*10+i));
			
			// dq.put()时会调用 Delay.compareTo()来进行排序，把延时即将到期的对象放最前面
			dq.put(s);
		}
		//执行时，每次输出的内容不一样
		//dq.peek() 获取但不移除此队列的头部；如果此队列为空，则返回 null。
		//System.out.println(dq.peek().getName());
		
		List<Student> list = new ArrayList<>();
		//把队列中四个元素放到list中
		dq.drainTo(list, 4);
		for (Student student : list) {
			System.out.println(student.getName());
		}
	}
}
