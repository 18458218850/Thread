package com.zuokai.thread0424;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 懒汉式
 * @author Administrator
 */
public class MySingleton {
	private static MySingleton mySingleton = null;
	private static byte[] b = new byte[1];
	private MySingleton(){};
	static ReentrantLock rl = new ReentrantLock();
	public static MySingleton getMySingleton(){
		/*synchronized (b) {
			if(mySingleton==null){
				return new MySingleton();
			}
			return null;
		}*/
		if(mySingleton == null){
			rl.lock();
			if(mySingleton == null){
				mySingleton = new MySingleton();
			}
			rl.unlock(); //使用Lock时，一定要有解锁方法
		}
		return mySingleton;
	}
}
