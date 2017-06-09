package org.keith.commons.lock;

import java.util.concurrent.ConcurrentHashMap;

// 非分布式全局锁
public class GlobalLock {
	
	private ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<Object, Object>();

	/**
	 * 日志记录
	 */
//	private static Logger logger = LoggerFactory.getLogger(GlobalLock.class);
	
	private static transient GlobalLock lock = null;
	
	private GlobalLock(){}
	
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
	
	public static GlobalLock getLock () {
		if (lock == null) {
			synchronized(GlobalLock.class) {
				if (lock == null) {
					lock = new GlobalLock();
				}
			}
			
		}
		return lock;
	} 
	
	public static void main(String[] args) {
		GlobalLock globalLock = new GlobalLock();
		globalLock.lock("businessName");
		// do something
		globalLock.unlock("businessName");
	}
}
