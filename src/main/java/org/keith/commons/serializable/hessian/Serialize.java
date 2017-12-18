package org.keith.commons.serializable.hessian.proxy;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author wubin
 * @date 2017/12/15
 **/
public class Serialize {

    public static byte[] serialize(Object obj) throws IOException {
        if(obj == null) throw new NullPointerException();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(os);
        ho.writeObject(obj);
        return os.toByteArray();
    }

    public static Object deserialize(byte[] by) throws IOException {
        if(by == null) throw new NullPointerException();

        ByteArrayInputStream is = new ByteArrayInputStream(by);
        HessianInput hi = new HessianInput(is);
        return hi.readObject();
    }

    public static void main(String[] args) throws Exception {
        // 需要实现Serializable
        GoodsPrefer prefer = new GoodsPrefer();
        prefer.setSeq(1);
        prefer.setGoodsType("type1");
        prefer.setPrefer("prefer1");
        byte[] bytes = serialize(prefer);
        System.out.println(Arrays.toString(bytes));
        Object obj = deserialize(bytes);
        System.out.println(obj);
    }
}
