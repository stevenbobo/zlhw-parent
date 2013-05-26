/*
 *  FormBean.java
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Library General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *  
 *  Author: Winter Lau (javayou@gmail.com)
 *  http://dlog4j.sourceforge.net
 */
package com.ZLHW.base.Form;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.Test;
import junit.framework.TestResult;

import com.ZLHW.base.table.BaseTable;
import com.ZLHW.util.StringUtils;

/**
 * FormBean的基类
 * 
 * @author 郑波
 */
public abstract class BaseForm {
	String LN = "\r\n";
	public static final String STRING = "String";
	public static final String INTEGER = "int";

	private int sid; // 网站编号
	private String fromPage;
	private String __ClientId; // 客户端安全识别码
	private String method;// action对应方法
	
	Logger logger = LoggerFactory.getLogger(BaseForm.class);

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	public String get_ClientId() {
		return __ClientId;
	}

	public void set_ClientId(String clientId) {
		__ClientId = clientId;
	}

	/**
	 * 判断一个类是否为Form类型
	 * 
	 * @param clazz
	 * @return
	 */
	public boolean isFormType(Class clazz) {
		return BaseForm.class.isAssignableFrom(clazz);
	}


	/**
	 * 判断一个类是否为BaseTable类型
	 * 
	 * @param clazz
	 * @return
	 */
	public boolean isBeanType(Class clazz) {
		return BaseTable.class.isAssignableFrom(clazz);
	}

	/**
	 * 用junit打印form 的set，get方法
	 */
	@org.junit.Test
	public void echo() {
		logger.info(getFormBuf().toString());
	}
	/**
	 * 获取form的所有set，get方法
	 * @return
	 */
	public StringBuffer getFormBuf() {
		StringBuffer buf = new StringBuffer();
		Class cls = this.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			if (isFormType(field.getType())) {
				buf.append(getSetComplexVal(field));
			} else if (isBeanType(field.getType())) {
				try {
					BaseTable table = (BaseTable) field.getType().newInstance();
					buf.append(table.generateForForm(field.getName()));
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				buf.append(getSetBaseVal(field));

			}
		}
		return buf;
	}

	public StringBuffer getBaseVal(Field field) {
		StringBuffer buf = new StringBuffer();
		buf.append("\tpublic " + field.getType().getSimpleName() + " get"
				+ StringUtils.capitalise(field.getName()) + "(){" + LN);
		buf.append("\t return this." + field.getName() + ";" + LN);
		buf.append("\t}" + LN);
		buf.append(LN);
		return buf;
	}

	public StringBuffer setBaseVal(Field field) {
		StringBuffer buf = new StringBuffer();
		buf.append("\tpublic void set"
				+ StringUtils.capitalise(field.getName()) + "("
				+ field.getType().getSimpleName() + " " + field.getName()
				+ "){" + LN);
		buf.append("\t    this." + field.getName() + " = " + field.getName()
				+ ";" + LN);
		buf.append("\t}" + LN);
		buf.append(LN);
		return buf;
	}

	public StringBuffer getBaseVal(Field field, Field father) {
		StringBuffer buf = new StringBuffer();
		buf
				.append("\tpublic "
						+ StringUtils
								.splitExtendsion(field.getType().getName())
						+ " get" + StringUtils.capitalise(field.getName())
						+ "(){" + LN);
		buf.append("\t return get" + StringUtils.capitalise(father.getName())
				+ "()." + "get" + StringUtils.capitalise(field.getName())
				+ "();" + LN);
		buf.append("\t}" + LN);
		buf.append(LN);
		return buf;
	}

	public StringBuffer setBaseVal(Field field, Field father) {

		StringBuffer buf = new StringBuffer();
		buf.append("\tpublic void set"
				+ StringUtils.capitalise(field.getName()) + "("
				+ field.getType().getSimpleName() + " " + field.getName()
				+ "){" + LN);
		buf.append("\t " + " get" + StringUtils.capitalise(father.getName())
				+ "()." + "set" + StringUtils.capitalise(field.getName()) + "("
				+ field.getName() + ");" + LN);
		buf.append("\t}" + LN);
		buf.append(LN);
		return buf;
	}

	public StringBuffer getSetBaseVal(Field field) {
		return new StringBuffer().append(getBaseVal(field)).append(
				setBaseVal(field));
	}

	public StringBuffer newTableForm(Field field) {
		StringBuffer buf = new StringBuffer();
		buf.append("\tpublic ").append(field.getType().getSimpleName()).append(
				" get").append(StringUtils.capitalise(field.getName())).append(
				"(){\r\n");
		buf.append("\t   if(").append(field.getName()).append(" == null);\r\n");
		buf.append("\t   ").append(field.getName()).append(" = new ").append(
				field.getType().getSimpleName()).append("();\r\n");
		buf.append("\t   return ").append(field.getName()).append(";\r\n");
		buf.append("\t}\r\n");
		return buf;
	}

	public StringBuffer getSetComplexVal(Field field) {
		StringBuffer buf = new StringBuffer();
		buf.append(newTableForm(field));
		Class cls = field.getType();
		Field[] fields = cls.getDeclaredFields();
		for (Field f : fields) {
			buf.append(getBaseVal(f, field));
			buf.append(setBaseVal(f, field));
		}
		return buf;
	}

}

