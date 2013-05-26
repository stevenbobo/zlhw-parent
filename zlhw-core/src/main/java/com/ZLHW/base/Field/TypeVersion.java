package com.ZLHW.base.Field;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class TypeVersion extends BaseField{

	public TypeVersion() {
		super(Integer.class,"version");
		this.setComment("乐观锁:版本号");
	}

	@Override
	public Element getElement() {
		Element version =DocumentHelper.createElement("version");
		version.addAttribute("name", this.getFieldName());
		version.addAttribute("type", this.getType().getName());
		Element column = version.addElement("column");
		Element comment=column.addElement("comment");
		comment.addText(this.getComment());
		column.addAttribute("name", this.getFieldName());
		return version;
	}

}
