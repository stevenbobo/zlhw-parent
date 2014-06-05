package com.zb.jnlxc.action;

import com.zb.jnlxc.model.PaiChanRecord;
import com.zb.jnlxc.service.PaichanRecordService;
import com.zb.jnlxc.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12-12-29
 * Time: 下午3:33
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/ReportAction")
@SessionAttributes(value = {"user"})
public class ReportAction  {
    @Resource
    PaichanRecordService paichanRecordService;
    @Resource
    ReportService reportService;

    @ResponseBody
    @RequestMapping("/exportProductRecord")
    public void exportProductRecord(Integer paichanRecordId, HttpServletResponse response) throws JRException, DocumentException, IOException {
        response.setContentType("application/x-download");
        response.setHeader("Content-disposition", "attachment;filename="
                + "a.pdf");
        OutputStream out = response.getOutputStream();
        try {
            PaiChanRecord paiChanRecord = paichanRecordService.getById(paichanRecordId);
            reportService.exportProductRecord(paiChanRecord, out);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();

    }
}
