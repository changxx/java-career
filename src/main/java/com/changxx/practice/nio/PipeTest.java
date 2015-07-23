package com.changxx.practice.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @author changxx on 15-7-23.
 */
public class PipeTest {

    /**
     * Java NIO 管道是2个线程之间的单向数据连接。Pipe有一个source通道和一个sink通道。数据会被写到sink通道，从source通道读取。
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Pipe pipe = Pipe.open();

        Pipe.SinkChannel sinkChannel = pipe.sink();

        String newData = "New String to write to file..." + System.currentTimeMillis();
        ByteBuffer buf = ByteBuffer.allocate(48);
        buf.clear();
        buf.put(newData.getBytes());
        buf.flip();

        int writeByte = 0;
        while (buf.hasRemaining()) {
            writeByte += sinkChannel.write(buf);
        }

        System.out.println("write " + writeByte + " byte");

        buf.clear();

        ByteBuffer buf2 = ByteBuffer.allocate(48);
        Pipe.SourceChannel sourceChannel = pipe.source();
        int readByte = sourceChannel.read(buf2);

        System.out.println("read " + readByte + " byte");

        buf2.flip();
        while (buf2.hasRemaining()) {
            System.out.print((char) buf2.get());
        }
    }

}
