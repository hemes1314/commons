package org.keith.commons.properties;

import java.io.UnsupportedEncodingException;

public class StringCharsetUtil {

	public static String formatUTF8(String str) {
		String formatValue = null;
		try {
			formatValue = new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}  
		return formatValue;
	}
}
