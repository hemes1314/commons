package org.keith.commons.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class ExceptionUtil {
	private ExceptionUtil() {
	}

	/**
	 * ����쳣��ջ
	 * 
	 * @author ���� 2016��7��19��
	 * @param thr
	 * @return
	 */
	public static String getStackTrace(Throwable thr) {
		StringWriter sw = new StringWriter();

		// Java 7 �µ� try-with-resources ���
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