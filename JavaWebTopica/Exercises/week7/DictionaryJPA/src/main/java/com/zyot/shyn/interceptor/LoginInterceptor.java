package com.zyot.shyn.interceptor;

import com.zyot.shyn.common.SessionAttr;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object role = session.getAttribute(SessionAttr.ROLE_ATTR);
        boolean isLogged = role != null;
        if (isLogged){
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        return true;
    }
}
