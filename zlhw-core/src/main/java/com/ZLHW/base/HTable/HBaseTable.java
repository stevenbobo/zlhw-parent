package com.ZLHW.base.HTable;

import java.lang.reflect.Field;

import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.table.Optimistic;
import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class HBaseTable extends BasicModel{
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
