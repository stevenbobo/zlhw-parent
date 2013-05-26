package com.zb.jnlxc.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

@Entity
@Table(name="JNLXC_UserGroup_Author")
@TableDeclare(comment = "用户组-权限表", tableName = "UserGroup_Author")
public class UserGroup_Author extends HIntTable{
	@ManyToOne
	private UserGroup userGroup; //用户组
	@ManyToOne
	private AuthorDetail authorDetail; //权限明细表
	
	public UserGroup getUserGroup(){
	    return this.userGroup;
	}
	public void setUserGroup(UserGroup userGroup){
	    this.userGroup = userGroup;
	}
	public AuthorDetail getAuthorDetail(){
	    return this.authorDetail;
	}
	public void setAuthorDetail(AuthorDetail authorDetail){
	    this.authorDetail = authorDetail;
	}


}
