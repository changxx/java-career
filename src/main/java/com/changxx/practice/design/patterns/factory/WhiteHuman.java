package com.changxx.practice.design.patterns.factory;

public class WhiteHuman implements Human {

    public void laugh() {
        System.out.println("白色人类会大笑，侵略的笑声");
    }

    public void cry() {
        System.out.println("白色人类会哭");
    }

    public void talk() {
        System.out.println("白色人类会说话，一般都是但是单字节！");
    }

}
