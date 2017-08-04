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
	 * 获取资源绑定实例
	 * @return ReloadableResourceBundleMessageSource
	 */
	private static ReloadableResourceBundleMessageSource getMessageResourceInstance() {
		if(messageResource == null) {
			messageResource = new ReloadableResourceBundleMessageSource();
			messageResource.setBasename(Constants.path);
			messageResource.setDefaultEncoding("UTF-8");
			// 刷新时间，默认-1，永不刷新
//			messageSource.setCacheSeconds(5);
		}
		return messageResource;
	}
	
	/**
	 * 获取资源文件值
	 * @param key 资源文件key
	 * @param args 参数列表
	 * @return value
	 */
	public static String getMessage(String key, String... args) {
		
		// 用相同key只获取最后一个key的值
		return getMessageResourceInstance().getMessage(key, args, null);    
	}
	
	/**
	 * 获取资源文件值
	 * @param key 资源文件key
	 * @param locale 地区代码
	 * @param args 参数列表
	 * @return value
	 */
	public static String getMessage(String key, Locale locale, String... args) {
		
		// 相同key只获取最后一个key的值
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
