package com.zb.jnlxc.action;

import com.ZLHW.base.Form.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zb.jnlxc.form.MiniPageReq;
import com.zb.jnlxc.form.MiniPageRsp;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.AuthorDetail;
import com.zb.jnlxc.model.DataDict;
import com.zb.jnlxc.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12-12-29
 * Time: 下午3:29
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/DataDictAction")
@SessionAttributes(value = {"user"})
public class DataDictAction {
    Logger logger = LoggerFactory.getLogger(DataDictAction.class);
    @Resource
    private DataDictService dataDictService;

    @ResponseBody
    @RequestMapping("/findLastLvDing")
    private DataDict findLastLvDing(){
        return dataDictService.findLastLvDing();
    }

    @RequestMapping("/loadDictList")
    @ResponseBody
    public MiniPageRsp loadDictList(MiniPageReq page, String code){
        dataDictService.loadDictList(page,code);
        return new MiniPageRsp(page.getResultData(),page.getTotalClum());
    }

    @RequestMapping("/findDictList")
    @ResponseBody
    public List findDictList(String code){
        return dataDictService.loadDictList(code);
    }
    @ResponseBody
    @RequestMapping("/editDict")
    public void editDict(String data){
        List<DataDict> dataDicts = new Gson().fromJson(data,new TypeToken<List<DataDict>>() {}.getType());
        for(DataDict dataDict:dataDicts){
            dataDictService.update(dataDict);
        }
    }
    @ResponseBody
    @RequestMapping("/deleteDict")
    public void deleteDict(Integer dbId){
        dataDictService.deleteById(dbId);
    }
}
