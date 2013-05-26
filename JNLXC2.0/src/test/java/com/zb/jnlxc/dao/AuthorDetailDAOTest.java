package com.zb.jnlxc.dao;

import com.google.gson.Gson;
import com.zb.jnlxc.model.Admin;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12-12-31
 * Time: 下午8:42
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
@TransactionConfiguration(defaultRollback=true)
public class AuthorDetailDAOTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Resource
    private AuthorDetailDAO authorDetailDAO;
    @Resource
    AdminDAO adminDAO;
    @Test
    public void testGetDefaultAuthor() throws Exception {
    }

    @Test
    public void testGetAuthorDetailsByAuthor() throws Exception {

    }

    @Test
    public void testGetViewTablesByAdmin() throws Exception {
        Admin user = adminDAO.getUserByAccount("zqh");
        List l =authorDetailDAO.getViewTablesByAdmin(user);
        logger.info(new Gson().toJson(l));
    }

}
