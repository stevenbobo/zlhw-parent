package com.zb.jnlxc.action;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zb.jnlxc.form.MiniPageRsp;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.model.DataDict;
import com.zb.jnlxc.model.Delivery;
import com.zb.jnlxc.service.DeliveryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 13-2-1
 * Time: 上午12:29
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/DeliveryAction")
@SessionAttributes(value = {"user"})
public class DeliveryAction {
    @Resource
    DeliveryService deliveryService;
    @RequestMapping("/addDelivery")
    @ResponseBody
    public Delivery addDelivery(@ModelAttribute("user")Admin user,Delivery delivery,Integer schemeId){
        return deliveryService.create(delivery,schemeId,user);
    }
    @RequestMapping("/loadDelivery")
    @ResponseBody
    public MiniPageRsp loadDeliveryUrl(Integer schemeId){
        List<Delivery> l =deliveryService.getDeliveryBySchemeId(schemeId);
        return new MiniPageRsp(l);
    }

    @RequestMapping("/editDelivery")
    @ResponseBody
    public void editDelivery(String data){
        List<Delivery> datas = new Gson().fromJson(data,new TypeToken<List<Delivery>>() {}.getType());
        for(Delivery delivery:datas){
            deliveryService.update(delivery);
        }
    }
}
