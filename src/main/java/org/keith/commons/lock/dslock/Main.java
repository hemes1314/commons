package org.keith.commons.lock.dslock;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wubin
 * @date 2017/11/07
 **/
public class Main {

    public static final int THREAD_NUM = 2;

    //确保所有线程运行结束；
    public static final CountDownLatch threadSemaphore = new CountDownLatch(THREAD_NUM);

    public static void main(String[] args) {
        for(int i=0; i < THREAD_NUM; i++){
            final int threadId = i+1;
            new Thread(){
                @Override
                public void run() {
                    try{
                        DistributedLock dl = new DistributedLock("sub");
                        dl.setHandler(new Business());
                        if(dl.tryLock()) {
                            dl.excute();
                            threadSemaphore.countDown();
                        }
                    } catch (Exception e){
                        System.out.println("【第"+threadId+"个线程】 抛出的异常：");
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        try {
            threadSemaphore.await();
            System.out.println("所有线程运行结束!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
