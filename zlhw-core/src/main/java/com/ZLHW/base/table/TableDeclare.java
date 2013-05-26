package com.ZLHW.base.table;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
@Retention(value = RetentionPolicy.RUNTIME)
public @interface TableDeclare {
	String tableName();
	String comment();
	String dataBase="";
}