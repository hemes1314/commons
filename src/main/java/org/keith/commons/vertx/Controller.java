//package org.keith.commons.vertx;
//
//import io.vertx.core.Handler;
//import io.vertx.core.Vertx;
//import io.vertx.core.eventbus.EventBus;
//import io.vertx.core.eventbus.Message;
//import io.vertx.core.json.JsonObject;
//
//public class Controller {
//
//	private static Controller instance;
//
//	public static Controller instance() {
//		if (instance == null) {
//			synchronized (Controller.class) {
//				if (instance == null) {
//					instance = new Controller();
//				}
//			}
//		}
//		return instance;
//	}
//
//	private EventBus eb;// 事件总线
//
//	public boolean init(Vertx vertx) {
//		this.eb = vertx.eventBus();
//		//启动拣货工作反馈单元
////		vertx.deployVerticle();
////		vertx.deployVerticle();
//		return true;
//	}
//
//	public void start() {
//		eb.<JsonObject> consumer("address1", businessHandler);
//	}
//
//	private Handler<Message<JsonObject>> businessHandler = message -> {
//		JsonObject param = message.body();
//		String param1 = param.getString("param1");
//		int param2 = param.getInteger("param2");
//
//		System.out.println(param1 + ";" + param2);
//
//		JsonObject response = new JsonObject();
//		response.put("code", 1);
//		response.put("msg", "fail");
//		message.reply(response);
//		return;
//	};
//}
