package com.zb.jnlxc.service;

import com.zb.jnlxc.model.City;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 13-1-29
 * Time: 上午12:26
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class CityServiceTest extends AbstractJUnit4SpringContextTests {
    @Resource
    CityService cityService;
    @Test
    public void testGetCode() throws Exception {
        City ruian = cityService.getById(10);
        String code = cityService.getCode(ruian);
        Assert.assertTrue(code.startsWith("1"));
        System.out.println(code);
    }
}
