package com.changxx.practice.design.patterns.factory.abstraction;

public interface HumanFactory {

    // 制造黄色人类
    public Human createYellowHuman();

    // 制造一个白色人类
    public Human createWhiteHuman();
}
