package com.changxx.practice.serializable.msgpack;

import java.io.IOException;

import org.msgpack.MessagePack;

import com.changxx.practice.serializable.Domain;

/**
 * @author changxx on 15-12-14.
 */
public class MessagePackTest {

    public static void main(String[] args) throws IOException {
        Domain domain = new Domain("xxx");
        MessagePack msgpack = new MessagePack();

        byte[] bytes = msgpack.write(domain);

        System.out.println(msgpack.read(bytes));
    }

}
