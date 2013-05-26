package com.ZLHW.base.Trigger;

import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.dao.IDAO;
import com.ZLHW.base.table.BaseTable;

public abstract class Trigger {
	public Trigger(IDAO dao, Class<? extends BaseTable> foreignClazz,BaseField foreignField) {
		this.dao=dao;
		this.foreignClazz = foreignClazz;
		this.foreignField=foreignField;
	}
	protected Class<? extends BaseTable> foreignClazz;
	protected BaseField foreignField;
	protected IDAO dao;
	

	public IDAO getDao() {
		return dao;
	}

	public void setDao(IDAO dao) {
		this.dao = dao;
	}

	public void beforeAdd(BaseTable bean) {
		
	} 

	public void beforeDelete(BaseTable bean) {
		
	}

	public void afterDelete(BaseTable bean) {
		// TODO Auto-generated method stub
		
	}

	public void beforeUpdate(BaseTable bean) {
		// TODO Auto-generated method stub
		
	}

	public void afterUpdate(BaseTable bean) {
		// TODO Auto-generated method stub
		
	}

	public void afterAdd(BaseTable bean) {
		// TODO Auto-generated method stub
		
	}

}
