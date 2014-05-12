package com.zb.jnlxc.aop; /**
 *
 * @author geloin
 * @date 2012-3-27 下午2:29:35
 */

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zb.jnlxc.model.Admin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.spi.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * @author geloin
 * @date 2012-3-27 下午2:29:35
 */
@Repository
public class LoginInterceptor extends HandlerInterceptorAdapter implements Filter {
    private static final Log log = LogFactory.getLog(LoginInterceptor.class);
    private String loginURL = "/login.vm";

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        if (request.getServletPath().indexOf("/AdminAction/login")!=-1) return true;
        HttpSession session = request.getSession();
        Admin user = (Admin) session.getAttribute("user");
        if (user == null)
            request.getRequestDispatcher(loginURL).forward(request, response);
        else{
            //--todo 需要删掉
            log.info("test cookie user.getName()="+user.getName());
            Cookie oItem;
            // 因为Cookie 中不允许保存特殊字符, 所以采用 BASE64 编码，CookieUtil.encode()是BASE64编码方法,略..
            oItem = new Cookie("username", user.getName());
            oItem.setMaxAge(5*365*24*60*60);
            oItem.setPath("/");
            response.addCookie(oItem);
        }
        return true;

    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse rsp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rsp;
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        HttpSession session = request.getSession();
        if (request.getServletPath().equals(loginURL)) {
            chain.doFilter(request, response);
            return;
        } else {
            if (session == null) {
                request.getRequestDispatcher(loginURL).forward(request, response);
                return;
            } else {
                Admin loginUser = (Admin) session.getAttribute("user");
                if (loginUser == null) {
                    request.getRequestDispatcher(loginURL).forward(request, response);
                    return;
                } else {
                    chain.doFilter(request, response);
                    return;
                }
            }
        }
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }


    @Override
    public void destroy() {
    }
}
