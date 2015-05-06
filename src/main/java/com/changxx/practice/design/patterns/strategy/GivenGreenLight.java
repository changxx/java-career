package com.changxx.practice.design.patterns.strategy;

/**
 * 求吴国太开个绿灯
 * @author changxx
 */
public class GivenGreenLight implements IStrategy {

    public void operate() {
        System.out.println("求吴国太开个绿灯,放行！");
    }

}
