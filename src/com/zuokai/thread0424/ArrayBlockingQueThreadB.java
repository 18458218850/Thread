package com.zuokai.thread0424;


public class ArrayBlockingQueThreadB implements Runnable{
	private ArrayBlockingQue abq;
	
	public ArrayBlockingQueThreadB(ArrayBlockingQue que){
		this.abq = que;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 15; i++) {
			Object object = abq.get();
			System.out.println("获取队列数据"+object);
		}
	}
}
