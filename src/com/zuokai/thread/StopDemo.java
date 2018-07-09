package com.zuokai.thread;

public class StopDemo {

    public static void main(String[] args) {
        StopThread thread=new StopThread();
        thread.start();
        try {
            //休眠1秒，确保i变量自增成功
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            //一般会处理中断异常，这里作为例子就直接打印到控制台了
            e.printStackTrace();
        	
        }
        //暂停线程
        thread.interrupt();
        System.out.println(thread.isInterrupted());
        while(thread.isAlive()){//确保线程已经终止

        }
        //输出结果
        thread.print();
    }
    
    private static class StopThread extends Thread{

        private int i=0;

        private int j=0;

        @Override
        public void run(){
            synchronized (this) {//增加同步锁，确保线程安全
                ++i;
                //休眠10秒,模拟耗时操作
                try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					//e.printStackTrace();
					System.out.println("zzz");
				}
            	++j;
               System.out.println(Thread.currentThread().isInterrupted());
                
            }
        }

        public void print(){
            System.out.println("i="+i+" j="+j);
        }
    }
}