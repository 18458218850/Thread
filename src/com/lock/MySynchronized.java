package com.lock;

public class MySynchronized {

	public static void main(String[] args) throws InterruptedException {
		byte[] b = new byte[1];
		Worker2 w1= new Worker2(b); 
		Worker2 w2= new Worker2(b); 
		Worker2 w3= new Worker2(b); 
		Worker2 w4= new Worker2(b); 
		Worker2 w5= new Worker2(b); 
		
		Thread t5 = new Thread(w5,"5");
		t5.setPriority(5);
		t5.start();
		
		Thread t4 = new Thread(w4,"4");
		t4.setPriority(1);
		t4.start();
		
		Thread t3 = new Thread(w3,"3");
		t3.setPriority(10);
		t3.start();
		
		Thread t2 = new Thread(w2,"2");
		t2.setPriority(1);
		t2.start();
		
		Thread t1 = new Thread(w1,"1");
		t1.setPriority(1);
		t1.start();
		
		Thread.sleep(1000);
		synchronized(b){
			b.notify();
		}
	}
}

class Worker2 implements Runnable{

	private byte[] b ;
	
	public Worker2(byte[] b){
		this.b = b;
	}
	
	@Override
	public void run() {
		synchronized(b){
				System.out.println("zzzzzzz");
				try {
					b.wait();
					System.out.println(Thread.currentThread().getName());
				} catch (InterruptedException e) {
					e.printStackTrace();
			}
		}
	}
	
}
