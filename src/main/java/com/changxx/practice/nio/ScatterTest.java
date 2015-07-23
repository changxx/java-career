package com.changxx.practice.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author changxx on 15-7-21.
 */
public class ScatterTest {

    public static void main(String[] args) throws Exception {
        RandomAccessFile file = new RandomAccessFile("/temp.txt", "rw");

        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(48);
        ByteBuffer buffer2 = ByteBuffer.allocate(48);

        ByteBuffer[] byteBuffers = {buffer, buffer2};

        long readByte = channel.read(byteBuffers);
        while (readByte != -1) {

            System.out.println("read " + readByte + " byte");

            buffer.flip();
            buffer2.flip();

            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }
            while (buffer2.hasRemaining()) {
                System.out.print((char) buffer2.get());
            }
            System.out.println();

            buffer.clear();
            buffer2.clear();

            readByte = channel.read(byteBuffers);
        }

        file.close();
    }
}
