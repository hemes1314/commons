package org.keith.commons.properties;

import java.util.GregorianCalendar;
import java.util.Locale;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * ��ʱˢ����Դ�ļ�����������
 */
public class ReloadableResourceBundleMessageSourceTest {

	public static void main(String[] args) throws InterruptedException {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename(Constants.path);
		messageSource.setDefaultEncoding("UTF-8");
		// ˢ��ʱ�䣬Ĭ��-1������ˢ��
//		messageSource.setCacheSeconds(5);
		
		Object[] params = {"John", new GregorianCalendar().getTime()};    
		    
		// ����ͬkeyֻ��ȡ���һ��key��ֵ
		for(int i = 0; i < 5; i++) {
			String str1 = messageSource.getMessage("C",params, null);    
			System.out.println(str1);    
			
			String str2 = messageSource.getMessage("C",params, Locale.JAPANESE);    
			System.out.println(str2);
			
			Thread.sleep(10000);
		}
	}
}

