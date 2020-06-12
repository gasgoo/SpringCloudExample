package com.feign.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.feign.jwt.TokenProvider;
import com.feign.jwt.UserDTO;
import com.feign.service.UserService;
import com.feign.dto.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 拦截用户的登录请求
 *
 * @Date 2020/6/11 10:42
 * @name JwtLoginFilter
 */

@Slf4j
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {


    @Autowired
    private UserService userService;
    private AuthenticationManager authenticationManager;

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 从输入流中获取到登录的信息
        try {
            LoginUser loginUser = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(JSON.toJSONString(loginUser), loginUser.getUserPassword(), new ArrayList<>()));
        } catch (IOException e) {
            log.error("exception:{}", e);
            return null;
        }
    }

    /**
     * @Description 成功验证后调用的方法  如果验证成功，就生成token并返回
     * @Date 2020/6/11 11:14
     **/
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        UserDTO userDTO = (UserDTO) authResult.getPrincipal();
        System.out.println("userDTO:" + userDTO.toString());

        String role = "";
        Collection<? extends GrantedAuthority> authorities = userDTO.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();
        }

        String token = TokenProvider.createToken(userDTO.getUserPhone(), role);
        //String token = JwtTokenUtils.createToken(userDTO.getUsername(), false);
        // 返回创建成功的token
        // 但是这里创建的token只是单纯的token
        // 按照jwt的规定，最后请求的时候应该是 `Bearer token`
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        String tokenStr = TokenProvider.TOKEN_PREFIX + token;
        ResUtils.responseJson(response,tokenStr);
    }



    /**
     * @Description   登录失败
     * @Date 2020/6/11 16:07
     **/
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().write("authentication failed, reason: " + failed.getMessage());
    }

    /**
     * 校验参数
     *
     * @param loginForm
     */
    private void checkLoginForm(LoginUser loginForm, HttpServletResponse response) {
        if (StringUtils.isBlank(loginForm.getUserPhone())) {
            ResponseUtil.addVaryFieldName(response, ("手机号不能为空"));
            return;
        }
        if (StringUtils.isBlank(loginForm.getUserPassword())) {
            ResponseUtil.addVaryFieldName(response, ("密码不能为空"));
            return;
        }
    }

}
