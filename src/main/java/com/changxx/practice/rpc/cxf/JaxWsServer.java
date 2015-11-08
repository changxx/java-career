package com.changxx.practice.rpc.cxf;

import org.apache.cxf.frontend.ServerFactoryBean;

/**
 * @author changxx on 15-11-8.
 */
public class JaxWsServer {

    public static void main(String[] args) {
        ServerFactoryBean factory = new ServerFactoryBean();
        factory.setAddress("http://localhost:8080/ws/soap/hello");
        factory.setServiceClass(HelloService.class);
        factory.setServiceBean(new HelloServiceImpl());
        factory.create();
    }

}
