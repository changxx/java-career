package com.changxx.practice.io.socket.pool;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author changxx on 15-11-8.
 */
public class TimeClient {

    public static void main(String[] args) {
        // 超过服务器能处理数目java.net.ConnectException: Connection refused
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new PutServer());
            thread.start();
        }
    }

}

class PutServer implements Runnable {
    @Override
    public void run() {
        int port = 8080;
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket("127.0.0.1", port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("client send request");
            System.out.println("client send succeed");
            String resp = in.readLine();
            System.out.println(Thread.currentThread().getName() + " client get respose : " + resp);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                socket = null;
            }
        }
    }
}
