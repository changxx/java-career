package com.changxx.practice.design.patterns.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author changxx on 15-11-5.
 */
public class CGLibProxyTest {

    public static void main(String[] args) {
        Calculator calculator = CGLibProxy.getInstance().getProxy(CalculatorImpl.class);
        int result = calculator.add(1, 1);
        System.out.println(result);
    }

}

class CGLibProxy implements MethodInterceptor {

    private static CGLibProxy cgLibProxy = new CGLibProxy();

    public static CGLibProxy getInstance() {
        return cgLibProxy;
    }

    public <T> T getProxy(Class<T> cls) {
        return (T) Enhancer.create(cls, this);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("invoke before");
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("invoke after");
        return result;
    }

}