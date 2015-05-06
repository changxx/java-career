package com.changxx.practice.design.patterns.factory.abstraction;

public class MaleHumanFactory extends AbstractHumanFactory {

    // 创建一个男性白种人
    public Human createWhiteHuman() {
        return super.createHuman(HumanEnum.WhiteMaleHuman);
    }

    // 创建一个男性黄种人
    public Human createYellowHuman() {
        return super.createHuman(HumanEnum.YelloMaleHuman);
    }
}
