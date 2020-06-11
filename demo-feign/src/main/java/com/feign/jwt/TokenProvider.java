package com.feign.jwt;

import com.feign.domain.Users;
import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class TokenProvider {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String SUBJECT = "congge";

    public static final long EXPIRITION = 1000 * 24 * 60 * 60 * 7;

    public static final String APPSECRET_KEY = "NmQ5MDk1Zjg3YTdhNmIxZTliYzBlNzQ3NzMzMmY2ODdiZDE4ZGZkNTI3YjA4NDg4ZTNmMjRhNjFkNGM5NDQ3MTQ0YWZhMDQ1YzE4M2I0N2Q2ZGZjZGFkZjA3N2M2ZTlmNzRhNzAwMWY5MDgxYjY3MzBjN2UzYWE5NGZkM2RiODY=";


    private static final String ROLE_CLAIMS = "role";

    /**
     * @Description  根据User对象创建token
     * @Date 2020/6/11 20:09
     **/
    public static String generateJsonWebToken(Users user) {

        if (user.getUserId() == null || user.getUserName() == null || user.getUserPassword()==null ) {
            return null;
        }

        Map<String,Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, "role");

        String token = Jwts
                .builder()
                .setSubject(SUBJECT)
                .setClaims(map)
                .claim("userId", user.getUserId())
                .claim("phone", user.getUserPhone())
                .claim("pwd", user.getUserPassword())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRITION))
                .signWith(SignatureAlgorithm.HS256, APPSECRET_KEY).compact();
        return token;
    }

    /**
     * 生成token
     * @param username
     * @param role
     * @return
     */
    public static String createToken(String username,String role) {

        Map<String,Object> claims = new HashMap<>();
        claims.put(ROLE_CLAIMS, role);

        String token = Jwts
                .builder()
                .setSubject(username)
                .setClaims(claims)
                .claim("username",username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRITION))
                .signWith(SignatureAlgorithm.HS256, APPSECRET_KEY).compact();
        return token;
    }




    public static Claims checkJWT(String token) {
        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
            return claims;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取用户名
     * @param token
     * @return
     */
    public static String getUsername(String token){
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        return claims.get("username").toString();
    }

    /**
     * 获取用户角色
     * @param token
     * @return
     */
    public static String getUserRole(String token){
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        if(Objects.isNull(claims.get("role")) || Strings.isNullOrEmpty(claims.get("role")+"")){
            log.error("用户没有角色属性!");
            return null;
        }
        return claims.get("role").toString();
    }

    /**
     * 是否过期
     * @param token
     * @return
     */
    public static boolean isExpiration(String token){
        Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
        return claims.getExpiration().before(new Date());
    }

    public static void main(String[] args) {
        String name = "niwodai";
        String role = "role";
        String token = createToken(name,role);
        System.out.println(token);

        Claims claims = checkJWT(token);
        System.out.println(claims.get("username"));

        System.out.println(getUsername(token));
        System.out.println(getUserRole(token));
        System.out.println(isExpiration(token));

    }



}
