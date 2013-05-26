package com.ZLHW.base.Field;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ZLHW.base.table.BaseTable;
/**
 * 多对多关系字段（尽量不用）
 * @author Administrator
 *
 */
public class TypeManyToMany extends BaseField{
	public TypeManyToMany(Class<? extends BaseTable> type, String fieldName) {
		super(type, fieldName);
	}
	
//	<set name="UsersManage" table="Users_Popedom">
//	<key column="PopedomId"/>
//	<many-to-many class="com.seipher.pojo.systemSet.UsersManage" column="UsersId"/>
//	</set>

	
	public Element getElement() {
		return null;
//		Element set = DocumentHelper.createElement("set");
//		set.addAttribute("name", asForeignKey.getFieldName());
//		set
//		set.addAttribute("inverse", "true");//不设置级联操作
//		Element key=set.addElement("key");
//		Element column=key.addElement("column");
//		column.addAttribute("name",asForeignKey.getFieldName());//设置该集合所对应的外键
//		Element oneToMany=set.addElement("one-to-many");
//		return set;
	}
}
