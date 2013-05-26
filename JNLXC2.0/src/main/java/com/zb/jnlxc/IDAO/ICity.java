package com.zb.jnlxc.IDAO;

import java.util.List;

import com.ZLHW.base.dao.IDAO;
import com.zb.jnlxc.model.City;

public interface ICity extends IDAO<City,Integer>{
	public List<City> getChildCitys(City city);

	public City getCityTree(Integer id);
	
	public void refreshCityMap();
	
	
}
