package com.allblue.controller;

import com.allblue.model.po.BlueUser;
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

    @RequestMapping(value = "/user/list")
    public String userList() {
        return "user/list";
    }

    @RequestMapping(value = "/user/detail")
    public String userDetail() {
        return "user/detail";
    }

    @RequestMapping(value = "/system/list")
    public String systemList() {
        return "system/list";
    }

    @RequestMapping(value = "/role/list")
    public String roleList() {
        return "role/list";
    }

    @RequestMapping(value = "/photo/gallery")
    public String gallery(){
        return "photo/gallery";
    }

    @RequestMapping(value = "/photo/list")
    public String photoList(){
        return "photo/list";
    }
}
