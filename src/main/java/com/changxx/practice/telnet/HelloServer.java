package com.changxx.practice.telnet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * HelloServer
 *
 * @author changxiangxiang
 * @date 2017/7/25
 */
public class HelloServer {

    public static final int SERVER_PORT = 9527;
    private ServerSocket serverSocket = null;
    private ExecutorService executorService = null;
    private final int POOL_SIZE = 2;

    public HelloServer() throws Exception {
        int cpuCount = Runtime.getRuntime().availableProcessors();
        executorService = Executors.newFixedThreadPool(cpuCount * POOL_SIZE);
        serverSocket = new ServerSocket(SERVER_PORT);
        System.out.println("服务器已启动,监听中...");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                executorService.execute(new HelloResponser(socket));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //
    class HelloResponser implements Runnable {
        private Socket socket = null;

        public HelloResponser(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                //
                String clientIp = socket.getInetAddress().getHostAddress();
                System.out.println("开始处理来在 " + clientIp + ":" + socket.getPort() + " 的请求");
                InputStream socketInStream = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(socketInStream, "UTF-8"));

                //
                OutputStream socketOutStream = socket.getOutputStream();
                String clientRequestString = null;
                while ((clientRequestString = br.readLine()) != null) {
                    System.out.println("接收到客户端 " + clientIp + " 的信息:" + clientRequestString);
                    String serverReturn = null;
                    if (clientRequestString.equals("sb")) {
                        serverReturn = "你也是SB.\r\n";
                        System.out.println("发送给客户端" + clientIp + "应答信息:" + serverReturn);
                        socketOutStream.write(serverReturn.getBytes("UTF-8"));
                        System.out.println("结束来自 " + clientIp + ":" + socket.getPort() + " 的请求");
                        break;
                    } else {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
                        serverReturn = df.format(new Date()) + "\r\n";
                        System.out.println("发送给客户端 " + clientIp + "应答信息:" + serverReturn);
                        socketOutStream.write((serverReturn+"\r\ndubbo>").getBytes("UTF-8"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    //
    public static void main(String[] args) throws Exception {
        new HelloServer();
    }

}
