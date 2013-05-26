package com.ZLHW.base.Field;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ZLHW.base.table.BaseTable;

public class TypeOneToMany extends BaseField{
	private Class childType;               //一对多类型
	private String childFildName;
	/**
	 * 一对多关系的字段类型为复杂类型，必须继承于BaseTable
	 * @param childFildName 子表中对应该表外键名
	 * @param childType 子表类型
	 * @param fieldName set类型名称
	 * 
	 */
	public TypeOneToMany(String childFildName,Class<? extends BaseTable> childType, String fieldName) {
		super(Set.class, fieldName);
		this.childType=childType;
		this.childFildName=childFildName;
	}
	
	/**
	 * 多对一关系的字段类型为复杂类型，必须继承于BaseTable（默认该表子表类型名称为子表类姓名+s）
	 * @param childFildName 子表中对应该表外键名
	 * @param childType 子表类型
	 */
	public TypeOneToMany(String childFildName,Class<? extends BaseTable> childType) {
		super(Set.class, StringUtils.uncapitalise(childType.getSimpleName())+"s");
		this.childType=childType;
		this.childFildName=childFildName;
	}
	
//	 <set name="products" inverse="true">
//     <key>
//         <column name="catelogid" />
//     </key>
//     <one-to-many class="com.zb.Product" />
// </set>
	/**
	 * 配置文件信息
	 */
	public Element getElement() {
		Element set = DocumentHelper.createElement("set");
		set.addAttribute("lazy","false");
		set.addAttribute("name", this.getFieldName());
		set.addAttribute("inverse", "true");//不设置级联操作
		Element key=set.addElement("key");
		Element column=key.addElement("column");
		Element comment=column.addElement("comment");//加入注释
		comment.addText(this.getComment());
		BaseTable bt;
		try {
			bt = (BaseTable)this.childType.newInstance();
		column.addAttribute("name",this.childFildName);//设置该集合所对应的外键
		Element oneToMany=set.addElement("one-to-many");
		oneToMany.addAttribute("class",this.childType.getName());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return set;
	}
	
	
	/**
	 * 声明一个bean
	 */
	public StringBuffer beanDeclare() {
		StringBuffer buf = new StringBuffer("\tprivate ").append(
				"Set<"+childType.getSimpleName()+"> ").append(
				this.getFieldName()).append("; //").append(this.getComment()).append("\r\n");
		return buf;
	}

	/**
	 * 获得模型getter方法字符串
	 */
	public StringBuffer getMethod() {
		StringBuffer buf = new StringBuffer("\tpublic Set<"+childType.getSimpleName()+"> get").append(
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
		StringBuffer buf = new StringBuffer("\tpublic void").append(" set")
				.append(capitalise(this.getFieldName())).append("(").append(
						"Set<"+childType.getSimpleName()+"> ").append(
						this.getFieldName()).append("){\r\n");
		buf.append("\t    this.").append(this.getFieldName()).append(" = ")
				.append(this.getFieldName()).append(";\r\n");
		buf.append("\t}\r\n");
		return buf;
	}


	@Override
	/**
	 * 无form属性
	 */
	public StringBuffer formGetMethod(String name) {
		return new StringBuffer("");
	}

	@Override
	public StringBuffer formSetMethod(String name) {
		return new StringBuffer("");
	}
	
	


	
}
