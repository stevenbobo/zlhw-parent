package com.ZLHW.base.Trigger;

import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.dao.IDAO;
import com.ZLHW.base.table.BaseTable;

public class onDeleteNull extends Trigger {
	public onDeleteNull(IDAO dao, Class<? extends BaseTable> foreignClazz,
			BaseField foreignField) {
		super(dao, foreignClazz, foreignField);
	}

	@Override
	public void beforeDelete(BaseTable bean) {
		String sql = "update " + foreignClazz.getSimpleName() + " set "
				+ foreignField.getFieldName() + "=? where "
				+ foreignField.getFieldName() + "=?";
		this.getDao().getSession().createSQLQuery(sql).setParameter(0, "").setParameter(1, bean.primary()).executeUpdate();
	}
}
