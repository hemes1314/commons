package org.keith.commons.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ������
 */
public class LockTest {
	
    private static java.util.concurrent.locks.Lock lock = new ReentrantLock();  
    
    public static final Integer PICK_RF_LOCK = new Integer(1);
    
    public static void main(String[] args) {  
        invokeMethod();  
        invokeMethod2();
    }  
  
    // ��ʽ�ͷ���
    private static void invokeMethod() {  
    	
    	// lock.lock(); // �����������ȴ�
    	if(lock.tryLock()) { // �����������, ���ᵼ�µ�ǰ�̱߳�����, ��ǰ�߳���Ȼ��������ִ�д���.
	    		
	    	try {  
	            // access the resource protected by this lock  
	        } finally { 
	        	// ������finally���ͷ�
	            // �ͷ���  
	        	// ����̲߳���������, ȴִ�и÷���, ���ܵ����쳣�ķ���
	            lock.unlock();  
	        }  
    	} else {
    		// ...
    	}
    }  
    
    // ��ʽ�ͷ���
    private static void invokeMethod2() {
    	synchronized(PICK_RF_LOCK) {
    		// access the resource protected by this lock  
    	}
    }
}
