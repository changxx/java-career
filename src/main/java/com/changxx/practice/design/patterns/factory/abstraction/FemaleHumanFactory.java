package com.changxx.practice.design.patterns.factory.abstraction;

public class FemaleHumanFactory extends AbstractHumanFactory {

    // 创建一个女性白种人
    public Human createWhiteHuman() {
        return super.createHuman(HumanEnum.WhiteFemaleHuman);
    }

    // 创建一个女性黄种人
    public Human createYellowHuman() {
        return super.createHuman(HumanEnum.YelloFemaleHuman);
    }
}
