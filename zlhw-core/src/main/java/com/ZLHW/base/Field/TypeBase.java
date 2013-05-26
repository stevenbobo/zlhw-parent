package com.ZLHW.base.Field;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
/**
 * 基本数据类型
 * @author Administrator
 *
 */
public class TypeBase extends BaseField {
	// 字段是否允许为空
	private boolean canotNull ;

	public boolean isCanBeNull() {
		return canotNull;
	}

	public TypeBase setCanBeNull(boolean canBeNull) {
		this.canotNull = canBeNull;
		return this;
	}

	public TypeBase canotNULL() {
		this.canotNull=true;
		return this;
	}
	
	public TypeBase(Class type, String fieldName) {
		super(type, fieldName);
		this.canotNull=false;
		// TODO Auto-generated constructor stub
	}

	public Element getElement() {
		Element property = DocumentHelper.createElement("property");
		property.addAttribute("name", this.getFieldName());
		property.addAttribute("type", this.getType().getName());
		Element column = property.addElement("column");
		Element comment=column.addElement("comment");//加入注释
		comment.addText(this.getComment());
		column.addAttribute("name", this.getFieldName());
		if (canotNull)
			column.addAttribute("not-null", "true");
		if (this.getLength() != null)
			column.addAttribute("length", this.getLength().toString());
		return property;

	}

}
