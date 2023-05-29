package com.oj.ojspringboot.filter;

import com.alibaba.fastjson.JSON;
import com.oj.ojspringboot.common.BaseContext;
import com.oj.ojspringboot.entity.Message;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 路径过滤 只开辟了静态资源入口和用户登录入口
 * 登录后开辟所用资源
 * @author ld
 * @since 1.8
 * @version 17
 */
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
//全路径过滤
public class LoginCheckFilter implements Filter {

    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    //路径匹配器
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        //获取本次的URI
        String requestURI = request.getRequestURI();

        //放行的url
        String[] urls=new String[]{
                "/html/**",
                "/user/login",
                "/user/logout",
                "/user/phoneSend",
                "/user/loginPhone",
                "/user/save"
        };
        boolean check=check(urls,requestURI);

        if(check){
            filterChain.doFilter(request,response);
            return;
        }
        //如果是登录的状态，也是直接放行
        if(request.getSession().getAttribute("user")!=null){

            Long userId=(Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);//将登录者id存入线程中

            filterChain.doFilter(request,response);
            return;
        }
        //未登录则返回未登录结果
        response.getWriter().write(JSON.toJSONString(Message.error("NOTLOGIN")));
    }

    public boolean check(String[] urls,String requestURI){
        for(String url:urls){
            boolean match=PATH_MATCHER.match(url,requestURI);
            if(match)
                return true;
        }
        return false;
    }
}
