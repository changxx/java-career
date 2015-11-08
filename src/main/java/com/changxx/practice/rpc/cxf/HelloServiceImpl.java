package com.changxx.practice.rpc.cxf;

/**
 * @author changxx on 15-11-8.
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String say(String name) {
        return "hello " + name;
    }

}
