package org.keith.commons.serializable.java;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.*;
import java.util.Arrays;

/**
 * @author wubin
 * @date 2017/12/15
 **/
public class Serialize {

    public static byte[] serialize(Object obj) throws Exception {
        if(obj == null) throw new NullPointerException();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(os);
        out.writeObject(obj);
        return os.toByteArray();
    }

    public static Object deserialize(byte[] by) throws Exception {
        if(by ==null) throw new NullPointerException();

        ByteArrayInputStream is = new ByteArrayInputStream(by);
        ObjectInputStream in = new ObjectInputStream(is);
        return in.readObject();
    }

    public static void main(String[] args) throws Exception {
        // 需要实现Serializable
        GoodsPrefer prefer = new GoodsPrefer();
        prefer.setSeq(1);
        prefer.setGoodsType("type1");
        prefer.setPrefer("prefer1");
        byte[] bytes = serialize(prefer);
        System.out.println("len:"+bytes.length+";bytes:"+Arrays.toString(bytes));

        Object obj = deserialize(bytes);
        System.out.println(obj);
    }
}
