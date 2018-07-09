package com.zuokai.thread0427;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureTaskTest {

	public static void main(String[] args) {
		Worker w1 =  new Worker("张三");
		FutureTask<String> ft1 = new FutureTask<>(w1);
		//因为FutureTask<V>实现了Runnable,所以可以使用ft作为参数运行线程
		//因为FutureTask<V>实现了Callable<V>，
		//Callable<V>接口类似于 Runnable，两者都是为那些其实例可能被另一个线程执行的类设计的。
		//但是 Runnable 不会返回结果，并且无法抛出经过检查的异常
		Thread t1 = new Thread(ft1);
		t1.start();
		try {
			//阻塞等到返回结果，然后才会往下执行
			System.out.println(ft1.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		Worker w2 =  new Worker("李四");
		FutureTask<String> ft2 = new FutureTask<>(w2);
		
		Thread t2 = new Thread(ft2);
		t2.start();
		
		try {
			//阻塞等到返回结果，然后才会往下执行
			System.out.println(ft2.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}		
		System.out.println("main stop");
	}
}

class Worker implements Callable<String>{
	
	private String name;
	
	public Worker(String name){
		this.name = name;
	}
	
	@Override
	public String call() throws Exception {		
		TimeUnit.SECONDS.sleep(1);
		return name+"工作完成";
	}
}
