package com.zb.jnlxc.dao;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Steven.Zheng
 * Date: 13-3-10
 * Time: 下午6:13
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
@TransactionConfiguration(defaultRollback=false)
public class ProductRecordDetailDAOTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Resource
    ProductRecordDetailDAO productRecordDetailDAO;
    @Resource
    OrderDetailDAO orderDetailDAO;
    @Test
    public void testGetCompDetail() throws Exception {
        Map<String,Long> map =productRecordDetailDAO.getCompDetail(orderDetailDAO.getById(42));
        logger.info(map.get("compWeight")+"  "+map.get("compQuantity"));

    }
}
