package com.ZLHW.base.table;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.Field.TypeManyToOne;
import com.ZLHW.base.Field.TypeOneToMany;
import com.ZLHW.base.Field.TypeVersion;
import com.ZLHW.base.HTable.BasicModel;
import com.ZLHW.base.Interface.XMLEcho;
import com.ZLHW.util.StringUtils;
/**
 * 表的表名，注释，所属数据库等数据从程序的注解中获取
 * @author Administrator
 *
 */

public abstract class BaseTable extends BasicModel implements XMLEcho, Serializable,Cloneable {
	public abstract BaseField getPrimaryField();// 获取主键FIELD
	Logger logger = LoggerFactory.getLogger(BaseTable.class);
	protected BaseField FIELD_Version=new TypeVersion();
	@Override  
	public Object clone() throws CloneNotSupportedException {  
	     return super.clone();  
	}  

	
	public String extendTableName(){
		return "BaseModel";
	}

	public static List<TypeManyToOne> getForeignField() {
		return null;
	}

	public static List<BaseField> getBasicField() {
		return null;
	}

	public static List<TypeOneToMany> getSetField() {
		return null;
	}

	public List<BaseField> getAllField() {
		List<BaseField> l = new ArrayList();
		l.add(getPrimaryField());// 添加主键
		l.addAll(getBasicField());
		l.addAll(getForeignField());
		return l;
	}

	/**
	 * 返回表结构XML
	 */
	public Element getElement() {
		// 类
		Element clazz = DocumentHelper.createElement("class");
		clazz.addAttribute("name", this.getClass().getName());
		clazz.addAttribute("table",this.getTableName());
		logger.info("正在映射:"+this.getTableName());
		Element comment=clazz.addElement("comment");//加入注释
		comment.addText(this.getComment());
		// clazz.addAttribute("catalog", this.DataBase);

		// 加入主键
		clazz.add(this.getPrimaryField().getElement());
		// 加入乐观锁
		if(this.getClass().getAnnotation(Optimistic.class)!=null)
		clazz.add(this.FIELD_Version.getElement());
		// 加入属性
		try {
			for (Field f : this.getClass().getFields()) {
				BaseField trueField;
				trueField = (BaseField) f.get(this);
				// 确保主键最先打印
				if (!trueField.getElement().getName().equals("id"))
					clazz.add(trueField.getElement());
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		// 输出
		return clazz;
	}

	/**
	 * 简化类型
	 * 
	 * @param className
	 * @return
	 */
	public String shortType(String className) {
		String[] name = className.split("\\.");
		return name[name.length - 1];
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

	// public AdAd getAdAd() {
	// if (_adAd == null)
	// _adAd = new AdAd();
	// return _adAd;
	// }
	public StringBuffer newTableForm(String name) {
		StringBuffer buf = new StringBuffer();
		buf.append("\tpublic ").append(shortType(this.getClass().getName()))
				.append(" get").append(capitalise(name)).append("(){\r\n");
		buf.append("\t   return this.bean;\r\n");
		buf.append("\t}\r\n");
		return buf;
	}

	/**
	 * 生成表单pojo
	 * 
	 * @param name
	 */
	public StringBuffer generateForForm(String name) {
		StringBuffer buf = new StringBuffer();
		StringBuffer beanDeclare = newTableForm(name);
		StringBuffer getsetMethod = new StringBuffer();
		for (Field f : this.getClass().getFields()) {
			try {
				// 反射获取实例
				BaseField trueField;
				trueField = (BaseField) f.get(this);
				getsetMethod.append(trueField.formGetMethod(name)).append(
						trueField.formSetMethod(name));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

		}
		return buf.append(beanDeclare).append(getsetMethod);
	}

	/**
	 * 打印所有的GET和SET方法
	 */
	public void printFieldList() {
		try {
			StringBuffer beanDeclare = new StringBuffer();
			StringBuffer getsetMethod = new StringBuffer();
			for (Field f : this.getClass().getDeclaredFields()) {
				if(BaseField.class.isAssignableFrom(f.getType())){
				BaseField trueField;
				// 反射获取实例
				trueField = (BaseField) f.get(this);
				beanDeclare.append(trueField.beanDeclare());
				getsetMethod.append(trueField.getMethod()).append(
						trueField.setMethod());
				}
			}
			System.out.print(beanDeclare.toString());
			System.out.print(getsetMethod.toString());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 返回所有的FLEX GET和SET方法
	 */
	public String printFLEXList() {
		try {
			StringBuffer content=new StringBuffer("package com.jnlxc.client.model{\r\n");
			content.append("\t[Bindable]\r\n");
			content.append("\t[RemoteClass(alias=\""+this.getClass().getName()+"\")]\r\n");
			content.append("\t public class ").append(this.getClass().getSimpleName()).append(" extends ")
			.append(this.extendTableName()).append("\r\n\t{\r\n");
			StringBuffer beanDeclare = new StringBuffer();
			for (Field f : this.getClass().getDeclaredFields()) {
				if(BaseField.class.isAssignableFrom(f.getType())){
				BaseField trueField;
				// 反射获取实例
				trueField = (BaseField) f.get(this);
				beanDeclare.append(trueField.FLEXbeanDeclare());
//				getsetMethod.append(trueField.getMethod()).append(
//						trueField.setMethod());
				}
			}
			if(this.getClass().getAnnotation(Optimistic.class)!=null)
				content.append(FIELD_Version.FLEXbeanDeclare());
			content.append(beanDeclare).append("\t}\r\n}");
			return content.toString();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public String getComment() {
		return this.getClass().getAnnotation(TableDeclare.class).comment();
	}

	public StringBuffer beanCreate() {
		return null;
	}

	public String getTableName() {
		return this.getClass().getAnnotation(TableDeclare.class).tableName();
	}

	public String getDataBase() {
		return this.getClass().getAnnotation(TableDeclare.class).dataBase;
	}

	@Override
	public String toString() {
		return super.toString();
	}


}
