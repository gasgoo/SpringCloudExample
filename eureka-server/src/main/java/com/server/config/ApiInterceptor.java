package com.server.config;

import com.common.utils.RSAUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 拦截器
 * @Author gg.rao
 * @Date 2019/3/28 19:11
 */
public class ApiInterceptor implements HandlerInterceptor {

    private final static Logger log= LoggerFactory.getLogger(ApiInterceptor.class);

    private String salt="ed4ffcd453efab32";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入拦截器......");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/json;charset=utf8");
        //这里是MD5加密算法
        StringBuilder urlBuilder = getUrlAuthenticationApi(request);
        String sign = RSAUtils.MD5(urlBuilder.toString() + salt);
        String signature = request.getHeader("signature");
        log.info("加密前传入的签名" + signature);
        log.info("后端加密后的签名" + sign);
        if (sign.equals(signature)) {
            return true;
        } else {            //签名错误
            response.getWriter().print("签名错误");
            response.getWriter().close();
            return false;
        }
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("逻辑处理中......");
    }
    @Override
    public  void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("拦截器后置处理......");
    }
    /*参数签名认证  取头部中的token timestamp字段*/
    private StringBuilder getUrlAuthenticationApi(HttpServletRequest request) {
        Enumeration<String> paramesNames = request.getParameterNames();
        List<String> nameList = new ArrayList<>();
        nameList.add("token");
        nameList.add("timestamp");
        while (paramesNames.hasMoreElements()){
            nameList.add(paramesNames.nextElement());
        }
        StringBuilder urlBuilder = new StringBuilder();
        nameList.stream().sorted().forEach(name -> {
            if ("token".equals(name) || "timestamp".equals(name)) {
                if ("token".equals(name) && null == request.getHeader(name)) {
                    return;
                }
                urlBuilder.append('&');
                urlBuilder.append(name).append('=').append(request.getHeader(name));
            } else {
                urlBuilder.append('&');
                urlBuilder.append(name).append('=').append(request.getParameter(name));
            }
        });
        urlBuilder.deleteCharAt(0);
        log.info("url : " + urlBuilder.toString());        return urlBuilder;
    }
}
