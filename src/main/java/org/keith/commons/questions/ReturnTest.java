package org.keith.commons.questions;

public class ReturnTest {
    public static void main(String[] args) {
        System.out.println(returnInt());
        System.out.println(returnString());
        System.out.println(returnStringBuffer());
    }

    static int returnInt(){
        int i;
        try{
            i=1;
            return i;
        }finally {
            i=2;
        }
    }

    static String returnString(){
        String i;
        try{
            i="a";
            return i;
        }finally {
            i="b";
        }
    }

    static StringBuffer returnStringBuffer(){
        StringBuffer i = new StringBuffer("a");
        try{
            return i;
        }finally {
            i.append("b");
        }
    }
}
