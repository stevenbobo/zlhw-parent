package com.zb.jnlxc.action;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.Form.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zb.jnlxc.form.MiniPageReq;
import com.zb.jnlxc.form.MiniPageRsp;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.AssociationCompany;
import com.zb.jnlxc.model.Client;
import com.zb.jnlxc.model.DataDict;
import com.zb.jnlxc.service.AssociationCompanyService;
import com.zb.jnlxc.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 12-12-29
 * Time: 下午3:27
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/AssociationCompanyAction")
@SessionAttributes(value = {"user"})
public class AssociationCompanyAction {
    @Resource
    AssociationCompanyService associationCompanyService;


    @ResponseBody
    @RequestMapping("/loadAssociation")
    public MiniPageRsp loadAssociation(MiniPageReq page,HttpServletRequest request) {
        page.setRequest(request);
        associationCompanyService.load(page);
        return new MiniPageRsp(page.getResultData(),page.getTotalClum());
    }


    @ResponseBody
    @RequestMapping("/editAssociation")
    public void editAssociation(String jsonData) {
        List<AssociationCompany> clents = new Gson().fromJson(jsonData,new TypeToken<List<AssociationCompany>>(){}.getType());
        for(AssociationCompany client:clents)
            if(client.getDbId()==null||client.getDbId().equals(0))
                associationCompanyService.create(client);
            else
                associationCompanyService.update(client);
    }

    @ResponseBody
    @RequestMapping("/deleteAssociation")
    public void deleteAssociation(Integer dbId) {
        associationCompanyService.deleteById(dbId);
    }
}
