package org.keith.commons.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;

public class Init {
	
	private final static Vertx vertx;
	
	static {
		VertxOptions options = new VertxOptions();
		//���ִ��ʱ��10S,����10s,����������
		options.setMaxEventLoopExecuteTime(10000000000L);
		vertx = Vertx.vertx(options);
	}
	
	public static Vertx messageChannel() {
		return Init.vertx;
	}

	public static EventBus eventBus() {
		return vertx.eventBus();
	}
	
	public static void init() {
		if(Controller.instance().init(vertx)) {
			Controller.instance().start();
		}
	}
	
	public static void main(String[] args) {
		
	}
}
