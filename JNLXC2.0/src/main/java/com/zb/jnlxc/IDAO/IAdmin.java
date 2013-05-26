package com.zb.jnlxc.IDAO;

import java.util.List;

import com.ZLHW.base.dao.IDAO;
import com.zb.jnlxc.model.Admin;



public interface IAdmin extends IDAO<Admin, Integer>{
	/**
	 * 查找所有的业务员
	 * @return
	 */
	public List<Admin> findAllAgent();
	
	public List<Admin> findAllRepairer();
	
	public List<Admin> findAllWaixie();
	
}
