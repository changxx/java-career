package com.changxx.practice.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author changxx on 15-7-21.
 */
public class ServerSocketChannelTest {

    /**
     * Java NIO中的 ServerSocketChannel 是一个可以监听新进来的TCP连接的通道, 就像标准IO中的ServerSocket一样
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8050);
        serverSocketChannel.socket().bind(address);

        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();

            if (socketChannel != null) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(48);

                int read = socketChannel.read(byteBuffer);

                while (read != -1) {
                    System.out.println("read " + read + " byte");

                    byteBuffer.flip();

                    while (byteBuffer.hasRemaining()) {
                        System.out.print((char) byteBuffer.get());
                    }
                    System.out.println();

                    byteBuffer.clear();

                    read = socketChannel.read(byteBuffer);
                }
            }
        }
    }
}
