package org.keith.commons.properties;

import java.text.MessageFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleTest {

	public static void main(String[] args) {
		
		//①加载本地化资源    
		ResourceBundle rb1 = ResourceBundle.getBundle(Constants.path);     
		ResourceBundle rb2 = ResourceBundle.getBundle(Constants.path,Locale.JAPANESE);    
		Object[] params = {"John", new GregorianCalendar().getTime()};    
		    
		String str1 = new MessageFormat(rb1.getString("C")).format(params);    
		String str2 =new MessageFormat(rb2.getString("C"),Locale.JAPANESE).format(params);   
		System.out.println(StringCharsetUtil.formatUTF8(str1));    
		System.out.println(StringCharsetUtil.formatUTF8(str2));    
	}
}
