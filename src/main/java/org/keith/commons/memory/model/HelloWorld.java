package org.keith.commons.memory.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Heap:HelloWorld,SimpleDateFormat,String,LOGGER
 * Stack:message->String,formatter->SimpleDateFormat
 * ,today->String,lineNo->每个方法都有个多少行的标识
 * Method Stack:类与方法字段等的结构信息
 */
public class HelloWorld {

	private static Logger LOGGER = Logger.getLogger(HelloWorld.class.getName());
	
	public void sayHello(String message) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.YYYY");
		String today = formatter.format(new Date());
		LOGGER.info(today + ":" +message);
	}
}
