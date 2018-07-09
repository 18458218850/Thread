package com.zuokai.thread0425;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * AbstractQueuedSynchronizer 抽象队列化同步器
 * @author Administrator
 *
 */
public class AQSTest implements Lock{
	
	/**
	 * 内部类
	 * @author Administrator
	 */
	private static class Sync extends AbstractQueuedSynchronizer{
		
		 protected boolean isHeldExclusively() { 
		        return getState() == 1; 
		 }
		 
	     public boolean tryAcquire(int acquires) {
	         assert acquires == 1; // Otherwise unused
	         if (compareAndSetState(0, 1)) {
	           setExclusiveOwnerThread(Thread.currentThread());
	           return true;
	         }
	         return false;
	     }
	     
	     protected boolean tryRelease(int releases) {
	         assert releases == 1; // Otherwise unused
	         if (getState() == 0) throw new IllegalMonitorStateException();
	         setExclusiveOwnerThread(null);
	         setState(0);
	         return true;
	     }
	     
	     Condition newCondition() { return new ConditionObject(); }
	     
	}
	
	public Sync s = new Sync();
	
	/**
	 * 获取锁
	 */
	@Override
	public void lock() {
		s.acquire(1);
	}
	/**
	 * 如果当前线程未被中断，则获取锁。
	 */
	@Override
	public void lockInterruptibly() throws InterruptedException {
		
	}
	/**
	 * 返回绑定到此 Lock 实例的新 Condition 实例
	 */
	@Override
	public Condition newCondition() {
		return null;
	}
	/**
	 * 仅在调用时锁为空闲状态才获取该锁
	 */
	@Override
	public boolean tryLock() {
		return s.tryAcquire(1);
	}
	/**
	 * 如果锁在给定的等待时间内空闲，并且当前线程未被中断，则获取锁
	 */
	@Override
	public boolean tryLock(long arg0, TimeUnit arg1) throws InterruptedException {
		return false;
	}
	/**
	 * 释放锁
	 */
	@Override
	public void unlock() {
		s.release(1);
	}
	
	public boolean isLocked(){
		return s.isHeldExclusively();
	}
}
