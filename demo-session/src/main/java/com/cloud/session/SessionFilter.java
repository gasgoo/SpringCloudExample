package com.cloud.session;

import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * session共享过滤器
 *
 * @Date 2020/7/23 14:20
 * @name SessionFilter
 */


public class SessionFilter implements Filter {

    public static final String USER_INFO = "user";


    private RedisTemplate redisTemplate;

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //包装request对象
        MyRequestWrapper myRequestWrapper = new MyRequestWrapper(request, redisTemplate);
        //如果未登陆，则拒绝请求，转向登陆页面
        String requestUrl = request.getServletPath();
        //不是登陆页面 、不是登陆状态
        if (!"/toLogin".equals(requestUrl) && !requestUrl.startsWith("/login") && !myRequestWrapper.isLogin()) {
            request.getRequestDispatcher("/toLogin").forward(myRequestWrapper, response);
            return;
        }

        try {
            filterChain.doFilter(myRequestWrapper, servletResponse);
        } finally {
//            System.out.println("代码2");
            //提交session到redis
            myRequestWrapper.commitSession();
        }
    }
}
