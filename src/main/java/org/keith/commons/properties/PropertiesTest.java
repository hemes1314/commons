package org.keith.commons.properties;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesTest {

	public static void main(String[] args) throws IOException {
		Properties prop = new Properties();
		prop.load(PropertiesTest.class.getClassLoader().getResourceAsStream(Constants.path+".properties"));  
		System.out.println(prop.get("A"));
		
		Properties prop2 = new Properties();         
		prop2.load(new InputStreamReader(PropertiesTest.class.getClassLoader().getResourceAsStream(Constants.path+".properties"), "UTF-8"));
		System.out.println(prop2.get("A")+"---"+prop2.get("B"));
	}
}
