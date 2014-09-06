package com.zb.jnlxc.model;

import junit.framework.TestCase;

public class LiaoKuangTest extends TestCase {

    public void testGetNextStep() throws Exception {
        String step = "时效氧化打包";
        String currentStep = "打包";
        LiaoKuang liaoKuang = new LiaoKuang();
        liaoKuang.setSteps(step);
        liaoKuang.setCurrentStep(currentStep);
        String result = liaoKuang.getNextStep();
        System.out.println(result);
    }
}