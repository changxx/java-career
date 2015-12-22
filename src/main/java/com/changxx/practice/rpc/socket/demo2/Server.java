package com.changxx.practice.rpc.socket.demo2;

import com.changxx.practice.rpc.HelloService;
import com.changxx.practice.rpc.HelloServiceImpl;

/**
 * @author changxx on 15-12-22.
 */
public class Server {

    public static void main(String[] args) throws Exception {
        HelloService helloService = new HelloServiceImpl();
        Rpc.export(helloService, 8080);
    }

}
