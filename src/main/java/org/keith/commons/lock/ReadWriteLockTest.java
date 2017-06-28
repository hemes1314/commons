package org.keith.commons.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ��д��
 * ��ȡ�������ݵ�Ƶ��Զ�����޸Ĺ������ݵ�Ƶ��
 * 1��������߿���ͬʱ���ж�
 * 2��д�߱��뻥�⣨ֻ����һ��д��д��Ҳ���ܶ���д��ͬʱ���У�
 * 3��д�������ڶ��ߣ�һ����д�ߣ���������߱���ȴ�������ʱ���ȿ���д�ߣ�
 */
public class ReadWriteLockTest {
	private static ReadWriteLock lock = new ReentrantReadWriteLock();  
    private static Person person = new ReadWriteLockTest().new Person("David Beckham", true);  
  
    public static void main(String[] args) {  
        new Thread() {  
            public void run() {  
                while(true) {  
                    try {  
                        lock.readLock().lock();  
                        System.out.print("name = " + person.getName());  
                        System.out.println(", isMan = " + person.isMan());  
                    } finally {  
                        lock.readLock().unlock();  
                    }  
                }  
            };  
        }.start();  
        new Thread() {  
            public void run() {  
                boolean state = true;  
                while(true) {  
                    try {  
                        lock.writeLock().lock();  
                        if (state) {  
                            person.setName("Lady GaGa");  
                            person.setMan(false);  
                            state = false;  
                        } else {  
                            person.setName("David Beckham");  
                            person.setMan(true);  
                            state = true;  
                        }  
                          
                    } finally {  
                        lock.writeLock().unlock();  
                    }  
                }  
            };  
        }.start();  
    }  
    
    public class Person {  
        private String name;  
        private boolean isMan;  
      
        public Person(String name, boolean isMan) {  
            this.name = name;  
            this.isMan = isMan;  
        }  
      
        public String getName() {  
            return name;  
        }  
      
        public void setName(String name) {  
            this.name = name;  
        }  
      
        public boolean isMan() {  
            return isMan;  
        }  
      
        public void setMan(boolean isMan) {  
            this.isMan = isMan;  
        }  
    }  
}
