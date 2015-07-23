package com.changxx.practice.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author changxx on 15-7-21.
 */
public class FileChannelTest {

    public static void main(String[] args) throws Exception {
        RandomAccessFile file = new RandomAccessFile("/temp.txt", "rw");

        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(48);

        int readByte = channel.read(buffer);
        while (readByte != -1) {

            System.out.println("read " + readByte + " byte");

            buffer.flip();

            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }
            System.out.println();

            buffer.clear();

            readByte = channel.read(buffer);
        }

        file.close();
    }

}
