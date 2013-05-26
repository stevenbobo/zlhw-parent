package com.ZLHW.base.table;

import java.io.Serializable;

import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.Field.TypePrimary;
import com.ZLHW.base.Field.TypeVersion;

/**
 * 主键为Interger类型的表结构
 * 
 * @author Administrator
 * 
 */
@TableDeclare(comment = "数值形主键", tableName = "IntTable")
public abstract class IntTable extends BaseTable {

	protected BaseField FIELD_DBID = new TypePrimary(Integer.class, "dbId",
			"sequence",this.getClass().getSimpleName()).setComment("数值形主键");

	@Override
	public BaseField getPrimaryField() {
		return FIELD_DBID;
	}
	public BaseField getVersionField(){
		return FIELD_Version;
	}

	private Integer dbId;

	public Integer getDbId() {
		return dbId;
	}

	public void setDbId(Integer dbId) {
		this.dbId = dbId;
	}

	@Override
	public Integer primary() {
		return dbId;
	}

	
	@Override
	public String extendTableName(){
		return "IntTable";
	}
	// 获取主键名称
	public  String primaryColumn(){
		return "dbid";
	}
	


}