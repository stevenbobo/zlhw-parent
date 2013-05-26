package com.zb.jnlxc.aop; /**
 *
 * @author geloin
 * @date 2012-3-27 下午2:29:35
 */

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.zb.jnlxc.model.Admin;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * @author geloin
 * @date 2012-3-27 下午2:29:35
 */
@Repository
public class LoginInterceptor extends HandlerInterceptorAdapter implements Filter {
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
