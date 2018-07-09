package com.zuokai.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 实现Callable<Object>接口来创建线程 Callable<Object>接口实现的线程，可以有返回值，返回值类型在<>中指定
 * @author Administrator
 *
 */
public class ThreadC implements Callable<String>{

	@Override
	public String call() throws Exception {
		TimeUnit.SECONDS.sleep(3);//休眠三秒
		System.out.println("this is ThreadC");
		return "ThreadC";
	}
	
	public static void main(String[] args) {
		ThreadC tc = new ThreadC();
		FutureTask<String> ft = new FutureTask<>(tc);
		new Thread(ft).start();
		System.out.println("main begin!");
		
		try {
			//得到线程执行后的返回值
			String string = ft.get();
			System.out.println(string);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

}
