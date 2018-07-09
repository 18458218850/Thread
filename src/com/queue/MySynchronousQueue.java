package com.queue;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

/**
 * 同步阻塞队列不会存储元素，每一个poll()方法必须等待一个take()操作，否则不能继续poll()
 * @author lijh
 *
 */
public class MySynchronousQueue {

    public static void main(String[] args) {
        final SynchronousQueue<String> synchronousQueue = new SynchronousQueue<String>();

        Producer3 p1 = new Producer3(synchronousQueue);

        Consumer3 p2 = new Consumer3(synchronousQueue);

        Consumer3 p3 = new Consumer3(synchronousQueue);
        
        //生成一个定长线程池
  		ExecutorService pool = Executors.newFixedThreadPool(3);
  		
  		pool.execute(p1);
  		pool.execute(p2);
  		pool.execute(p3);

    }
}

/**
 * 消费者
 * @author lijh
 *
 */
class Consumer3 implements Runnable {

    protected SynchronousQueue<String> blockingQueue;

    public Consumer3(SynchronousQueue<String> queue) {
        this.blockingQueue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
            	//从同步队列中获取一个元素，如果队列中没有元素，则阻塞
                String data = blockingQueue.take();
                System.out.println(Thread.currentThread().getName()+ " take(): " + data);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

/**
 * 生产者
 * @author lijh
 *
 */
class Producer3 implements Runnable {

    protected SynchronousQueue<String> blockingQueue;
    final Random random = new Random();

    public Producer3(SynchronousQueue<String> queue) {
        this.blockingQueue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String data = UUID.randomUUID().toString();
                System.out.println("Put: " + data);
                //往同步队列中插入一个元素，如果队列不为空则阻塞
                blockingQueue.put(data);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
