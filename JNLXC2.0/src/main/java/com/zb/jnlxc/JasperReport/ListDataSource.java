package com.zb.jnlxc.JasperReport;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

public class ListDataSource implements JRDataSource {
	private Iterator iterator;
	private Object current;

	public ListDataSource(List l) {
		iterator = l.iterator();
	}

	public Object getFieldValue(JRField field) throws JRException {
		Class type = current.getClass();
		try {
			Method method = type
					.getMethod("get" + firstUpcase(field.getName()));
			return method.invoke(current);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return type;
	}

	public boolean next() throws JRException {
		if (iterator.hasNext()) {
			this.current=iterator.next();
			return true;
		} else {
			return false;
		}
	}
	
	public static String firstUpcase(String str) {
		char[] ar = str.toCharArray();
		int f = (int) ar[0];
		if (f >= 97 && f <= 122)
			ar[0] = (char) (ar[0] - 32);
		return new String(ar);
	}

}
