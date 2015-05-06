package com.changxx.practice.design.patterns.strategy;

/**
 * 找乔国老帮忙，使孙权不能杀刘备
 * @author changxx
 */
public class BackDoor implements IStrategy {

    public void operate() {
        System.out.println("找乔国老帮忙，让吴国太给孙权施加压力");
    }

}
