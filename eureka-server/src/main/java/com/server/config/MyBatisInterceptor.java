package com.server.config;

import com.alibaba.druid.pool.DruidPooledPreparedStatement;
import com.mysql.cj.jdbc.ClientPreparedStatement;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.jdbc.PreparedStatementLogger;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.Statement;
import java.util.Properties;


/**
 * 自定义mybatis拦截器  配置执行时间超过 多少秒则打印日志
 * @Date 2020/6/19 20:27
 * @name MyBatisInterceptor
 */

@Intercepts({@Signature(type= StatementHandler.class,method = "query",args = {Statement.class, ResultHandler.class})})
@Component
@Slf4j
public class MyBatisInterceptor implements Interceptor {

    private long threshold=1;
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("myBatis拦截器执行》》》");
        long begin=System.currentTimeMillis();
        Object res=invocation.proceed();
        long end=System.currentTimeMillis();
        long runTime=end-begin;
        if(runTime>=threshold){
            DruidPooledPreparedStatement mappedStatement = (DruidPooledPreparedStatement) invocation.getArgs()[0];
            String sql= mappedStatement.getSql();
            System.out.println("sql语句: "+sql +"执行时间:"+runTime+"毫秒");

        }
        return res;
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o,this);
    }

    @Override
    public void setProperties(Properties properties) {
        this.threshold=Long.valueOf(properties.getProperty("threshold"));
    }
}
