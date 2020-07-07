package com.tomcat;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * @Description 创建tomcat http://localhost:9070/app/index
 * @Author admin
 * @Date 2020/4/18 16:51
 */
@Slf4j
public class AppTomcat {

    public static void main(String[] args) throws LifecycleException, ServletException {
        Tomcat tomcat=new Tomcat();
        tomcat.getConnector().setPort(9070);
        HttpServlet servlet = new HttpServlet() {
            @Override
            public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
                res.getWriter().write("hello world! create Tomcat");
            }
        };
        log.info("start the tomcat on prot:9070");
        Context context=tomcat.addWebapp("/app","d:\\temp\\");
        tomcat.addServlet("/app","index",servlet);
        context.addServletMappingDecoded("/index", "index");
        tomcat.init();
        tomcat.start();
        tomcat.getServer().await();




    }

}
