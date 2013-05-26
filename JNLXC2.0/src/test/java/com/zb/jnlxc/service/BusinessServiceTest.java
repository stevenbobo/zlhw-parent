package com.zb.jnlxc.service;

import com.zb.jnlxc.model.OrderForm;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: Steven.Zheng
 * Date: 13-3-3
 * Time: 下午1:02
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class BusinessServiceTest extends AbstractJUnit4SpringContextTests {
    @Resource
    BusinessService businessService;
    @Test
    public void testGetLastOrderForm() throws Exception {
        OrderForm orderForm = businessService.getLastOrderForm(20040,1);
        logger.info(orderForm);
        Assert.assertNotNull(orderForm);
    }
}
