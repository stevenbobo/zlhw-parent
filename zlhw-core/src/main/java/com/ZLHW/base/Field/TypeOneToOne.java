package com.ZLHW.base.Field;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class TypeOneToOne extends BaseField{
	public TypeOneToOne(Class type, String fieldName) {
		super(type, fieldName);
		// TODO Auto-generated constructor stub
	}
	public Element getElement() {
		Element oneToOne=DocumentHelper.createElement("one-to-one");
		oneToOne.addAttribute("name", this.getFieldName());
		oneToOne.addAttribute("class", this.getType().getName());
		oneToOne.addAttribute("cascade", "all");
		return oneToOne;
		
	}
}
