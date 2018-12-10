package com.allblue.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class BlueViewController {

    @RequestMapping(value = "/login")
    public String login() {
        return "common/login";
    }

    @RequestMapping(value = "/register")
    public String register() {
        return "common/register";
    }

    @RequestMapping(value = "/error")
    public String error() {
        return "common/error";
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
    public String index() {
        return "index";
    }
}
