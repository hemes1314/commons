package org.keith.commons.lock;

import java.util.concurrent.ConcurrentHashMap;

// 非分布式全局锁
public class GlobalLockTest {
	
	private ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<Object, Object>();

	private static transient GlobalLockTest lock = null;
	
	private GlobalLockTest(){}
	
	public void lock(Object o) {		
		while(!tryLock(o)) {
			//wait
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
//				logger.error("OrderLock error: {}", e);
			}
		}
	}
	
	public boolean tryLock(Object o) {
		if (!map.contains(o)) {
			synchronized(map) {
				if (!map.contains(o)) {
					map.put(o, o);
					return true;
				}
			}
		}
		return false;
	}
	
	public void unlock(Object o) {
		map.remove(o);
	}
	
	public static GlobalLockTest getLock () {
		if (lock == null) {
			synchronized(GlobalLockTest.class) {
				if (lock == null) {
					lock = new GlobalLockTest();
				}
			}
		}
		return lock;
	} 
	
	public static void main(String[] args) {
		GlobalLockTest globalLock = new GlobalLockTest();
		globalLock.lock("businessName");
		// do something
		globalLock.unlock("businessName");
	}
}
