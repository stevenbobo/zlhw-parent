package com.zb.jnlxc.service;

import com.zb.jnlxc.model.Scheme;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 13-1-31
 * Time: 下午11:51
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
@TransactionConfiguration(defaultRollback=false)
public class SchemeServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    Logger logger = LoggerFactory.getLogger(SchemeServiceTest.class);
    @Resource
    SchemeService schemeService;
    @Test
    public void testGetAllProductRecordDetail() throws Exception {
        List l =schemeService.findHql("select t.dbId as aa from Scheme t where t.dbId=?",1);
        logger.info(l.size()+"");
    }

    @Test
    public void testChangeOldMap() throws Exception {
        File file = new File("E:\\newMap");
        File[] files = file.listFiles();
        int a=0;
        for(File f :files){
            logger.info("i = {}",a++);
            schemeService.changeOldMap(f);
        }
    }
    @Test
    public void updateCode(){
        List<Scheme> l =schemeService.findAll();
        int a=0;
        for(Scheme s:l){
            logger.info(""+a++);
            schemeService.update(s);
        }
    }
}
