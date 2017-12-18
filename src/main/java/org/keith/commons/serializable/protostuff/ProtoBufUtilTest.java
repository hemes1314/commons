package org.keith.commons.serializable.protostuff;

import java.util.Arrays;

/**
 * @author wubin
 * @date 2017/12/18
 **/
public class ProtoBufUtilTest {

    public static void main(String[] args) {
        // 无需实现Serializable
        Student stu = new Student();
        stu.setName("lance");
        stu.setAge(28);
        stu.setStudentNo("2017070122");
        stu.setSchoolName("BJUT");

        byte[] serializerResult = ProtoBufUtil.serializer(stu);
        System.out.println("serializer result:"+ Arrays.toString(serializerResult));
        Student deSerializerResult = ProtoBufUtil.deserializer(serializerResult, Student.class);
        System.out.println("deserializerResult:"+deSerializerResult.toString());
    }
}
