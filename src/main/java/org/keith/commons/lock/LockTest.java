package org.keith.commons.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 互斥锁
 */
public class LockTest {
	
    private static java.util.concurrent.locks.Lock lock = new ReentrantLock();  
    
    public static final Integer PICK_RF_LOCK = new Integer(1);
    
    public static void main(String[] args) {  
        invokeMethod();  
        invokeMethod2();
    }  
  
    // 显式释放锁
    private static void invokeMethod() {  
    	
    	// lock.lock(); // 锁不可用则会等待
    	if(lock.tryLock()) { // 如果锁不可用, 不会导致当前线程被禁用, 当前线程仍然继续往下执行代码.
	    		
	    	try {  
	            // access the resource protected by this lock  
	        } finally { 
	        	// 必须在finally中释放
	            // 释放锁  
	        	// 如果线程并不持有锁, 却执行该方法, 可能导致异常的发生
	            lock.unlock();  
	        }  
    	} else {
    		// ...
    	}
    }  
    
    // 隐式释放锁
    private static void invokeMethod2() {
    	synchronized(PICK_RF_LOCK) {
    		// access the resource protected by this lock  
    	}
    }
}
