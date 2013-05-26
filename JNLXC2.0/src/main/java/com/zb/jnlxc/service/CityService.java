package com.zb.jnlxc.service;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ZLHW.base.factory.BeanFactory;
import com.ZLHW.base.service.BaseService;
import com.zb.jnlxc.dao.CityDAO;
import com.zb.jnlxc.model.City;
@Transactional
@Service
public class CityService extends BaseService<CityDAO,City, Integer> {

    public City addCityAfter(String newNodeName, Integer refNodeId){
        City newCity = new City();
        City refNode = getById(refNodeId);
        newCity.setName(newNodeName);
        newCity.setFather_dbId(refNode.getFather_dbId());
        create(newCity);
        updateCode(newCity);
        return newCity;
    }

    public City addChildCity(String newNodeName, Integer refNodeId){
        City newCity = new City();
        City refNode = getById(refNodeId);
        newCity.setName(newNodeName);
        newCity.setFather_dbId(refNode.getDbId());
        create(newCity);
        updateCode(newCity);
        return newCity;
    }

    public City updateCity(String newNodeName, Integer refNodeId){
        City refNode = getById(refNodeId);
        refNode.setName(newNodeName);
        refNode.setCode(getCode(refNode));
        update(refNode);
        return refNode;
    }

    public void updateCode(City city){
        city.setCode(getCode(city));
        this.update(city);
    }

    public String getCode(City city){
        if(city.getFather_dbId()==null||city.getFather_dbId().equals(0)) return city.getDbId()+"";
         City parentCity = getById(city.getFather_dbId());
        return getCode(parentCity)+"_"+city.getDbId();
    }
}
