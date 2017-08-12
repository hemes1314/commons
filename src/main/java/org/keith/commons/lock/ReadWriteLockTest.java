package org.keith.commons.lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁
 * 读取共享数据的频率远大于修改共享数据的频率
 * 1）多个读者可以同时进行读
 * 2）写者必须互斥（只允许一个写者写，也不能读者写者同时进行）
 * 3）写者优先于读者（一旦有写者，则后续读者必须等待，唤醒时优先考虑写者）
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
