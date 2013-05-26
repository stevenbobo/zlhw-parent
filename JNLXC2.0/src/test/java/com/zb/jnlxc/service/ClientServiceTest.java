package com.zb.jnlxc.service;

import com.zb.jnlxc.dao.AdminDAO;
import com.zb.jnlxc.model.Client;
import com.zb.util.StringUtils;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Steven.Zheng
 * Date: 13-3-3
 * Time: 上午10:08
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
@TransactionConfiguration(defaultRollback=false)
public class ClientServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Resource
    ClientService clientService;
    @Resource
    AdminDAO adminDAO;
//    @Test
//    public void testEditClient() throws Exception {
//        List<Client> l = clientService.findAll();
//        int a=0;
//        for(Client c:l){
//            logger.info(""+a++);
//            clientService.updateForOld(c);
//        }
//    }
//@Test
    public void testAddClient() throws FileNotFoundException {
        // TODO Auto-generated method stub
        String filePath = "d://客户明细.xls";
        InputStream fs = null;
        Workbook workBook = null;

        try {
            // 加载excel文件
            fs = new FileInputStream(filePath);
            // 得到 workbook
            workBook = Workbook.getWorkbook(fs);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 取得sheet，如果你的workbook里有多个sheet 可以利用 wb.getSheets()方法来得到所有的。
        // getSheets() 方法返回 Sheet[] 数组 然后利用数组来操作。就是多次循环的事。
        Sheet sheet = workBook.getSheet(0);//这里只取得第一个sheet的值，默认从0开始
        System.out.println(sheet.getColumns());//查看sheet的列
        System.out.println(sheet.getRows());//查看sheet的行
        Cell cell = null;//就是单个单元格
        // 开始循环，取得 cell 里的内容，这里都是按String来取的 为了省事，具体你自己可以按实际类型来取。或者都按
        // String来取。然后根据你需要强制转换一下。
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < sheet.getRows(); i++) {
                String clientName = sheet.getCell(0, i).getContents();
                String agentId = sheet.getCell(1, i).getContents();
                if(StringUtils.isNotBlank(clientName)&&
                        StringUtils.isNotBlank(agentId)){
                    try {

                        Integer agentDbId = Integer.parseInt(agentId.trim());
                        Client client = new Client();
                        client.setAgent(adminDAO.loadById(agentDbId));
                        client.setName(clientName);
                        clientService.addClient(client);
                    }catch (Exception e){
                        e.printStackTrace();
                        continue;
                    }
                }
            }
        workBook.close();//记得关闭
    }
    @Test
    public void testFormate(){
        String clientCode = String.format("%1$06d", 555555);
        System.out.println(clientCode);
    }
}
