package org.keith.commons.properties;

import java.util.GregorianCalendar;
import java.util.Locale;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 定时刷新资源文件，不必重启
 */
public class ReloadableResourceBundleMessageSourceTest {

	public static void main(String[] args) throws InterruptedException {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename(Constants.path);
		messageSource.setDefaultEncoding("UTF-8");
		// 刷新时间，默认-1，永不刷新
//		messageSource.setCacheSeconds(5);
		
		Object[] params = {"John", new GregorianCalendar().getTime()};    
		    
		// 用相同key只获取最后一个key的值
		for(int i = 0; i < 5; i++) {
			String str1 = messageSource.getMessage("C",params, null);    
			System.out.println(str1);    
			
			String str2 = messageSource.getMessage("C",params, Locale.JAPANESE);    
			System.out.println(str2);
			
			Thread.sleep(10000);
		}
	}
}

