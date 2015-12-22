package com.changxx.practice.rpc.socket.demo2;

import com.changxx.practice.rpc.HelloService;

/**
 * @author changxx on 15-12-22.
 */
public class Client {

    public static void main(String[] args) throws Exception {
        HelloService helloService = Rpc.refer(HelloService.class, "127.0.0.1", 8080);
        String result = helloService.say("changxx");
        System.out.println("client receive : " + result);
    }

}
