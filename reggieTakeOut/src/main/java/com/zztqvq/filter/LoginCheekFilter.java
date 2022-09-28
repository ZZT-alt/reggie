package com.zztqvq.filter;

import com.alibaba.fastjson.JSON;
import com.zztqvq.common.BaseContext;
import com.zztqvq.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TODO 检查用户是否登录
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheekFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        log.info("拦截到请求:" + request.getRequestURI());
        //获取本次请求的URI
        String requestURI = request.getRequestURI();
        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        boolean check = Check(urls, requestURI);
        if (check) {
            log.info("本次请求:{}不需要处理", requestURI);
            filterChain.doFilter(request, response);
            return;
        }
        //后台已登录
        if (request.getSession().getAttribute("employee") != null) {
            log.info("已登录,本次请求:{}不需要处理", requestURI);
            BaseContext.setCurrentId((Long) request.getSession().getAttribute("employee"));
            filterChain.doFilter(request, response);
            return;
        }
        //移动端已登录
        if (request.getSession().getAttribute("user") != null) {
            log.info("已登录,本次请求:{}不需要处理", requestURI);
            BaseContext.setCurrentId((Long) request.getSession().getAttribute("user"));
            filterChain.doFilter(request, response);
            return;
        }
        log.error("本次请求:{}被拦截", requestURI);
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    /**
     * 路径匹配
     *
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean Check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
