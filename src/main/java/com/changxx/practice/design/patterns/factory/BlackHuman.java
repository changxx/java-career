package com.changxx.practice.design.patterns.factory;

public class BlackHuman implements Human {

    public void laugh() {
        System.out.println("黑人会笑");
    }

    public void cry() {
        System.out.println("黑人会哭");
    }

    public void talk() {
        System.out.println("黑人可以说话，一般人听不懂");
    }

}
