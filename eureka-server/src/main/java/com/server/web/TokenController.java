package com.server.web;

import com.server.annotation.ApiIdempotent;
import com.server.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Date 2019/12/27 17:09
 * @name TokenController
 */

@RestController
@RequestMapping("/api")
@Slf4j
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/token")
    public String getToken(){
       return  tokenService.createToken();
    }

    @RequestMapping(value = "/repeat",method = RequestMethod.GET)
    @ApiIdempotent
    public String testIdempotence(HttpServletRequest request) throws Exception {
        tokenService.checkToken(request);
        return "ok";
    }

}
