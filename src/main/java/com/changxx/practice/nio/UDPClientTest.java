package com.changxx.practice.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @author changxx on 15-7-23.
 */
public class UDPClientTest {

    /**
     * Java NIO中的DatagramChannel是一个能收发UDP包的通道。因为UDP是无连接的网络协议，所以不能像其它通道那样读取和写入。它发送和接收的是数据包
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        DatagramChannel channel = DatagramChannel.open();

        String newData = "New String to write to file..." + System.currentTimeMillis();

        ByteBuffer buf = ByteBuffer.allocate(48);

        buf.clear();

        buf.put(newData.getBytes());

        buf.flip();
        int bytesSent = channel.send(buf, new InetSocketAddress("127.0.0.1", 8050));
    }

}
