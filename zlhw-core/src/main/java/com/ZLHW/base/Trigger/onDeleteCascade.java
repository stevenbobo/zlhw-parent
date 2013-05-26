package com.ZLHW.base.Trigger;

import java.util.List;

import org.hibernate.Query;

import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.dao.IDAO;
import com.ZLHW.base.table.BaseTable;

public class onDeleteCascade extends Trigger {
	public onDeleteCascade(IDAO dao, Class<? extends BaseTable> foreignClazz,BaseField foreignField) {
		super(dao,foreignClazz,foreignField);
	}
	@Override
	public void beforeDelete(BaseTable bean) {
		String hql = "from " + foreignClazz.getSimpleName() + " this where this."
				+ foreignField.getFieldName() + "=?";
		
//			Object value = bean.getClass().getMethod(
//					"get" + StringUtils.capitalise(foreignKey.getFieldName()))
//					.invoke(bean);
			Query query = this.getDao().getSession().createQuery(hql).setParameter(
					0, bean);
			List<BaseTable> l=query.list(); 
			for(BaseTable b:l) {
				this.getDao().getSession().delete(b);
			}
	}
}
