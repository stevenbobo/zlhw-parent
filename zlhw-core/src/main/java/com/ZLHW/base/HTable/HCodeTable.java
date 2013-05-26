package com.ZLHW.base.HTable;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public abstract class HCodeTable extends HBaseTable{
	@Id
	protected String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public Serializable primary() {
		return code;
	}

	@Override
	public String primaryColumn() {
		// TODO Auto-generated method stub
		return "code";
	}

}
