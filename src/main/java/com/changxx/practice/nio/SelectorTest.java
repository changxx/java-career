package com.changxx.practice.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author changxx on 15-7-21.
 *         <p/>
 *         Selector（选择器）是Java NIO中能够检测一到多个NIO通道，并能够知晓通道是否为诸如读写事件做好准备的组件。
 *         这样，一个单独的线程可以管理多个channel，从而管理多个网络连接。
 */
public class SelectorTest {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();

        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);

        channel.register(selector, SelectionKey.OP_READ);

        while (true) {
            int readyChannels = selector.select();
            if (readyChannels == 0) continue;
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                if (key.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.
                } else if (key.isConnectable()) {
                    // a connection was established with a remote server.
                } else if (key.isReadable()) {
                    // a channel is ready for reading
                } else if (key.isWritable()) {
                    // a channel is ready for writing
                }
                keyIterator.remove();
            }
        }

    }

}
