package com.template.demeter.web.controllers;

import com.template.demeter.persistent.models.User;
import com.template.demeter.users.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by eric on 15/5/20.
 */
@Controller
public class HelloController {

    @Resource(name="userService")
    private UserService userService;

    @RequestMapping("/hello")
    public ModelAndView hello(){
        User user = userService.getUser(1);
        ModelAndView result = new ModelAndView("index");
        result.addObject("username", user.getName());
        return result;
    }

}
