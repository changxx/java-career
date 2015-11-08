package com.changxx.practice.io.socket.pool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * bio，一个客户端一个链接，会造成线程资源耗尽
 * @author changxx on 15-11-8.
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8080;
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("socket is start in port: " + port);
            Socket socket = null;
            // 超过能处理数目 java.util.concurrent.RejectedExecutionException
            TimeServerHandlerExecutePool pool = new TimeServerHandlerExecutePool(5, 10);
            while (true) {
                socket = server.accept();
                pool.execute(new TimeServerHandler(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
