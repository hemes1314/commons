package org.keith.commons.properties;

import java.util.GregorianCalendar;
import java.util.Locale;

import org.springframework.context.support.ResourceBundleMessageSource;

public class ResourceBundleMessageSourceTest {

	public static void main(String[] args) {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename(Constants.path);
		messageSource.setDefaultEncoding("UTF-8");
		
		Object[] params = {"John", new GregorianCalendar().getTime()};    
		    
		//②获取格式化的国际化信息    
		String str1 = messageSource.getMessage("C",params, null);    
		System.out.println(str1);    
		
		String str2 = messageSource.getMessage("C",params, Locale.JAPANESE);    
		System.out.println(str2);    
	}
}

