package com.zb.jnlxc.action;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zb.jnlxc.form.MiniPageReq;
import com.zb.jnlxc.form.MiniPageRsp;
import com.zb.jnlxc.model.*;
import com.zb.jnlxc.service.*;
import com.zb.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: stevenbobo
 * Date: 12-12-29
 * Time: 下午3:28
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/CommonAction")
@SessionAttributes(value = {"user"})
public class CommonAction {
    @Resource
    private DepartService departService;
    @Resource
    private ProductTeamService productTeamService;
    @Resource
    private AssociationCompanyService associationCompanyService;
    @Resource
    private CityService cityService;
    @Resource
    private MouldSizeService mouldSizeService;


    @ResponseBody
    @RequestMapping("/findAllDepart")
    private List findAllDepart(){
        return departService.findAll();
    }

    @ResponseBody
    @RequestMapping("/eidtDepart")
    private void eidtDepart(String data){
        List<Depart> departs = new Gson().fromJson(data,new TypeToken<List<Depart>>() {}.getType());
        for(Depart depart:departs)
        departService.update(depart);
    }

    @ResponseBody
    @RequestMapping("/deleteDepart")
    private void deleteDepart(int dbId){
        departService.deleteById(dbId);
    }

    @ResponseBody
    @RequestMapping("/findAllTeam")
    private List findAllTeam(){
        return productTeamService.findAll();
    }

    @ResponseBody
    @RequestMapping("/findAllTeamDetail")
    private List findAllTeamDetail(){
        return productTeamService.findAllTeamDetail();
    }

    @ResponseBody
    @RequestMapping("/eidtTeam")
    private void eidtTeam(String data){
        List<ProductTeam> teams = new Gson().fromJson(data,new TypeToken<List<ProductTeam>>() {}.getType());
        for(ProductTeam team:teams)
            productTeamService.update(team);
    }

    @ResponseBody
    @RequestMapping("/deleteTeam")
    private void deleteTeam(int dbId){
        productTeamService.deleteById(dbId);
    }

    @ResponseBody
    @RequestMapping("/findAllAssociation")
    private List findAllAssociation(){
        return associationCompanyService.findAll();
    }

    @ResponseBody
    @RequestMapping("/eidtAssociation")
    private void eidtAssociation(String data){
        List<AssociationCompany> associationCompanys = new Gson().fromJson(data,new TypeToken<List<AssociationCompany>>() {}.getType());
        for(AssociationCompany association:associationCompanys)
            associationCompanyService.update(association);
    }

    @ResponseBody
    @RequestMapping("/deleteAssociation")
    private void deleteAssociation(int dbId){
        associationCompanyService.deleteById(dbId);
    }

    @ResponseBody
    @RequestMapping("/findAllCity")
    private List findAllCity(){
        return cityService.findAll();
    }

    @ResponseBody
    @RequestMapping("/eidtCity")
    private void eidtCity(City city){
        cityService.update(city);
    }

    @ResponseBody
    @RequestMapping("/addCity")
    private City addCity(String newNodeName, String operate, Integer refNodeId){
        if (operate.equals("after")){
            return cityService.addCityAfter(newNodeName,refNodeId);
        }else if (operate.equals("child")){
            return cityService.addChildCity(newNodeName,refNodeId);
        }else if (operate.equals("edit")){
            return cityService.updateCity(newNodeName,refNodeId);
        }else
            return null;
    }

    @ResponseBody
    @RequestMapping("/deleteCity")
    private void deleteCity(int dbId){
        cityService.deleteById(dbId);
    }

    @ResponseBody
    @RequestMapping("/findAllMouldSize")
    private MiniPageRsp findAllMouldSize(MiniPageReq page,String key){
        if(StringUtils.isEmpty(key)) key="";
         mouldSizeService.loadList(page,key);
        return new MiniPageRsp(page.getResultData(),page.getTotalClum());
    }

    @ResponseBody
    @RequestMapping("/eidtMouldSize")
    private void eidtMouldSize(String data){
        List<MouldSize> datas = new Gson().fromJson(data,new TypeToken<List<MouldSize>>() {}.getType());
        for(MouldSize mouldSize:datas)
            mouldSizeService.update(mouldSize);
    }

    @ResponseBody
    @RequestMapping("/deleteMouldSize")
    private void deleteMouldSize(int dbId){
        mouldSizeService.deleteById(dbId);
    }

}
