package com.allblue.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.allblue.model.BlueUser;
import com.allblue.model.dto.ResultInfo;
import com.allblue.model.dto.SearchDTO;
import com.allblue.service.BlueUserService;
import com.allblue.utils.PropUtil;
import com.allblue.utils.UploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description:
 * @Author Xone
 * @Date 19:08 2018/7/19
 **/
@Controller
@RequestMapping("/blueUser")
public class BlueUserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private PropUtil propUtil = new PropUtil("FrameWork.properties");

    @Autowired
    private BlueUserService blueUserService;

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String userRegister(HttpServletRequest request) {
        //入参
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        //返回数据
        JSONObject re = new JSONObject();
        //入参判断
        if (name == null || "".equals(name) ||
                email == null || "".equals(email) ||
                password == null || "".equals(password)) {
            re.put("result", "fail");
            re.put("msg", "注册信息不能为空~");
            return JSON.toJSONString(re);
        }
        //判断用户名是否已存在
        BlueUser isExist = blueUserService.getUserInfo(name);
        if (null != isExist) {
            re.put("result", "fail");
            re.put("msg", "用户名已存在，换一个试试~");
            return JSON.toJSONString(re);
        } else {
            //加密密码
            BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
            password = bcryptPasswordEncoder.encode(password);

            BlueUser blueUser = new BlueUser();
            blueUser.setName(name);
            blueUser.setEmail(email);
            blueUser.setPassword(password);
            blueUser.setPhoto(propUtil.get("DefaultPhoto"));
            blueUser.setCreator(name);
            blueUser.setModifier(name);
            //插入数据库
            int id = blueUserService.add(blueUser);
            if (id != 0) {
                re.put("result", "success");
                re.put("msg", "注册成功~");
                logger.info("注册成功！信息：name:" + name);
            } else {
                re.put("result", "fail");
                re.put("msg", "注册失败,请重试~");
            }
            return JSON.toJSONString(re);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String userLogin(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        logger.info("name:" + name + "   password:" + password);

        BlueUser blueUser = blueUserService.getUserInfo(name, password);

        JSONObject re = new JSONObject();
        if (blueUser != null) {
            HttpSession session = request.getSession();
            session.setAttribute("blueUser", blueUser);

            re.put("result", "success");
            re.put("msg", "登录成功");

            Cookie nameCookie = new Cookie("name", name);
            nameCookie.setMaxAge(6000);
            nameCookie.setPath("/");
            response.addCookie(nameCookie);

            Cookie[] cookies = request.getCookies();
//            logger.info("外部的SessionId:" + session.getId());
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
//                    logger.info("Cookie里边的：" + session.getId());
                    cookie.setValue(session.getId());
                    cookie.setPath("/");
                    cookie.setMaxAge(6000);
                    response.addCookie(cookie);
                }
            }

        } else {
            re.put("result", "fail");
            re.put("msg", "用户名或密码错误~");
        }
        logger.info("用户【" + name + "】登录成功！");
        return JSON.toJSONString(re);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        logger.info("session: " + request.getSession().getAttributeNames());
        request.getSession().invalidate();
        Cookie nameCookie = new Cookie("name", "");
        nameCookie.setMaxAge(0);
        nameCookie.setPath("/");
        response.addCookie(nameCookie);
        logger.info("清除数据，退出登录！！！");
        return "common/login";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo save(@RequestParam(value = "name") String name,
                           @RequestParam(value = "email") String email,
                           HttpSession session) {
        //判断用户名是否已存在
        BlueUser isExist = blueUserService.getUserInfo(name);
        if (null != isExist) {
            return ResultInfo.error("用户已存在！");
        } else {
            //获取session内当前操作用户名
            BlueUser bl = (BlueUser) session.getAttribute("blueUser");

            BlueUser blueUser = new BlueUser();
            blueUser.setName(name);
            blueUser.setEmail(email);
            blueUser.setPassword(propUtil.get("DefaultPassword"));
            blueUser.setPhoto(propUtil.get("DefaultPhoto"));
            blueUser.setCreator(bl.getName());
            blueUser.setModifier(bl.getName());
            //插入数据库
            int id = blueUserService.add(blueUser);
            if (id == 0) {
                return ResultInfo.error("新增用户失败！");
            }
            return ResultInfo.success("新建用户【" + name + "】成功!");
        }
    }

    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo detail(@PathVariable("id") int id) {
        if (id == 0) {
            return ResultInfo.error("用户ID不正确！");
        }
        BlueUser userInfo = blueUserService.getUserInfo(id);
        if (userInfo == null) {
            return ResultInfo.error("用户信息不存在！");
        }
        logger.info("查询用户【" + id + "】成功");
        return ResultInfo.success("SUCCESS", userInfo);
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo update(@PathVariable("id") int id,
                             @RequestParam(value = "photo", required = false) String photo,
                             @RequestParam(value = "email", required = false) String email,
                             @RequestParam(value = "status", required = false) Integer status,
                             @RequestParam(value = "password", required = false) String password,
                             HttpSession session) {
        //入参判断
        if (id == 0) {
            return ResultInfo.error("用户ID不正确！");
        }
        if ((email == null || "".equals(email)) &&
                (password == null || "".equals(password)) &&
                (photo == null || "".equals(photo)) &&
                (status == null)) {
            return ResultInfo.error("请填写要修改的信息!!!");
        }

        BlueUser blueUser = new BlueUser();
        blueUser.setId(id);
        if (email != null && !"".equals(email)) {
            blueUser.setEmail(email);
        }
        if (password != null && !"".equals(password)) {
            blueUser.setPassword(password);
        }
        if (status == 0 || status == 1) {
            blueUser.setStatus(status);
        }
        if (photo != null && !"".equals(photo)) {
            blueUser.setPhoto(photo);
        }
        //从session中取当前的用户名
        BlueUser sn = (BlueUser) session.getAttribute("blueUser");
        blueUser.setModifier(sn.getName());

        //update数据库
        int count = blueUserService.update(blueUser);
        if (count != 0) {
            //更新session
            if (sn.getId() == id) {
                BlueUser bu = blueUserService.getUserInfo(id);
                session.setAttribute("blueUser", bu);
            }
            return ResultInfo.success("更新用户【" + id + "】信息成功！");
        } else {
            return ResultInfo.error("修改失败,请重试！");
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo upload(
            @RequestParam(value = "uploadImage", required = false) MultipartFile photo) {
        //上传照片
        String newName = UploadUtil.fileUpload(photo, propUtil.get("UserPhotoPath"));
        String url = "/photos/user/" + newName;
        return ResultInfo.success(url);
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo deleteUser(@PathVariable("id") int id) {
        if (id == 0) {
            return ResultInfo.error("用户ID不正确！");
        }
        blueUserService.delete(id);
        return ResultInfo.success("删除用户【" + id + "】成功！");
    }

    @RequestMapping(value = "/getUserListBySearchDTO", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getUserListBySearchDTO(
            @RequestParam(value = "searchContext", required = false) String opts,
            @RequestParam(value = "sort", required = false) String sortName,
            @RequestParam(value = "order", required = false) String sortOrder,
            @RequestParam(value = "offset", required = false) Integer pageNumber,
            @RequestParam(value = "limit", required = false) Integer pageSize) {

        //获取用户数量
        int totalCount = blueUserService.getUserTotalCount(opts);
        if (totalCount > 0) {
            //设置参数
            SearchDTO searchDTO = new SearchDTO();
            searchDTO.setOffset(pageNumber);
            searchDTO.setLimit(pageSize);
            searchDTO.setSearchContext(opts);
            searchDTO.setSortName(sortName);
            searchDTO.setSortOrder(sortOrder);

            //获取符合条件的用户列表
            List<BlueUser> list = blueUserService.getUserListBySearchDTO(searchDTO);

            return ResultInfo.success(Integer.toString(totalCount), list);
        }
        return ResultInfo.error("用户数量<0!");
    }
}
