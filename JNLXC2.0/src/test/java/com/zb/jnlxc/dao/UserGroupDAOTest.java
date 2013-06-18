package com.zb.jnlxc.dao;

import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.UserGroup;
import junit.framework.Assert;
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
 * Date: 13-6-19
 * Time: 上午12:30
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class UserGroupDAOTest extends AbstractTransactionalJUnit4SpringContextTests {
    Logger logger = LoggerFactory.getLogger(UserGroupDAOTest.class);
    @Resource
    UserGroupDAO userGroupDAO;
    @Resource
    AdminDAO adminDAO;
    @Test
    public void testFindAllUserGroup() throws Exception {
        Admin hr = adminDAO.getById(223);
        int hrsize = userGroupDAO.findAllUserGroup(hr).size();
        logger.info("hr可操作的组有{}个",hrsize);
        Admin zqh = adminDAO.getById(1);
        int zqhsize = userGroupDAO.findAllUserGroup(zqh).size();
        logger.info("zqh可操作的组有{}个",zqhsize);
        Assert.assertTrue(zqhsize>hrsize);
    }
}
