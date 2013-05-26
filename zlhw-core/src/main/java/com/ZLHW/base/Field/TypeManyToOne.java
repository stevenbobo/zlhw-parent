package com.ZLHW.base.Field;


import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ZLHW.base.table.BaseTable;
import com.ZLHW.base.table.TableDeclare;

public class TypeManyToOne extends BaseField {
	/**
	 * 多对一关系的字段类型为复杂类型，必须继承于BaseTable
	 * @param type
	 * @param fieldName
	 */
	public TypeManyToOne(Class<? extends BaseTable> type, String fieldName) {
		super(type, fieldName);
		this.setComment(type.getAnnotation(TableDeclare.class).comment());
	}
	
	/**
	 * 多对一关系的字段类型为复杂类型，必须继承于BaseTable，默认表字段名为外键名
	 * @param type
	 * @param fieldName
	 */
	public TypeManyToOne(Class<? extends BaseTable> type) {
		this(type, StringUtils.uncapitalise(type.getSimpleName()));
	}

	/**
	 * 用于生成hibernate的配置文件
	 */
	public Element getElement() {
		Element manyToOne = DocumentHelper.createElement("many-to-one");
		manyToOne.addAttribute("name", this.getFieldName());
		manyToOne.addAttribute("class", this.getType().getName());
		manyToOne.addAttribute("fetch", "select");
		if(lazy==false)manyToOne.addAttribute("lazy", "false");
		Element column = manyToOne.addElement("column");
		Element comment=column.addElement("comment");//加入注释
		comment.addText(this.getComment());
		column.addAttribute("name", this.getFieldName());
		return manyToOne;
	}

	/**
	 * 获取bean的主键field
	 * 
	 * @return
	 */
	public Class getTablePrimaryType() {
		BaseTable tb = null;
		try {
			tb = (BaseTable) (this.getType().newInstance());

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tb.getPrimaryField().getType();
	}

	/**
	 * 表单pojo Getter方法实现打印
	 * 
	 * @param name  form中声明的变量的名字
	 * @return
	 */
	public StringBuffer formGetMethod(String name) {
		StringBuffer buf = new StringBuffer("\tpublic ").append(
				getTablePrimaryType().getSimpleName()).append(" get")
				.append(capitalise(this.getFieldName())).append("(){\r\n");
		buf.append("\t    return get").append(capitalise(name))
				.append("().get").append(capitalise(this.getFieldName()))
				.append("().getPrimary();\r\n");
		buf.append("\t}\r\n");
		return buf;
	}

	/**
	 * 表单pojo Setter方法实现打印
	 * 
	 * @param name  form中声明的变量的名字
	 * @return
	 */
	public StringBuffer formSetMethod(String name) {
		StringBuffer buf = new StringBuffer("\tpublic void").append(" set")
				.append(capitalise(this.getFieldName())).append("(").append(
						getTablePrimaryType().getSimpleName()).append(" ")
				.append(this.getFieldName()).append("){\r\n");
		// AdAdGrp temp = new AdAdGrp();
		buf.append("\t    ").append(this.getType().getSimpleName()).append(
				" temp = new ").append(this.getType().getSimpleName())
				.append("();\r\n");
		// temp.setKeyValue(adGrp);
		buf.append("\t    temp.setPrimary(").append(this.getFieldName())
				.append(");\r\n");
		//getAdAd().setAdGrp(temp);
		buf.append("\t    get").append(capitalise(name)).append("().set")
				.append(capitalise(this.getFieldName())).append("(temp);\r\n");
		buf.append("\t}\r\n");
		return buf;
	}
}
