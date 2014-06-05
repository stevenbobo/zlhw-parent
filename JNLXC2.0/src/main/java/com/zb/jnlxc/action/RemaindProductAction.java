package com.zb.jnlxc.action;

import com.zb.jnlxc.form.MiniPageReq;
import com.zb.jnlxc.form.MiniPageRsp;
import com.zb.jnlxc.model.Admin;
import com.zb.jnlxc.service.RemaindProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * Created by zhengbo.zb on 2014/6/3.
 */
@Controller
@RequestMapping("/RemaindProductAction")
@SessionAttributes(value = {"user"})
public class RemaindProductAction {
    @Resource
    RemaindProductService remaindProductService;

    @ResponseBody
    @RequestMapping("/distributionToOrderDetail")
    public void distributionToOrderDetail(Integer remaindProductId,Integer orderDetailId,Integer quantity,@ModelAttribute("user") Admin user){
        remaindProductService.distributionToOrderDetail(remaindProductId,orderDetailId,quantity,user);
    }

    @ResponseBody
    @RequestMapping("/loadRemaindProduct")
    public MiniPageRsp loadRemaindProduct(MiniPageReq page,@ModelAttribute("user") Admin user,HttpServletRequest request) throws ParseException {
        page.setRequest(request);
        remaindProductService.loadRemaindProduct(page,user);
        return new MiniPageRsp(page.getResultData(),page.getTotalClum());
    }

    @ResponseBody
    @RequestMapping("/loadDistributionRecord")
    public MiniPageRsp loadDistributionRecord(MiniPageReq page,@ModelAttribute("user") Admin user,HttpServletRequest request) throws ParseException {
        page.setRequest(request);
        remaindProductService.loadDistributionRecord(page,user);
        return new MiniPageRsp(page.getResultData(),page.getTotalClum());
    }
}
