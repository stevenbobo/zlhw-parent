package com.zb.jnlxc.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: root
 * Date: 13-3-17
 * Time: 上午11:25
 * To change this template use File | Settings | File Templates.
 */
@Controller
@SessionAttributes(value = {"user"})
public class VelocityActon {
    Logger logger = LoggerFactory.getLogger(VelocityActon.class);
    @ResponseBody
    @RequestMapping("/*.do")
    public String autoMap(HttpServletRequest request)  {
         logger.info(request.getContextPath());
         return request.getContextPath();
    }
}
