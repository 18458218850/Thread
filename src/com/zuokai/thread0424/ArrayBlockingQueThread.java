package com.zuokai.thread0424;


public class ArrayBlockingQueThread implements Runnable{

	private ArrayBlockingQue abq;
	
	public ArrayBlockingQueThread(ArrayBlockingQue abq){
		this.abq = abq;
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 15; i++) {
			abq.add(i);
			System.out.println("第"+(i+1)+"次存入数据为："+i);
			
		}
	}

}
