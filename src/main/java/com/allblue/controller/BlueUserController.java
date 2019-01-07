package com.allblue.controller;

import com.allblue.model.BlueUser;
import com.allblue.model.dto.ResultInfo;
import com.allblue.model.dto.SearchDTO;
import com.allblue.service.BlueUserService;
import com.allblue.utils.UploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description:
 * @Author Xone
 * @Date 19:08 2018/7/19
 **/
@RestController
@RequestMapping("/blueUser")
public class BlueUserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${allblue.UserPhotoPath}")
    private String UserPhotoPath;

    @Value("${allblue.DefaultPhoto}")
    private String DefaultPhoto;

    @Value("${allblue.DefaultPassword}")
    private String DefaultPassword;

    @Autowired
    private BlueUserService blueUserService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResultInfo userRegister(@RequestParam(value = "name") String name,
                                   @RequestParam(value = "email") String email,
                                   @RequestParam(value = "password") String password) {

        //判断用户名是否已存在
        BlueUser isExist = blueUserService.getUserInfo(name);
        if (null != isExist) {
            return ResultInfo.error("用户名已存在，换一个试试");
        }

        //加密密码
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        password = bcryptPasswordEncoder.encode(password);

        BlueUser blueUser = new BlueUser();
        blueUser.setName(name);
        blueUser.setEmail(email);
        blueUser.setPhoto(DefaultPhoto);
        blueUser.setPassword(password);
        blueUser.setCreator(name);
        blueUser.setModifier(name);

        //插入数据库
        int id = blueUserService.add(blueUser);
        if (id == 0) {
            return ResultInfo.error("注册失败,请重试");
        }
        return ResultInfo.success("注册用户【" + name + "】成功!");
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResultInfo userAdd(@RequestParam(value = "name") String name,
                              @RequestParam(value = "email") String email,
                              HttpSession session) {

        //判断用户名是否已存在
        BlueUser isExist = blueUserService.getUserInfo(name);
        if (null != isExist) {
            return ResultInfo.error("用户名已存在，换一个试试");
        }

        //加密密码
        BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bcryptPasswordEncoder.encode(DefaultPassword);

        BlueUser blueUser = new BlueUser();
        blueUser.setName(name);
        blueUser.setEmail(email);
        blueUser.setPhoto(DefaultPhoto);
        blueUser.setPassword(password);
        //获取session内当前操作用户名
        BlueUser bl = (BlueUser) session.getAttribute("blueUser");
        blueUser.setCreator(bl.getName());
        blueUser.setModifier(bl.getName());

        //插入数据库
        int id = blueUserService.add(blueUser);
        if (id == 0) {
            return ResultInfo.error("注册失败,请重试");
        }
        return ResultInfo.success("新建用户【" + name + "】成功!");
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
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

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
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
            //加密密码
            BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
            password = bcryptPasswordEncoder.encode(password);
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
    public ResultInfo upload(
            @RequestParam(value = "uploadImage", required = false) MultipartFile photo) {
        //上传照片
        String newName = UploadUtil.fileUpload(photo, UserPhotoPath);
        String url = "/photos/user/" + newName;
        return ResultInfo.success(url);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResultInfo deleteUser(@PathVariable("id") int id) {
        if (id == 0) {
            return ResultInfo.error("用户ID不正确！");
        }
        blueUserService.delete(id);
        return ResultInfo.success("删除用户【" + id + "】成功！");
    }

    @RequestMapping(value = "/getUserListBySearchDTO", method = RequestMethod.POST)
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
