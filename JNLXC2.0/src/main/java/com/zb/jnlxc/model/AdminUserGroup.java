package com.zb.jnlxc.model;

import com.ZLHW.base.HTable.HIntTable;
import com.ZLHW.base.table.TableDeclare;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Steven.Zheng
 * Date: 13-3-8
 * Time: 下午4:33
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="JNLXC_AdminUserGroup")
@TableDeclare(comment = "用户-用户组表", tableName = "AdminUserGroup")
public class AdminUserGroup extends HIntTable {
    @ManyToOne
    private Admin admin;
    @ManyToOne
    private UserGroup userGroup;

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }
}
