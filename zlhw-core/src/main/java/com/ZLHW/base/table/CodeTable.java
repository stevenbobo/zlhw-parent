package com.ZLHW.base.table;

import java.io.Serializable;

import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.Field.TypePrimary;

/**
 * 用于主键为String类型的
 * @author Administrator
 *
 */
@TableDeclare(comment = "字符型主键", tableName = "CodeTable")
public abstract class CodeTable extends BaseTable{

	public static BaseField FIELD_CODE=new TypePrimary(String.class,"code","assigned").setLength(32).setComment("字符型主键");

	@Override
	public BaseField getPrimaryField() {
		return FIELD_CODE;
	}
	
	//在beans.properties产生当前类-------------------------------
	//自动生成的模型POJO-----------------------------------------
	
	private String code;
	public String getCode(){
	    return this.code;
	}
	public void setCode(String code){
	    this.code = code;
	}


	@Override
	public Serializable primary() {
		return code;
	}

	
	@Override
	public String extendTableName(){
		return "CodeTable";
	}

	// 获取主键名称
	public  String primaryColumn(){
		return "code";
	}
	

}
