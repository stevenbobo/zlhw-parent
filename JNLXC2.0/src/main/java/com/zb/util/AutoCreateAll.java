//package com.zb.util;
//
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.lang.reflect.Field;
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.Element;
//import org.dom4j.io.SAXReader;
//import org.dom4j.io.XMLWriter;
//
//import com.zb.User.User;
//import com.zb.WebInfo.WebInfo;
//
//public class AutoCreateAll {
//	private User bean;
//	private String name ;
//	private String lname ;
//	private String primaryKey;
//	private String primaryName;
//	private String tableName;
//	Class bs;
//	public AutoCreateAll() {
//		bean = new User();
//		bs = bean.getClass();
//		int length=bean.getClass().getName().split("\\.").length;
//		name=bean.getClass().getName().split("\\.")[length-1];
//		lname = Tools.firstLowcase(name);
//		Field[] fields = bs.getDeclaredFields();
//		
//		System.out.println("类名："+name);
//		System.out.println("实例名："+lname);
//		getPrimary();
//		System.out.println("--------映射字段---------\n");
//		for(Field f:fields){
//			System.out.println(f.getName());
//		}
//		new File("src/com/zb/" + name ).mkdir();
//		new File("WebRoot/"+name ).mkdir();
//	}
//	public void getPrimary(){
//		try {
//			SAXReader reader = new SAXReader();
//			Document document= reader.read(new File("src/com/zb/"+name+"/"+name+".hbm.xml"));
//			Element mapping = document.getRootElement();
//			Element clazz=mapping.element("class");
//			tableName=clazz.attributeValue("table");
//			Element id=clazz.element("id");
//			primaryName=id.attributeValue("name");
//			primaryKey=id.attributeValue("type").split("\\.")[2];
//			System.out.println("主键类型："+primaryKey);
//			System.out.println("主键字段名："+primaryName);
//			System.out.println("表名："+tableName);
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	public void createDAO() throws IOException {
//		FileReader fr = new FileReader("src/com/zb/template/TemplateDAO.java");
//		char[] cc = new char[5000];
//		fr.read(cc);
//		FileWriter fw = new FileWriter("src/com/zb/" + name + "/" + name
//				+ "DAO.java");
//		String ss = new String(cc);
//		ss = ss.replaceAll("Template", name);
//		ss = ss.replaceAll("template", lname);
//		ss = ss.replaceAll("TPrimary", primaryKey);
//		ss = ss.trim();
//		fw.write(ss);
//		fr.close();
//		fw.close();
//	}
//
//	public void createServer() throws IOException {
//		FileReader fr = new FileReader(
//				"src/com/zb/template/TemplateServer.java");
//		char[] cc = new char[5000];
//		fr.read(cc);
//		FileWriter fw = new FileWriter("src/com/zb/" + name + "/" + name
//				+ "Server.java");
//		String ss = new String(cc);
//		ss = ss.replaceAll("Template", name);
//		ss = ss.replaceAll("template", lname);
//		ss = ss.replaceAll("TPrimary", primaryKey);
//		ss = ss.trim();
//		
//		String getInfo="自动获取表单信息并持久化";
//		Field[] fields = bs.getDeclaredFields();
//		for (Field f : fields) {
//			String fName = f.getName();
//			String TypeName = f.getType().getName().replace("java.lang.","");
//			String getMethodName = "get" + Tools.firstUpcase(fName);
//			String setMethodName = "set" + Tools.firstUpcase(fName);
//			String getStr = "t."+setMethodName+"(tf."+getMethodName+"());\n";
//			getInfo+=getStr;
//		}
//		ss = ss.replaceAll("Change", getInfo);
//		fw.write(ss);
//		fr.close();
//		fw.close();
//	}
//
//	public void createForm() throws IOException {
//		FileReader fr = new FileReader("src/com/zb/template/TemplateForm.java");
//		char[] cc = new char[5000];
//		fr.read(cc);
//		FileWriter fw = new FileWriter("src/com/zb/" + name + "/" + name
//				+ "Form.java");
//		String ss = new String(cc);
//		ss = ss.replaceAll("Template", name);
//		ss = ss.replaceAll("template", lname);
//		ss = ss.replaceAll("TPrimary", primaryKey);
//		ss = ss.trim();
//		String fInfo="";
//		String getInfo="";
//		String setInfo="";
//		Field[] fields = bs.getDeclaredFields();
//		for (Field f : fields) {
//			String fName = f.getName();
//			String TypeName = f.getType().getName().replace("java.lang.","");
//			String getMethodName = "get" + Tools.firstUpcase(fName);
//			String setMethodName = "set" + Tools.firstUpcase(fName);
//			String fieldStr = "private " + TypeName + " " + fName + ";\n";
//			String getStr = "public " + " " + TypeName + " " + getMethodName
//					+ " (){\n" + "return " + fName + ";\n" + "}\n";
//			String setStr = "public void " + setMethodName + " (" + TypeName
//					+ " " + fName + "){\n" + "this." + fName + "=" + fName
//					+ ";\n" + "}\n";
//			fInfo+=fieldStr;
//			getInfo+=getStr;
//			setInfo+=setStr;
//		}
//		String tableInfo = "根据"+name+"表生成的字段\n"+fInfo+getInfo+setInfo;
//		ss = ss.replaceAll("change", tableInfo);
//		fw.write(ss);
//		fr.close();
//		fw.close();
//	}
//	
//	public void createAction() throws IOException {
//		FileReader fr = new FileReader(
//				"src/com/zb/template/TemplateAction.java");
//		char[] cc = new char[8000];
//		fr.read(cc);
//		FileWriter fw = new FileWriter("src/com/zb/" + name + "/" + name
//				+ "Action.java");
//		String ss = new String(cc);
//		ss = ss.replaceAll("Template", name);
//		ss = ss.replaceAll("template", lname);
//		ss = ss.replaceAll("TPrimary", primaryKey);
//		ss = ss.replaceAll("tableName", tableName);
//		ss = ss.trim();
//		fw.write(ss);
//		fr.close();
//		fw.close();
//	}
//	
//	
//	public void createSelectJsp() throws IOException {
//		FileReader fr = new FileReader(
//				"WebRoot/Template/select.jsp");
//		char[] cc = new char[10000];
//		fr.read(cc);
//		FileWriter fw = new FileWriter("WebRoot/"+name+"/select.jsp");
//		String ss = new String(cc);
//		
//		ss = ss.replaceAll("Template", name);
//		ss = ss.replaceAll("template", lname);
//		ss = ss.replaceAll("TPrimary", primaryKey);
//		ss = ss.replaceAll("primaryName", primaryName);
//		ss = ss.trim();
//		
//		
//		String c1="\n";
//		String c2="\n";
//		Field[] fields = bs.getDeclaredFields();
//		for (Field f : fields) {
//			String fName = f.getName();
//			String TypeName = f.getType().getName().replace("java.lang.","");
//			String getMethodName = "get" + Tools.firstUpcase(fName);
//			String setMethodName = "set" + Tools.firstUpcase(fName);
//			String fieldStr = "<th>"+fName+"</th>\n";
//			String valueStr="<td>\\${"+lname+"."+fName+"}</td>\n";
//
//			c1+=fieldStr;
//			c2+=valueStr;
//		}
//		ss = ss.replaceAll("ForChange1", c1);
//		ss = ss.replaceAll("ForChange2", c2);
//		
//		fw.write(ss);
//		fr.close();
//		fw.close();
//	}
//	
//	public void createAddJsp() throws IOException {
//		FileReader fr = new FileReader(
//				"WebRoot/Template/add.jsp");
//		char[] cc = new char[10000];
//		fr.read(cc);
//		FileWriter fw = new FileWriter("WebRoot/"+name+"/add.jsp");
//		String ss = new String(cc);
//		
//		ss = ss.replaceAll("Template", name);
//		ss = ss.replaceAll("template", lname);
//		ss = ss.replaceAll("TPrimary", primaryKey);
//		ss = ss.trim();
//		
//		
//		String c1="\n";
//		Field[] fields = bs.getDeclaredFields();
//		for (Field f : fields) {
//			String fName = f.getName();
//			String TypeName = f.getType().getName().replace("java.lang.","");
//			String getMethodName = "get" + Tools.firstUpcase(fName);
//			String setMethodName = "set" + Tools.firstUpcase(fName);
//			String fieldStr = "<p>\n"+
//			"<label>"+fName+"：</label>\n"+
//			"<input name=\""+fName+"\"  type=\"text\" size=\"30\"/>\n"+
//		    "</p>\n";
//			c1+=fieldStr;
//		}
//		ss = ss.replaceAll("ForChange1", c1);
//		
//		fw.write(ss);
//		fr.close();
//		fw.close();
//	}
//	
//	
//	public void createUpdateJsp() throws IOException {
//		FileReader fr = new FileReader(
//				"WebRoot/Template/update.jsp");
//		char[] cc = new char[10000];
//		fr.read(cc);
//		FileWriter fw = new FileWriter("WebRoot/"+name+"/update.jsp");
//		String ss = new String(cc);
//		
//		ss = ss.replaceAll("Template", name);
//		ss = ss.replaceAll("template", lname);
//		ss = ss.replaceAll("TPrimary", primaryKey);
//		ss = ss.trim();
//		
//		String c1="\n";
//		Field[] fields = bs.getDeclaredFields();
//		for (Field f : fields) {
//			String fName = f.getName();
//			String TypeName = f.getType().getName().replace("java.lang.","");
//			String getMethodName = "get" + Tools.firstUpcase(fName);
//			String setMethodName = "set" + Tools.firstUpcase(fName);
//			String fieldStr = "<p>\n"+
//			"<label>"+fName+"：</label>\n"+
//			"<input name=\""+fName+"\"  type=\"text\" size=\"30\" value=\"\\${"+lname+"."+fName+"}\"/>\n"+
//		    "</p>\n";
//			c1+=fieldStr;
//		}
//		ss = ss.replaceAll("ForChange1", c1);
//		
//		fw.write(ss);
//		fr.close();
//		fw.close();
//	}
//	
//	public void editHibernateXML(){
//		SAXReader reader = new SAXReader();
//		Document document;
//		try {
//			document = reader.read(new File("WebRoot/WEB-INF/hibernate.cfg.xml"));
//			Element rootElm = document.getRootElement();
//			Element sessionFactory=rootElm.element("session-factory");
//			Element hSource =  sessionFactory.addElement("mapping");
//			hSource.addAttribute("resource","com/zb/"+name+"/"+name+".hbm.xml");
//			XMLWriter writer = new XMLWriter(new FileWriter("WebRoot/WEB-INF/hibernate.cfg.xml"));
//            writer.write(document);
//            writer.close();
//
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public void editApplicationContextXML(){
//		SAXReader reader = new SAXReader();
//		Document document;
//		try {
//			document = reader.read(new File("WebRoot/WEB-INF/applicationContext.xml"));
//			Element beans = document.getRootElement();
//			//DAO-------------------------------------------------------------
//			Element dao =  beans.addElement("bean");
//			dao.addAttribute("id",name+"Dao");
//			dao.addAttribute("class","com.zb."+name+"."+name+"DAO");
//			dao.addAttribute("parent", "DAO");
//			//Server----------------------------------------------------------
//			Element server = beans.addElement("bean");
//			server.addAttribute("id",name+"Server");
//			server.addAttribute("class","com.zb."+name+"."+name+"Server");
//			Element serProperty=server.addElement("property");
//			serProperty.addAttribute("name", "dao");
//			Element serRef=serProperty.addElement("ref");
//			serRef.addAttribute("bean", name+"Dao");
//			//action----------------------------------------------------------
//			Element action = beans.addElement("bean");
//			action.addAttribute("name","/"+lname);
//			action.addAttribute("class","com.zb."+name+"."+name+"Action");
//			Element actionProperty=action.addElement("property");
//			actionProperty.addAttribute("name", "serverDao");
//			Element actionRef=actionProperty.addElement("ref");
//			actionRef.addAttribute("bean", name+"Server");
//			
//			//更新保存----------------------------------------------------
//			XMLWriter writer = new XMLWriter(new FileWriter("WebRoot/WEB-INF/applicationContext.xml"));
//            writer.write(document);
//            writer.close();
//			
//
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public void editStrutXML(){
//		SAXReader reader = new SAXReader();
//		Document document;
//		try {
//			document = reader.read(new File("WebRoot/WEB-INF/struts-config.xml"));
//			Element struts = document.getRootElement();
//			//设置form----------------------------------------------
//			Element forms = struts.element("form-beans");
//			Element form =  forms.addElement("form-bean");
//			form.addAttribute("name", lname+"Form");
//			form.addAttribute("type", "com.zb."+name+"."+name+"Form");
//			
//			//设置action------------------------------------------------
//			Element actionMappings=struts.element("action-mappings");
//			Element action=actionMappings.addElement("action");
//			action.addAttribute("attribute", lname+"Form");
//			action.addAttribute("name", lname+"Form");
//			action.addAttribute("parameter", "method");
//			action.addAttribute("path", "/"+lname);
//			action.addAttribute("scope", "request");
//			action.addAttribute("type", "org.springframework.web.struts.DelegatingActionProxy");
//			action.addAttribute("cancellable", "true");
//			Element forward1=action.addElement("forward");
//			Element forward2=action.addElement("forward");
//			forward1.addAttribute("name", "success");
//			forward1.addAttribute("path", "/"+name+"/select.jsp");
//			forward2.addAttribute("name", "updatePage");
//			forward2.addAttribute("path", "/"+name+"/update.jsp");
//			
//			XMLWriter writer = new XMLWriter(new FileWriter("WebRoot/WEB-INF/struts-config.xml"));
//            writer.write(document);
//            writer.close();
//
//		} catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public static void main(String[] args) throws IOException {
//		AutoCreateAll aca = new AutoCreateAll();
//		aca.createDAO();
//		aca.createServer();
//		aca.createForm();
//		aca.createAction();
//		aca.createSelectJsp();
//		aca.createAddJsp();
//		aca.createUpdateJsp();
//		aca.editHibernateXML();
//		aca.editApplicationContextXML();
//		aca.editStrutXML();
//	}
//}
