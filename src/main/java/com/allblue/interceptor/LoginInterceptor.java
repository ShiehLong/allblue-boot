package com.allblue.interceptor;

import com.allblue.model.BlueUser;
import com.allblue.service.BlueUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlueUserService blueUserService;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        try {
            request.setCharacterEncoding("UTF-8");
            //取session和cookie
            Cookie[] cookies = request.getCookies();
            HttpSession session = request.getSession(false);
            if (cookies == null || session == null) {
                logger.error("cookie/session信息为空！！！");
                ajaxRequest(request, response);
                return false;
            }
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    String sessionId = session.getId();
                    if (!cookie.getValue().equals(sessionId)) {
                        logger.error("cookie信息与session信息不一致！！！");
                        ajaxRequest(request, response);
                        return false;
                    }
                    break;
                }
            }
            for (Cookie cookie2 : cookies) {
                String cookieKey = cookie2.getName();
                if (cookieKey.equals("name")) {
                    String cookieValue = cookie2.getValue();
                    BlueUser userInSession = (BlueUser) session.getAttribute("blueUser");
                    if (userInSession != null && cookieValue.equals(userInSession.getName())) {
                        BlueUser userInCookie = blueUserService.getUserInfo(cookieValue);
                        if (userInCookie.getPassword().equals(userInSession.getPassword())) {
                            logger.info("拦截器验证通过！");
                            return true;
                        }
                    }
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("拦截器异常：" + e);
        }
        logger.error("拦截器校验未通过！跳转登录页面!!!");
        ajaxRequest(request, response);
        return false;
    }

    /**
     * @Description: 由于ajax请求不能收到拦截器的返回消息，需要自己写入response
     * @Author Xone
     * @Date 15:57 2018/11/5
     **/
    private void ajaxRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            //如果是ajax请求响应头会有x-requested-with
            String header = request.getHeader("x-requested-with");
            if (header.equalsIgnoreCase("XMLHttpRequest")) {
                response.getWriter().write("loseSession");
            } else {
//                response.sendRedirect("/view/login");
                request.getRequestDispatcher("/view/login").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("拦截器响应数据异常：" + e);
        }
    }
}

