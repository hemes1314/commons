package org.keith.commons.properties;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class Il8nUtil {

	private static ReloadableResourceBundleMessageSource messageResource = null;
	
	private Il8nUtil(){
	}
	
	/**
	 * ��ȡ��Դ��ʵ��
	 * @return ReloadableResourceBundleMessageSource
	 */
	private static ReloadableResourceBundleMessageSource getMessageResourceInstance() {
		if(messageResource == null) {
			messageResource = new ReloadableResourceBundleMessageSource();
			messageResource.setBasename(Constants.path);
			messageResource.setDefaultEncoding("UTF-8");
			// ˢ��ʱ�䣬Ĭ��-1������ˢ��
//			messageSource.setCacheSeconds(5);
		}
		return messageResource;
	}
	
	/**
	 * ��ȡ��Դ�ļ�ֵ
	 * @param key ��Դ�ļ�key
	 * @param args �����б�
	 * @return value
	 */
	public static String getMessage(String key, String... args) {
		
		// ����ͬkeyֻ��ȡ���һ��key��ֵ
		return getMessageResourceInstance().getMessage(key, args, null);    
	}
	
	/**
	 * ��ȡ��Դ�ļ�ֵ
	 * @param key ��Դ�ļ�key
	 * @param locale ��������
	 * @param args �����б�
	 * @return value
	 */
	public static String getMessage(String key, Locale locale, String... args) {
		
		// ��ͬkeyֻ��ȡ���һ��key��ֵ
		return getMessageResourceInstance().getMessage(key, args, locale);
	}
	
	public static void main(String[] args) {
		String value = Il8nUtil.getMessage("A");
		String value1 = Il8nUtil.getMessage("A", Locale.JAPANESE);
		String value2 = Il8nUtil.getMessage("B");
		String value3 = Il8nUtil.getMessage("C", "John", DateFormat.getInstance().format(new Date()));
		System.out.println(value);
		System.out.println(value1);
		System.out.println(value2);
		System.out.println(value3);
	}
}
