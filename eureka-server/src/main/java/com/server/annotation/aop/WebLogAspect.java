package com.server.annotation.aop;
import com.server.redis.RedisUtils;
import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志切面类
 * @Author gg.rao
 * @Date 2019/5/22 11:52
 */
@Aspect
@Component
public class WebLogAspect {

    private final Logger log = LoggerFactory.getLogger(WebLogAspect.class);

    /*带有WebLog注解的方位为切入点*/
    @Pointcut("@annotation(com.server.annotation.WebLog)")
    public void webLog(){}

    /*定义@Around环绕 用于何时执行*/
    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start=System.currentTimeMillis();
        Object result=proceedingJoinPoint.proceed();
        log.info("Response :{}",new Gson().toJson(result));
        log.info("time consuming:{} ms",System.currentTimeMillis()-start );
        return result;
    }

    /*切点之前植入*/
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        log.info("请求信息:{}",request.getRemoteHost() + joinPoint.getSignature().getDeclaringTypeName());
        log.info("{}请求参数:{}",request.getRequestURI(),request.getQueryString());
    }
    /*切点之后植入*/
    @After("webLog()")
    public void doAfter(JoinPoint joinPoint){
        log.info("{}.{}方法执行完成！",joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName());
    }


}
