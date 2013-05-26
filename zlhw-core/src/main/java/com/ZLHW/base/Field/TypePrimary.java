package com.ZLHW.base.Field;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class TypePrimary extends BaseField{

	private String generator="native";
	private String seqName="defalut_sequence";
	
	public TypePrimary(Class type, String fieldName,String generator) {
		super(type, fieldName);
		this.generator=generator;
		// TODO Auto-generated constructor stub
	}
	
	public TypePrimary(Class type, String fieldName,String generator,String seqName) {
		super(type, fieldName);
		this.generator=generator;
		this.seqName=seqName;
		// TODO Auto-generated constructor stub
	}

	public Element getElement() {
		Element id=DocumentHelper.createElement("id");
		id.addAttribute("name", this.getFieldName());
		id.addAttribute("type", this.getType().getName());
		Element column = id.addElement("column");
		Element comment=column.addElement("comment");
		comment.addText(this.getComment());
		column.addAttribute("name", this.getFieldName());
		if(this.getLength()!=null)
		column.addAttribute("length", this.getLength().toString());
		Element generator=id.addElement("generator");
		generator.addAttribute("class",this.getGenerator());
		if(this.generator.equals("sequence")){
			Element param=generator.addElement("param");
			param.addAttribute("name", "sequence");
			param.setText(this.seqName+"_sequence");
		}
		return id;
		
	}
	
	
	public String getGenerator() {
		return generator;
	}

	public TypePrimary setGenerator(String generator) {
		this.generator = generator;
		return this;
	}
	
	
}
