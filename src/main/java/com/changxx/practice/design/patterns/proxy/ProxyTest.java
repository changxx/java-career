package com.changxx.practice.design.patterns.proxy;

import java.lang.reflect.Proxy;

/**
 * @author changxiangxiang
 * @date 2014年8月6日 上午11:09:10
 * @description 动态代理
 * @since sprint2
 */
public class ProxyTest {

    public static void main(String[] args) {

        Calculator calculator = new CalculatorImpl();

        LogHandler logHandler = new LogHandler(calculator);

        Calculator proxy = (Calculator) Proxy.newProxyInstance(calculator.getClass().getClassLoader(), calculator.getClass().getInterfaces(), logHandler);

        int num = proxy.add(1, 2);

        System.out.println(num);

    }
}
