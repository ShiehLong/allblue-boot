package com.allblue.controller;

import com.allblue.model.BlueUser;
import com.allblue.service.BlueUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/view")
public class BlueViewController {

    @Autowired
    BlueUserService blueUserService;

    @RequestMapping(value = "/login")
    public String login() {
        return "common/login";
    }

    @RequestMapping(value = "/register")
    public String register() {
        return "common/register";
    }

    @RequestMapping(value = "/access")
    public String error() {
        return "common/access";
    }

    @RequestMapping(value = "/userList")
    public String userList() {
        return "user/list";
    }

    @RequestMapping(value = "/userDetail")
    public String userDetail() {
        return "user/detail";
    }

    @RequestMapping(value = "/systemList")
    public String systemList() {
        return "system/list";
    }

    @RequestMapping(value = "/roleList")
    public String roleList() {
        return "role/list";
    }

    @RequestMapping(value = "/index")
    public String index(HttpSession session) {
        BlueUser bl = (BlueUser) session.getAttribute("blueUser");
        if (bl == null) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            BlueUser blueUser = blueUserService.getUserInfo(username);
            session.setAttribute("blueUser", blueUser);
        }
        return "index";
    }
}
