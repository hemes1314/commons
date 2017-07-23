package org.keith.commons.lock;

import java.util.concurrent.ArrayBlockingQueue;

public class Syncronized {
	
	private static final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);

	public static void main(String[] args) throws InterruptedException {
		
		while(true) {
			// 永远在while中wait
			synchronized(queue) {
				while(queue.isEmpty()) {
					queue.wait();
				}
				// 执行操作
			}
		}
	}
}
