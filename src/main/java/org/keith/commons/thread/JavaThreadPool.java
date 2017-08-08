package org.keith.commons.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;

public class JavaThreadPool {
    public static void main(String[] args) {
    	
//    	//创建一个使用单个 worker 线程的 Executor，以无界队列方式来运行该线程。 
//    	ExecutorService pool = Executors.newSingleThreadExecutor(); 
//    	// 可变尺寸的线程池 ，缓冲池容量大小为Integer.MAX_VALUE
//    	ExecutorService pool = Executors.newCachedThreadPool();
		// 创建一个可重用固定线程数的线程池
//		ExecutorService pool = Executors.newFixedThreadPool(2);
    	// 创建一个大小无限的线程池。此线程池支持定时以及周期性执行任务的需求。
    	ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
		// 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
		Thread t1 = new MyThread();
		Thread t2 = new MyThread();
		Thread t3 = new MyThread();
		Thread t4 = new MyThread();
		Thread t5 = new MyThread();
		// 将线程放入池中进行执行
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		pool.execute(t4);
		pool.execute(t5);
		
//		pool.scheduleAtFixedRate(new MyThread2(), 10000, 100, TimeUnit.MILLISECONDS);
		// scheduleAtFixedRate(TimerTask task, long delay, long period)
		// task--这是被调度的任务。delay--上一个个任务开始执行之后延迟 多少秒之后再执行
		// period--这是在连续执行任务之间的毫秒的时间。

		pool.scheduleWithFixedDelay(new MyThread2(), 10000, 1000, TimeUnit.MILLISECONDS);
		// 上一个个任务结束执行之后延迟 ，多少秒之后再执行， 是从上一个任务结束时开始计算
		
		// 关闭线程池
//		pool.shutdown();
    }
}
class MyThread extends Thread {
    @Override
    public void run() {
    	System.out.println(Thread.currentThread().getName() + "正在执行。。。");
    }
}
class MyThread2 implements Runnable {
    @Override
    public void run() {
    	System.out.println(Thread.currentThread().getName() + "正在执行。。。");
    }
}