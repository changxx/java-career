package com.changxx.practice.rpc.socket.demo1;

import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.net.Socket;

/**
 * @author changxx on 15-8-7.
 */
public class ClientTest {

    public static void main(String args[]) throws Exception {
        //为了简单起见，所有的异常都直接往外抛
        String host = "127.0.0.1";  //要连接的服务端IP地址
        int port = 8080;   //要连接的服务端对应的监听端口
        //与服务端建立连接
        Socket client = new Socket(host, port);
        //建立连接后就可以往服务端写数据了
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
        objectOutputStream.writeObject(new Domain(1, "changxx"));

        objectOutputStream.flush();

        //写完以后进行读操作
        Reader reader = new InputStreamReader(client.getInputStream());
        char chars[] = new char[64];
        int len;
        StringBuffer sb = new StringBuffer();
        while ((len = reader.read(chars)) != -1) {
            sb.append(new String(chars, 0, len));
        }
        System.out.println("from server: " + sb);
        reader.close();
        objectOutputStream.close();
        client.close();
    }

}
