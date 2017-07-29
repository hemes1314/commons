package org.keith.commons.memory.size;

import com.javamex.classmexer.MemoryUtil;

/**
Tomcat��VM arguments:
-Dcatalina.base="D:\software\apache-tomcat-8.0.24" 
-Dcatalina.home="D:\software\apache-tomcat-8.0.24" -Dwtp.deploy="D:\software\apache-tomcat-8.0.24\webapps" 
-Djava.endorsed.dirs="D:\software\apache-tomcat-8.0.24\endorsed"
-noverify -javaagent:D:\classmexer.jar

Debug��VM arguments:
-javaagent:D:\classmexer.jar
 */
public class Teeeeeeeeest {

	public static void main(String[] args) throws IllegalAccessException {  
//        ObjectInfo res = new ClassIntrospector().introspect( new ObjectA() );  
//        System.out.println( res.getDeepSize() );  
		System.out.println(MemoryUtil.deepMemoryUsageOf(new Object()));
    }  
   
    private static class ObjectA {  
        String str;  // 4  
        int i1; // 4  
        byte b1; // 1  
        byte b2; // 1  
        int i2;  // 4   
        ObjectB obj; //4  
        byte b3;  // 1  
    }  
      
    private static class ObjectB {  
          
    }  
    
}
