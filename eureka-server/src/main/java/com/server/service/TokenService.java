package com.server.service;

import com.server.redis.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.text.StrBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.UUID;

/**
 * Token服务
 * @Date 2019/12/27 16:08
 * @name TokenService
 */

@Slf4j
@Service
public class TokenService {

    private static final String TOKEN_NAME="token";
    private static final String REDIS_TOKEN_PREFIX="redisToken";

    @Autowired
    private RedisUtils redisUtils;


    public String createToken(){
        String str= UUID.randomUUID().toString();
        StrBuilder token =new StrBuilder();
        token.append(REDIS_TOKEN_PREFIX).append(str);
        redisUtils.set(token.toString(),token.toString());
        log.info("token={}",token.toString());
        return token.toString();
    }


    public void checkToken(HttpServletRequest request) throws Exception {
        String token = request.getHeader(TOKEN_NAME);
        // header中不存在token
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(TOKEN_NAME);
            // parameter中也不存在token
            if (StringUtils.isBlank(token)) {
                throw new Exception("参数为null");

            }
        }
        if (!Objects.nonNull(redisUtils.get(token))) {
            throw new Exception("token无效!");
        }
        boolean del = redisUtils.del(token);
        if (!del) {
            throw new Exception ("删除token失败!");
        }
    }
}
