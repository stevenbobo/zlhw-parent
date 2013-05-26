package com.zb.jnlxc.dao;

import com.zb.jnlxc.model.Admin;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven.Zheng
 * Date: 13-3-8
 * Time: 下午6:45
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
@TransactionConfiguration(defaultRollback=true)
public class AdminDAOTest  extends AbstractTransactionalJUnit4SpringContextTests {
    @Resource
    AdminDAO adminDAO;
    @Test
    public void testFindAdminsByAuthor() throws Exception {
        List l =adminDAO.findAdminsByAuthorName("用户添加");
        logger.info(l.size()+"");
    }
    @Test
    public void testfindAdminsByGroupName(){
        List<Admin> l =adminDAO.findAdminsByGroupName("业务员");
        for (Admin a:l)
            logger.info(a.getName());
    }
}
