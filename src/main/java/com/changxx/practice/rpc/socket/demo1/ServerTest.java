package com.changxx.practice.rpc.socket.demo1;

import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author changxx on 15-8-7.
 */
public class ServerTest {

    private static String json = "[";

    static {
        for (int i = 0; i < 5; i++) {
            json += "{\"age\": 13,\n" +
                    "  \"id\": \"motorola-defy-with-motoblur\",\n" +
                    "  \"name\": \"Motorola DEFY\\u2122 with MOTOBLUR\\u2122\",\n" +
                    "  \"snippet\": \"Are you ready for everything life throws your way?\"},";
        }

        json += "{\"age\": 13,\n" +
                "  \"id\": \"motorola-defy-with-motoblur\",\n" +
                "  \"name\": \"Motorola DEFY\\u2122 with MOTOBLUR\\u2122\",\n" +
                "  \"snippet\": \"Are you ready for everything life throws your way?\"}";

        json += "]";
    }

    public static final int PORT = 8080;//监听的端口号


    public static void main(String args[]) throws Exception {
        //为了简单起见，所有的异常信息都往外抛
        //定义一个ServerSocket监听在端口8899上
        ServerSocket server = new ServerSocket(PORT);
        //server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的
        while (true) {
            Socket socket = server.accept();
            //跟客户端建立好连接之后，我们就可以获取socket的InputStream，并从中读取客户端发过来的信息了。

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Domain domain = (Domain) in.readObject();

            System.out.println("from client: " + domain.getName());

            //读完后写一句
            Writer writer = new OutputStreamWriter(socket.getOutputStream());
            writer.write(json);
            writer.flush();
            writer.close();
            in.close();
            socket.close();
        }

    }

}
