package org.keith.commons.memory;

public class Teeeeeeeeest {

	public static void main(String[] args) throws IllegalAccessException {  
        ObjectInfo res = new ClassIntrospector().introspect( new ObjectA() );  
        System.out.println( res.getDeepSize() );  
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
