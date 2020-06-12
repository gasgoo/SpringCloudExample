package com.feign.web;

import com.feign.api.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Author gg.rao
 * @Date 2019/4/2 19:45
 */
@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private ApiService apiService;

    @GetMapping("/getTasks")
    @ResponseBody
    public String listTasks(){
        return "任务列表";
    }

    /**
     * @Description  需要有ADMIN角色才能访问
     * @Date 2020/6/11 11:38
     **/
    @PostMapping("/addTask")
    @PreAuthorize("hasRole('ADMIN')")
    public String newTasks(){
        return "创建了一个新的任务";
    }
}


