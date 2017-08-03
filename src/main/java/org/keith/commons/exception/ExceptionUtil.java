package org.keith.commons.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class ExceptionUtil {
	private ExceptionUtil() {
	}

	/**
	 * 输出异常堆栈
	 * 
	 * @author 刘俊 2016年7月19日
	 * @param thr
	 * @return
	 */
	public static String getStackTrace(Throwable thr) {
		StringWriter sw = new StringWriter();

		// Java 7 新的 try-with-resources 语句
		try (PrintWriter pw = new PrintWriter(sw)) {
			thr.printStackTrace(pw);
		}
		
		return sw.toString();
	}
	
//	public static void main(String[] args) {
//		try {
//			int i = 1 / 0;
//		} catch (Exception e) {
//			System.err.println(getExceptionStack(e));
//		}
//		
//	}
}