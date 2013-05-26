/**
 *  ClassName: ExprotDDLScript.java
 *  created on 4:49:18 PM
 *  Copyrights 2008 qjyong All rights reserved.
 *  EMail: qjyong@gmail.com
 */
package com.zb.util;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import com.ZLHW.base.table.BaseTable;


/**
 * 根据hbm文件生成数据库DDL的工具类
 * 
 * @author qiujy
 */
public class HibernateAutoCreate {

	// 默认的hibernate配置地址
	public static String HIBERNATE_CONFIG = "WebRoot/WEB-INF/hibernate.cfg.xml";
	public static Document document = DocumentHelper.createDocument();
	static {
		document.addDocType("hibernate-mapping ",
				"-//Hibernate/Hibernate Mapping DTD 3.0//EN",
				"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd");
	}

	/**
	 * 根据指定的hibernate配置文件来创建数据库
	 */
	public static void createTable(String cfg, List<? extends BaseTable> clazzs) {
		Configuration configuration = new Configuration().configure(new File(
				cfg));
		configuration.addXML(wrapXML(clazzs));
		SchemaExport export = new SchemaExport(configuration);
		export.create(true, true);
	}

	/**
	 * 生成单张的表
	 * 
	 * @param tb
	 */
	public static void createTable(BaseTable tb) {
		Configuration configuration = new Configuration().configure(new File(
				HIBERNATE_CONFIG));
		configuration.addXML(wrapXML(tb));
		SchemaExport export = new SchemaExport(configuration);
		export.create(true, true);
	}

	/**
	 * 模型XML封装打包输出
	 * 
	 * @param clazzs
	 * @return
	 */
	public static String wrapXML(List<? extends BaseTable> clazzs) {
		Element mapping = document.addElement("hibernate-mapping");
		for (BaseTable c : clazzs) {
			mapping.add(c.getElement());
		}
		return document.asXML();
	}

	/**
	 * 单个模型打包输出
	 * 
	 * @param tb
	 * @return
	 */
	public static String wrapXML(BaseTable tb) {
		Element mapping = document.addElement("hibernate-mapping");
		mapping.add(tb.getElement());
		return document.asXML();
	}

	/**
	 * 根据默认的配置文件来创建数据库表
	 */
	public static void createTable(List<? extends BaseTable> clazzs) {
		createTable(HIBERNATE_CONFIG, clazzs);
	}

	/**
	 * 根据指定的hibernate配置文件把数据库DDL生成到指定的文件?
	 * 
	 * @param cfg
	 * @param destFile
	 */
	public static void createDDL2File(String cfg, String destFile) {
		SchemaExport export = new SchemaExport(new Configuration()
				.configure(cfg));
		export.setDelimiter(";").setOutputFile(destFile).create(true, false);
	}

	public static void createDDL2File(String destFile) {
		createDDL2File(HIBERNATE_CONFIG, destFile);
	}

//	public static void main(String[] args) {
//		List<BaseTable> l = new ArrayList();
//		l.add(new Company());
//		l.add(new Catalog());
//		createTable(l);
//		// createDDL2File("ssh.sql");
//	}

}
