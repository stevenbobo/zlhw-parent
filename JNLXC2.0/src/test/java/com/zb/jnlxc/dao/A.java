package com.zb.jnlxc.dao;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: zhengbo.zb
 * Date: 13-12-13
 * Time: 上午11:23
 * To change this template use File | Settings | File Templates.
 */
public class A {
    @Test
    public void test1(){
        System.out.print(4.8f / 2.4f);
        Assert.assertTrue(4.8f % 2.4f == 0);

        Random random = new Random();//指定种子数100
        for (int a=0;a<100;a++){
        int seed = random.nextInt(4);
        System.out.println(seed);
        }
    }
}
