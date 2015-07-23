package com.changxx.practice.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @author changxx on 15-7-23.
 */
public class DatagramChannelTest {

    /**
     * Java NIO中的DatagramChannel是一个能收发UDP包的通道。因为UDP是无连接的网络协议，所以不能像其它通道那样读取和写入。它发送和接收的是数据包
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.bind(new InetSocketAddress("127.0.0.1", 8050));

        while (true) {

            ByteBuffer buf = ByteBuffer.allocate(48);
            buf.clear();

            datagramChannel.receive(buf);

            if (buf.position() > 0) {
                buf.flip();

                while (buf.hasRemaining()) {
                    System.out.print((char) buf.get());
                }

                buf.clear();

                System.out.println();
            }

        }

    }

}
