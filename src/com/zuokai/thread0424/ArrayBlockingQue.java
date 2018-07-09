package com.zuokai.thread0424;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 数组类型阻塞队列
 * @author Administrator
 *
 */
public class ArrayBlockingQue {
	ArrayBlockingQueue<Object> abq = new ArrayBlockingQueue<>(10);//容积为10的阻塞队列
	public static void main(String[] args) throws InterruptedException {
		ArrayBlockingQue que = new ArrayBlockingQue();
		ArrayBlockingQueThread abqt1 = new ArrayBlockingQueThread(que);
		Thread t1 = new Thread(abqt1);
		
		ArrayBlockingQueThreadB abqt2 = new ArrayBlockingQueThreadB(que);
		Thread t2 = new Thread(abqt2);
		
		t1.start();
		t2.start();
		Thread.sleep(10000);
	}
	
	public boolean add(Object e){
		return abq.add(e);
	}
	
	public Object get(){
		Object o = new Object();
		try {
			o =  abq.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return o;
	}
}
