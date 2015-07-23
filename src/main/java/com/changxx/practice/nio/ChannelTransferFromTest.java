package com.changxx.practice.nio;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @author changxx on 15-7-21.
 */
public class ChannelTransferFromTest {

    public static void main(String[] args) throws Exception {
        RandomAccessFile fFile = new RandomAccessFile("/temp.txt", "rw");
        FileChannel fromChannel = fFile.getChannel();

        RandomAccessFile tFile = new RandomAccessFile("/temp1.txt", "rw");
        FileChannel toChannel = tFile.getChannel();

        long count = fromChannel.size();
        fromChannel.transferTo(0, count, toChannel);
    }

}
