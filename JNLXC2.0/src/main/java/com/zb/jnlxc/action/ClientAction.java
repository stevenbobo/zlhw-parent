package com.zb.jnlxc.action;

import com.ZLHW.base.Exception.BaseErrorModel;
import com.ZLHW.base.Form.Page;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zb.jnlxc.form.MiniPageReq;
import com.zb.jnlxc.form.MiniPageRsp;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.Client;
import com.zb.jnlxc.model.DataDict;
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
@RequestMapping("/ClientAction")
@SessionAttributes(value = {"user"})
public class ClientAction {
    @Resource
    ClientService clientService;

    @ResponseBody
    @RequestMapping("/loadClient")
    public MiniPageRsp loadClient(MiniPageReq page,@ModelAttribute("user") Admin user,HttpServletRequest request) {
        page.setRequest(request);
        clientService.loadClientByAgent(page,user);
        return new MiniPageRsp(page.getResultData(),page.getTotalClum());
    }

    @ResponseBody
    @RequestMapping("/editClient")
    public void editClient(String jsonData) {
        jsonData = jsonData.replace("{\"dbId\":\"\"}","null");
        List<Client> clents = new Gson().fromJson(jsonData,new TypeToken<List<Client>>(){}.getType());
        for(Client client:clents)
        if(client.getDbId()==null||client.getDbId().equals(0))
            clientService.addClient(client);
        else
            clientService.update(client);
    }

    @ResponseBody
    @RequestMapping("/deleteClient")
    public void addClient(Integer dbId) {
        clientService.deleteById(dbId);
    }
}
