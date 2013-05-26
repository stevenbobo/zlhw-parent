package com.zb.jnlxc.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

@Entity
@Table(name="JNLXC_City")
@TableDeclare(comment = "城市类别", tableName = "City")
public class City extends HIntTable{
	@Column(length=50)
	private String name; //名称
	private Integer father_dbId; //父城市,为空则为一级城市
    private String code;

	/**
	* 获取名称
	*/
	public String getName(){
	    return this.name;
	}
	/**
	* 设置名称
	* @param name
	*/
	public void setName(String name){
	    this.name = name;
	}

    public Integer getFather_dbId() {
        return father_dbId;
    }

    public void setFather_dbId(Integer father_dbId) {
        this.father_dbId = father_dbId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
