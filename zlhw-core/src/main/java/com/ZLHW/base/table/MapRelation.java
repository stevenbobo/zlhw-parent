package com.ZLHW.base.table;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class MapRelation{
	Document document;
	Map<String,Element> xmlMap;
	SAXReader reader = new SAXReader();
	public MapRelation() throws DocumentException {
		xmlMap=new HashMap();
		File file=new File("./WebRoot/WEB-INF/hibernate.cfg.xml");
		document = reader.read(file);
		Element rootElm = document.getRootElement();
		List<Element> mappings=rootElm.element("session-factory").elements("mapping");
		for(Element mapping:mappings) {
			Document tableXML=reader.read(new File("./WebRoot/WEB-INF/classes/"+mapping.attributeValue("resource")));
			Element eClass=tableXML.getRootElement().element("class");
			String classRoot=eClass.attributeValue("name");
			xmlMap.put(classRoot, eClass);
		}
	}
	public Element getXMLDocument(Class clz) throws DocumentException {
		return xmlMap.get(clz.getName());
	}
	public void mapBean() {
		
	}
}
