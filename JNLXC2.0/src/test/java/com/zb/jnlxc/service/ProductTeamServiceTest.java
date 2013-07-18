package com.zb.jnlxc.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 我是头熊没钱
 * Date: 13-7-10
 * Time: 下午11:15
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
@TransactionConfiguration(defaultRollback=false)
public class ProductTeamServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    Logger logger = LoggerFactory.getLogger(PaiDanFlowServiceTest.class);
    @Resource
    private ProductTeamService productTeamService;
    @Test
    public void testFindAllTeamDetail() throws Exception {
        List l = productTeamService.findAllTeamDetail();
        logger.info(l.toString());
    }
}
