package com.zb.jnlxc.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.jbpm.api.identity.Group;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

@Entity
@Table(name="JNLXC_UserGroup")
@TableDeclare(comment = "用户组", tableName = "UserGroup")
public class UserGroup extends HIntTable implements Group{
	@Column(length=50)
	private String name; //名称
    private Byte groupLever;//操作等级,1为最高级别

	public String getName(){
	    return this.name;
	}
	public void setName(String name){
	    this.name = name;
	}
    @JsonIgnore
	public String getId() {
		return name;
	}
    @JsonIgnore
	public String getType() {
		return null;
	}

    public Byte getGroupLever() {
        return groupLever;
    }

    public void setGroupLever(Byte groupLever) {
        this.groupLever = groupLever;
    }
}
