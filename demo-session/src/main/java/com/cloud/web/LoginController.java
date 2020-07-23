package com.cloud.web;

import com.cloud.session.CookieBasedSession;
import com.cloud.session.MyRequestWrapper;
import com.cloud.session.SessionFilter;
import com.cloud.session.UserForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description 会话共享 A
 * @Date 2020/7/23 15:45
 **/
@Controller
public class LoginController {

    @GetMapping("/toLogin")
    public String toLogin(Model model, MyRequestWrapper request) {
        UserForm user = new UserForm();
        user.setUsername("James");
        user.setPassword("James");
        model.addAttribute("user", user);

        return "login";
    }

    @PostMapping("/login")
    public void login(@ModelAttribute UserForm user, MyRequestWrapper request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession().setAttribute(SessionFilter.USER_INFO, user);

        //种cookie
        CookieBasedSession.onNewSession(request, response);
        //重定向
        response.sendRedirect("/index");
    }

    @GetMapping("/index")
    public ModelAndView index(MyRequestWrapper request) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("index");
        modelAndView.addObject("user", request.getSession().getAttribute(SessionFilter.USER_INFO));

        return modelAndView;
    }

}
