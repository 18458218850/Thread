package com.zuokai.thread0424;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延时队列
 * @author Administrator
 *
 */
public class Student implements Delayed{
	
	private String name;
	private Long workTime;
	private Long submitTime;
	
	public String getName(){
		return this.name+"交卷用时："+workTime;
	} 
	
	public Student(String name,Long submitTime){
		this.name = name;
		this.workTime = submitTime;
		//把6按照分钟转换成秒，也就是说，6分钟转成秒返回值为360
		//long convert = TimeUnit.SECONDS.convert(6, TimeUnit.MINUTES);
		//System.out.println(convert);
		//NANOSECONDS 纳秒   MILLISECONDS 毫秒
		this.submitTime = TimeUnit.NANOSECONDS.convert(submitTime, TimeUnit.MILLISECONDS)+System.nanoTime();
	}
	
	// 比较此对象与指定对象的顺序。
	@Override
	public int compareTo(Delayed d) {
		Student s = (Student)d;
		return submitTime>s.submitTime?1:(submitTime<s.submitTime?-1:0);
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(submitTime-System.nanoTime(), unit.NANOSECONDS);
	}

}
