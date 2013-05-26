package com.zb.jnlxc.JasperReport;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;


public class CreateReportPDF {


	//这两个路径需要放到xml配置文件中
	private String source=null;//数据源
	private JasperReport jasperReport=null;//编译后内容
	private JasperDesign jasperDesign=null;//用于设计
	private String destination=null;//目标地址
	private List fieldContent=null;//列表内容
	private Map parameters=null;//参数变量

    private String getdefaultJasperPath(){
        String resourcePath = CreateReportPDF.class.getClassLoader().getResource("./JasperReport/WebappReport.jasper").getPath();
        return resourcePath;
    }

    private String getdefaultJrxmlPath(){
        String resourcePath = CreateReportPDF.class.getClassLoader().getResource("./JasperReport/WebappReport.jrxml").getPath();
        return resourcePath;
    }

	/**
	 * 构造函数
	 * @param source 默认在 com/zb/jnlxc/JasperReport下
	 * @param des 目标地址
	 */
	public CreateReportPDF(String source,String des,Map parameters,List fieldContent){
		this.source=source;
		this.destination=des;
		this.parameters=parameters;
		this.fieldContent=fieldContent;
        try {
			jasperDesign = JRXmlLoader.load(getdefaultJrxmlPath());
            if(CreateReportPDF.class.getClassLoader().getResource("./JasperReport/WebappReport.jasper")==null){
                jasperReport=JasperCompileManager.compileReport(jasperDesign);
//                File jasperFile = new File("./JasperReport/WebappReport.jasper");
//                jasperFile.createNewFile();
//                JRSaver.saveObject(jasperReport, "./JasperReport/WebappReport.jasper");
            }else {
                jasperReport = (JasperReport) JRLoader.loadObject(getdefaultJasperPath());
            }

		} catch (JRException e) {
			e.printStackTrace();
		}
    }
	/**
	 * 生成pdf
	 * @throws JRException
	 */
	public void createPdf() throws JRException{
		ListDataSource ld=new ListDataSource(fieldContent);
		JasperPrint jasperPrint =JasperFillManager.fillReport(jasperReport,parameters,ld);
		JasperExportManager.exportReportToPdfFile(jasperPrint, destination);
	}
	
	/**
	 * 输出pdf文件流
	 * @throws JRException
	 */
	public void exportReportToPdfStream(OutputStream outputStream) throws JRException{
		ListDataSource ld=new ListDataSource(fieldContent);
		JasperPrint jasperPrint =JasperFillManager.fillReport(jasperReport,parameters,ld);
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	}
	

	}
