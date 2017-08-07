package org.keith.commons.vertx;

import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;

public class Business {

	public static void main(String[] args) {
		Init.init();
		
		EventBus eb = Init.messageChannel().eventBus();
		JsonObject parms = new JsonObject();
		parms.put("param1", "abc");
		parms.put("param2", 123);
		
		eb.<JsonObject> send("address1", parms, message -> {
			if (message.succeeded()) {
				JsonObject result = message.result().body();
				System.out.println(result);
			}
		});
	}
}
