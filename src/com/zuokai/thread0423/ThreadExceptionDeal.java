package com.zuokai.thread0423;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 未捕获的异常处理
 * @author Administrator
 *
 */
public class ThreadExceptionDeal implements UncaughtExceptionHandler{

	/**
	 * 发生未捕获的异常时进入的默认方法
	 */
	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {
		System.out.println("Thread name is "+Thread.currentThread().getName());
		System.out.println("Thread id is "+Thread.currentThread().getId());
		arg1.printStackTrace(); //输出异常信息
	}

}
