package com.zb.jnlxc.service;

import com.ZLHW.base.factory.BeanFactory;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.OrderForm;
import com.zb.jnlxc.model.ProductRecord;
import com.zb.jnlxc.model.ProductTeam;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Steven.Zheng
 * Date: 13-3-6
 * Time: 下午3:22
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")

public class ReportServiceTest extends AbstractJUnit4SpringContextTests {
    @Resource
    OrderFormService orderFormService;
    @Resource
    ReportService reportService;
    @Test
    public void testExportProductRecord() throws Exception {
    }

    @Test
    public void testPrint() throws Exception {
        File file = new File("d:/test.pdf");
        if(!file.exists())
            file.createNewFile();
        OutputStream os = new FileOutputStream(file);
        OrderForm orderForm=orderFormService.getById(26);
        Integer num=1;
        ProductTeam productTeam=new ProductTeam();
        Admin charge=new Admin();
        charge.setAccount("郑波");
        charge.setName("郑波");
        productTeam.setCharge_dbId(charge.getDbId());
        ProductRecord productRecord = new ProductRecord();
        productRecord.setRecordNum(num);
        productRecord.setCode(orderForm.getCode()+num);//设置跟踪单编码
        productRecord.setCharger(charge);//设置机台负责人
        productRecord.setOrderForm(orderForm);//对应的订单
        orderForm.setMcomment("以前都可以访问的，今天发现突然不能下载jar包了，太蛋疼了啊。\n" +
                "\n" +
                "百度搜索了下貌似联通的哥们遇到这个问题比较多啊。ping返回正确的ip地址，看来有可能是联通内部的原因。但是search.maven.org这个域名还能访问，不知道联通在唱啥戏呢？\n" +
                "\n" +
                "google又search了半天未果，还是老老实实的找个代理来用吧。\n" +
                "\n" +
                "不知道还有什么更好的方便。谢谢！");
        productRecord.setNitrideNum(0);//已完成数量为0
        productRecord.setCreateDate(new Date(System.currentTimeMillis()));//设置开跟踪单时间
        reportService.exportProductRecord(productRecord,os);
    }
}
