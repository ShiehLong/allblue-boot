package com.allblue.controller;

import com.allblue.model.po.BlueUser;
import com.allblue.model.po.Photo;
import com.allblue.service.BlueUserService;
import com.allblue.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/view")
public class BlueViewController {

    @Autowired
    BlueUserService blueUserService;

    @Autowired
    private PhotoService photoService;

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
        return "photo/detail";
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
    public String gallery() {
        return "photo/gallery";
    }

    @RequestMapping(value = "/photo/list")
    public String photoList() {
        return "photo/list";
    }

    @RequestMapping(value = "/photo/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable("id") int id, Model model) {
        //判断用户ID
        if (id == 0) {
            return "/photo/gallery";
        }
        Photo photoInfo = photoService.getPhotoDerail(id);
        if (StringUtils.isEmpty(photoInfo)) {
            return "/photo/gallery";
        }
        model.addAttribute("photoInfo", photoInfo);
        return "/photo/detail";
    }
}
