package com.changxx.practice.design.patterns.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author changxiangxiang
 * @date 2014年8月6日 上午11:06:32
 * @description
 * @since sprint2
 */
public class LogHandler implements InvocationHandler {

    Object obj;

    public LogHandler(Object obj) {
        super();
        this.obj = obj;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        doBefore();

        Object ob = method.invoke(obj, args);

        doAfter();

        return ob;
    }

    public void doBefore() {
        System.out.println("method start");
    }

    public void doAfter() {
        System.out.println("method end");
    }
}
