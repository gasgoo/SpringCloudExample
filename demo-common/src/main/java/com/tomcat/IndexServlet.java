package com.tomcat;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description
 * @Author admin
 * @Date 2020/4/18 17:02
 */
@Slf4j
public class IndexServlet extends HttpServlet {



    @Override
    public void service(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        log.info("============IndexServlet======");
    }


}
