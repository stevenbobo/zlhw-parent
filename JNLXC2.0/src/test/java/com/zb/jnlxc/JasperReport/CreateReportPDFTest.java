package com.zb.jnlxc.JasperReport;

import com.zb.jnlxc.model.Admin;
import net.sf.jasperreports.engine.JRException;
import org.junit.Test;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12-12-30
 * Time: 上午12:48
 * To change this template use File | Settings | File Templates.
 */
public class CreateReportPDFTest {
    /**
     * 测试用
     */
    public static Object[][] data =
            {
                    {"郑波", new Integer(22), "郑波Bill Ott", "250 - 20th Ave."},
                    {"Berne", new Integer(9), "James Schneider", "277 Seventh Av."},
                    {"Boston", new Integer(32), "Michael Ott", "339 College Av."},
                    {"Boston", new Integer(23), "Julia Heiniger", "358 College Av."},
                    {"Chicago", new Integer(39), "Mary Karsen", "202 College Av."},
                    {"Chicago", new Integer(35), "George Karsen", "412 College Av."},
                    {"Chicago", new Integer(11), "Julia White", "412 Upland Pl."},
                    {"Dallas", new Integer(47), "Janet Fuller", "445 Upland Pl."},
                    {"Dallas", new Integer(43), "Susanne Smith", "2 Upland Pl."},
                    {"Dallas", new Integer(40), "Susanne Miller", "440 - 20th Ave."},
                    {"Dallas", new Integer(36), "John Steel", "276 Upland Pl."},
                    {"Dallas", new Integer(37), "Michael Clancy", "19 Seventh Av."},
                    {"Dallas", new Integer(19), "Susanne Heiniger", "86 - 20th Ave."},
                    {"Dallas", new Integer(10), "Anne Fuller", "135 Upland Pl."},
                    {"Dallas", new Integer(4), "Sylvia Ringer", "365 College Av."},
                    {"Dallas", new Integer(0), "Laura Steel", "429 Seventh Av."},
                    {"Lyon", new Integer(38), "Andrew Heiniger", "347 College Av."},
                    {"Lyon", new Integer(28), "Susanne White", "74 - 20th Ave."},
                    {"Lyon", new Integer(17), "Laura Ott", "443 Seventh Av."},
                    {"Lyon", new Integer(2), "Anne Miller", "20 Upland Pl."},
                    {"New York", new Integer(46), "Andrew May", "172 Seventh Av."},
                    {"New York", new Integer(44), "Sylvia Ott", "361 College Av."},
                    {"New York", new Integer(41), "Bill King", "546 College Av."},
                    {"Oslo", new Integer(45), "Janet May", "396 Seventh Av."},
                    {"Oslo", new Integer(42), "Robert Ott", "503 Seventh Av."},
                    {"Paris", new Integer(25), "Sylvia Steel", "269 College Av."},
                    {"Paris", new Integer(18), "Sylvia Fuller", "158 - 20th Ave."},
                    {"Paris", new Integer(5), "Laura Miller", "294 Seventh Av."},
                    {"San Francisco", new Integer(48), "Robert White", "549 Seventh Av."},
                    {"San Francisco", new Integer(7), "James Peterson", "231 Upland Pl."}
            };
    /**
     * 测试用的主函数
     * @throws net.sf.jasperreports.engine.JRException
     */
    @Test
    public void testReport() throws JRException {
        List<Admin> l=new ArrayList();
        for(Object[] os:data){
//            l.add(new Admin((String)os[0],(Integer)os[1],(String)os[2],(String)os[3]));
        }

        Map parameters = new HashMap();
        parameters.put("ReportTitle", "生产跟踪单");
        parameters.put("time", new Date());
        parameters.put("Charger", "王培");
        parameters.put("recordid", "12302382");
        parameters.put("BaseDir", "c:/20031127_5.bmp");

        new CreateReportPDF("WebappReport", "c:/a.pdf", parameters, l).createPdf();
    }
}
