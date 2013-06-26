package com.zb.jnlxc.service;

import junit.framework.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: Steven.Zheng
 * Date: 13-3-8
 * Time: 下午8:40
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")

public class AdminServiceTest  extends AbstractJUnit4SpringContextTests {
    @Resource
    AdminService adminService;
    @Test
    public void testHavePower() throws Exception {
        Assert.assertTrue(adminService.havePower(adminService.getById(1),"订单浏览"));
    }


}
