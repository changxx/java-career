package com.changxx.practice.serializable.jdk;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.changxx.practice.serializable.Domain;

/**
 * @author changxx on 15-11-25.
 */
public class SerializableTest {

    /**
     * 同一个java对象只要序列化ID相同，属性相同，那么反序列化会正常的执行
     * 同一个java对象，如果序列化ID不同，属性相同，反序列化都不会成功
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("/home/changxx/1.txt"));
        os.writeObject(new Domain("xxxxx"));

        os.flush();
        os.close();

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("/home/changxx/1.txt"));
        Domain domain = (Domain) in.readObject();
        System.out.println(domain.getName());
    }

}
