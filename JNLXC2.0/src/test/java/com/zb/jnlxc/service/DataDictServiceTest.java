package com.zb.jnlxc.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import com.zb.jnlxc.model.DataDict;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 13-3-1
 * Time: 上午12:18
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class DataDictServiceTest extends AbstractJUnit4SpringContextTests {
    Logger logger = LoggerFactory.getLogger(DataDictServiceTest.class);
    @Resource
    DataDictService dataDictService;
    @Test
    public void testFindLastLvDing() throws Exception {
        DataDict dataDict = dataDictService.findLastLvDing();
        logger.info(dataDict.toString());

    }
}
