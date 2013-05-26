package com.ZLHW.base.Field;

import java.io.Serializable;
import java.util.*;

import org.dom4j.Element;
import org.w3c.dom.Document;

import com.ZLHW.base.Interface.BeanSetGet;
import com.ZLHW.base.Interface.FormSetGet;
import com.ZLHW.base.Interface.XMLEcho;
import com.ZLHW.util.StringUtils;


public abstract class BaseField implements XMLEcho, BeanSetGet, FormSetGet,
		Serializable,Cloneable {
	private String fieldName; // 数据库字段名
	private Integer length = null; // 字段长度
	private Class type; // 外键类型
	private String comment = ""; // 注释
	protected Boolean lazy=false;//对一对多和多对一的字段有效
	
	public BaseField clone(){
			BaseField o = null;   
			 try {   
			o = (BaseField) super.clone();   
			} catch (CloneNotSupportedException e) {   
			e.printStackTrace();   
			}   
			return o;   
	}
	

	/**
	 * 自动加载关联内容，只对多对一得类型有效
	 * @return
	 */
	public BaseField AutoLoad() {
		this.lazy = false;
		return this;
	}
	
	/**
	 * 取消自动加载关联内容，只对多对一得类型有效
	 * @return
	 */
	public BaseField AutoLoadFalse() {
		this.lazy = true;
		return this;
	}

	public BaseField(Class type, String fieldName) {
		this.type = type;
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public BaseField setFieldName(String fieldName) {
		this.fieldName = fieldName;
		return this;
	}

	public Integer getLength() {
		return length;
	}

	public BaseField setLength(Integer length) {
		this.length = length;
		return this;
	}

	public Class getType() {
		return type;
	}

	public BaseField setType(Class type) {
		this.type = type;
		return this;
	}

	public String getComment() {
		return comment;
	}

	public BaseField setComment(String comment) {
		this.comment = comment;
		return this;
	}

	/**
	 * 首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public String capitalise(String str) {
		return StringUtils.capitalise(str);
	}

	/**
	 * 产生映射文件，子类必须实现的方法
	 */
	public abstract Element getElement();

	/**
	 * 声明一个bean
	 */
	public StringBuffer beanDeclare() {
		StringBuffer buf = new StringBuffer("\tprivate ").append(
				type.getSimpleName()).append(" ").append(this.getFieldName())
				.append("; //").append(this.getComment()).append("\r\n");
		return buf;
	}

	/**
	 * 声明一个FLEXbean
	 */
	public StringBuffer FLEXbeanDeclare() {
		
		String beanType=getType(type);
		StringBuffer buf = new StringBuffer("\tpublic var ").append(
				this.getFieldName()).append(" :").append(beanType)
				.append(";// ").append(this.getComment()).append("\r\n");
		return buf;
	}
	
	public String getType(Class clazz){
		Map<Class,String> typeMap=new HashMap();
		typeMap.put(Integer.class, "int");
		typeMap.put(boolean.class, "Boolean");
		typeMap.put(short.class, "int");
		typeMap.put(Short.class, "int");
		typeMap.put(Byte.class, "int");
		typeMap.put(byte.class, "int");
		typeMap.put(String.class, "String");
		typeMap.put(Double.class, "Number");
		typeMap.put(Long.class, "Number");
		typeMap.put(Float.class, "Number");
		typeMap.put(char.class, "String");
		typeMap.put(Calendar.class, "Date");
		typeMap.put(ArrayList.class, "ArrayCollection");
		typeMap.put(LinkedList.class, "ArrayCollection");
		typeMap.put(Map.class, "ArrayCollection");
		typeMap.put(Document.class, "XML");
		typeMap.put(Set.class, "ArrayCollection");
		typeMap.put(HashSet.class, "ArrayCollection");
		if(typeMap.get(clazz)!=null){
			return typeMap.get(clazz);
		}else{
			return clazz.getSimpleName();
		}
	}

	/**
	 * 获得模型getter方法字符串
	 */
	public StringBuffer getMethod() {
		StringBuffer buf = new StringBuffer("\t/**\r\n\t* 获取"+this.getComment()+"\r\n\t*/\r\n");
		buf.append("\tpublic ").append(
				type.getSimpleName()).append(" get").append(
				capitalise(this.getFieldName())).append("(){\r\n");
		buf.append("\t    return this.").append(this.getFieldName()).append(
				";\r\n");
		buf.append("\t}\r\n");
		return buf;
	}

	/**
	 * 获得模型setter方法字符串
	 */
	public StringBuffer setMethod() {
		StringBuffer buf = new StringBuffer("\t/**\r\n\t* 设置"+this.getComment()+"\r\n\t* @param "+this.getFieldName()+"\r\n\t*/\r\n");
		buf.append("\tpublic void").append(" set")
				.append(capitalise(this.getFieldName())).append("(").append(
						type.getSimpleName()).append(" ").append(
						this.getFieldName()).append("){\r\n");
		buf.append("\t    this.").append(this.getFieldName()).append(" = ")
				.append(this.getFieldName()).append(";\r\n");
		buf.append("\t}\r\n");
		return buf;
	}

	/**
	 * 表单pojo Getter方法实现打印
	 * 
	 * @param name
	 *            form中声明变量的名字
	 * @return
	 */
	public StringBuffer formGetMethod(String name) {
		StringBuffer buf = new StringBuffer("\t/**\r\n\t* 获取"+this.getComment()+"\r\n\t*/\r\n");
		buf.append("\tpublic ").append(
				type.getSimpleName()).append(" get").append(
				capitalise(this.getFieldName())).append("(){\r\n");
		buf.append("\t    return get").append(capitalise(name))
				.append("().get").append(capitalise(this.getFieldName()))
				.append("();\r\n");
		buf.append("\t}\r\n");
		return buf;
	}

	/**
	 * 表单pojo Setter方法实现打印
	 * 
	 * @param name
	 *            form中声明变量的名字
	 * @return
	 */
	public StringBuffer formSetMethod(String name) {
		StringBuffer buf = new StringBuffer("\tpublic void").append(" set")
				.append(capitalise(this.getFieldName())).append("(").append(
						type.getSimpleName()).append(" ").append(
						this.getFieldName()).append("){\r\n");
		buf.append("\t    get").append(capitalise(name)).append("().set")
				.append(capitalise(this.getFieldName())).append("(").append(
						this.getFieldName()).append(");\r\n");
		buf.append("\t}\r\n");
		return buf;
	}
	
	

}
