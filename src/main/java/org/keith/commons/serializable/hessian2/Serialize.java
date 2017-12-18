package org.keith.commons.serializable.hessian2;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import org.keith.commons.serializable.java.GoodsPrefer;

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
        Hessian2Output h2o = new Hessian2Output(os);

        h2o.startMessage();
        h2o.writeObject("abc");
        h2o.completeMessage();
        h2o.close();

        byte[] buffer = os.toByteArray();
        os.close();
        return buffer;
    }

    public static Object deserialize(byte[] by) throws IOException {
        if(by == null) throw new NullPointerException();

        ByteArrayInputStream is = new ByteArrayInputStream(by);
        Hessian2Input h2i = new Hessian2Input(is);

        h2i.startMessage();
        Object obj = h2i.readObject();
        h2i.completeMessage();
        h2i.close();
        is.close();
        return obj;
    }

    public static void main(String[] args) throws Exception {
        // 无需实现Serializable
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
