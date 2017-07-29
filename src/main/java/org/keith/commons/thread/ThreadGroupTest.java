package org.keith.commons.thread;

public class ThreadGroupTest {

	public static void main(String[] args) throws Exception {
		
		new ThreadGroupTest().test();
	}
	
	public void test() throws Exception {
		ThreadGroup tg = new ThreadGroup("my group 1");
		MyThread t1 = new MyThread(tg, "thread 1");
		t1.start();
		MyThread t2 = new MyThread(tg, "thread 1");
		t2.start();
		MyThread t3 = new MyThread(tg, "thread 1");
		t3.start();
		tg.interrupt();
	}
	
	class MyThread extends Thread {
		
		public MyThread(ThreadGroup tg, String name) {
			super(tg, name);
		}
		
	    @Override
	    public void run() {
	    	System.out.println(Thread.currentThread().getName() + "正在执行。。。");
	    }
	}
}
