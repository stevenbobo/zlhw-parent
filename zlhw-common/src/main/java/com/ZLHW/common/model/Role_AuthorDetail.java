package com.ZLHW.common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ZLHW.base.Field.BaseField;
import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.IntTable;
import com.ZLHW.base.table.TableDeclare;
@Entity
@Table(name="Base_Role_AuthorDetail")
@TableDeclare(comment = "角色-权限表", tableName = "Base_Role_AuthorDetail")
public class Role_AuthorDetail extends HIntTable{
	//已在beans.properties产生/更新当前类-------------------------------
	//自动生成的模型POJO-----------------------------------------
	//注释：JNLXC_Base_Role_AuthorDetail:角色-权限表
	//主键id类型：Integer
	@ManyToOne
	private Role role; //用户组
	@ManyToOne
	private AuthorDetail authorDetail; //权限明细表
	/**
	* 获取用户组
	*/
	public Role getRole(){
	    return this.role;
	}
	/**
	* 设置用户组
	* @param role
	*/
	public void setRole(Role role){
	    this.role = role;
	}
	/**
	* 获取权限明细表
	*/
	public AuthorDetail getAuthorDetail(){
	    return this.authorDetail;
	}
	/**
	* 设置权限明细表
	* @param authorDetail
	*/
	public void setAuthorDetail(AuthorDetail authorDetail){
	    this.authorDetail = authorDetail;
	}


}
