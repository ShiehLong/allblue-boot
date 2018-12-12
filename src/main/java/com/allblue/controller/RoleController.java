package com.allblue.controller;

import com.alibaba.fastjson.JSON;
import com.allblue.model.Role;
import com.allblue.model.dto.RoleDTO;
import com.allblue.service.RoleService;
import com.allblue.utils.UploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @Author Xone
 * @Date 17:26 2018/8/31
 **/
@Controller
@RequestMapping("/role")
public class RoleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${allblue.RolePhotoPath}")
    private String RolePhotoPath;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = {"text/json;charset=UTF-8"})
    public String addRole(@ModelAttribute RoleDTO roleDTO) {

        //入参判断
        if (roleDTO.getName() == null || "".equals(roleDTO.getName()) ||
                roleDTO.getSex() == null || "".equals(roleDTO.getSex()) ||
                roleDTO.getAge() <= 0 ||
                roleDTO.getDescription() == null || "".equals(roleDTO.getDescription()) ||
                roleDTO.getVideo() == null || "".equals(roleDTO.getVideo()) ||
                roleDTO.getPic().getSize() <= 0) {
            logger.info("角色信息不完整!!!");
            return "redirect:/role/list";
        }

        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setSex(roleDTO.getSex());
        role.setAge(roleDTO.getAge());
        role.setDescription(roleDTO.getDescription());
        role.setVideo(roleDTO.getVideo());

        //上传照片
        String newName = UploadUtil.fileUpload(roleDTO.getPic(), RolePhotoPath);
        if (newName != null) {
            role.setPic("/photos/role/" + newName);
        }

        int id = roleService.addRole(role);
        if (id != 0) {
            logger.info("添加角色成功!!!");
            return "redirect:/role/" + id + "/detail";
        } else {
            logger.info("添加角色失败!!!");
            return "redirect:/role/list";
        }
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String updateRolePage(@PathVariable("id") int id, Model model) {
        if (id == 0) {
            return "redirect:/role/list";
        }
        Role roleInfo = roleService.getRoleInfo(id);
        logger.info("查询到角色信息如下：名称-" + roleInfo.getName() +
                "/年龄-" + roleInfo.getAge() + "/图片-" + roleInfo.getPic());
        if (StringUtils.isEmpty(roleInfo)) {
            return "redirect:/role/list";
        }
        model.addAttribute("roleInfo", roleInfo);
        return "role/roleUpdate";
    }

    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST, produces = {"text/json;charset=UTF-8"})
    public String updateRole(@PathVariable("id") int id, @ModelAttribute RoleDTO roleDTO) {
        if (id == 0) {
            return "redirect:/role/list";
        }
        //入参判断
        if (roleDTO.getName() == null || "".equals(roleDTO.getName()) &&
                roleDTO.getSex() == null || "".equals(roleDTO.getSex()) &&
                roleDTO.getAge() <= 0 &&
                roleDTO.getDescription() == null || "".equals(roleDTO.getDescription()) &&
                roleDTO.getVideo() == null || "".equals(roleDTO.getVideo()) &&
                roleDTO.getPic().getSize() <= 0) {
            logger.info("角色信息不完整!!!");
            return "redirect:/role/list";
        }

        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setSex(roleDTO.getSex());
        role.setAge(roleDTO.getAge());
        role.setDescription(roleDTO.getDescription());
        role.setVideo(roleDTO.getVideo());
        role.setId(id);

        //上传照片
        String newName = UploadUtil.fileUpload(roleDTO.getPic(), RolePhotoPath);
        if (newName != null) {
            role.setPic("/photos/role/" + newName);
        }

        int count = roleService.updateRole(role);
        if (count != 0) {
            logger.info("更新角色成功!!!");
            return "redirect:/role/" + id + "/detail";
        } else {
            logger.info("更新角色失败!!!");
            return "redirect:/role/list";
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String roleList(Model model) {
        //获取用户信息列表
        List<Role> list = roleService.getRoleList();
        model.addAttribute("list", list);
        return "role/roleList";
    }

    @RequestMapping(value = "/roleHome", method = RequestMethod.GET)
    public String roleHome(Model model) {
        //获取用户信息列表
        List<Role> list = roleService.getRoleList();
        model.addAttribute("list", list);
        return "role/roleHome";
    }

    @RequestMapping(value = "/roleIndex", method = RequestMethod.GET)
    @ResponseBody
    public String roleIndex() {
        //获取用户信息列表
        List<Role> list = roleService.getRoleList();
        return JSON.toJSONString(list);
    }

    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    public String roleDetail(@PathVariable("id") int id, Model model) {
        //判断用户ID
        if (id == 0) {
            return "redirect:/role/list";
        }
        Role roleInfo = roleService.getRoleInfo(id);
        logger.info("查询到角色信息如下：名称-" + roleInfo.getName() +
                "/年龄-" + roleInfo.getAge() + "/图片-" + roleInfo.getPic());
        if (StringUtils.isEmpty(roleInfo)) {
            return "redirect:/role/list";
        }
        model.addAttribute("roleInfo", roleInfo);
        return "role/roleDetail";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String roleDelete(@PathVariable("id") int id) {
        if (id == 0) {
            return "redirect:/role/list";
        }
        boolean flag = roleService.deleteRole(id);
        if (flag) {
            logger.info("删除角色成功!!!");
        } else {
            logger.info("删除角色失败！！！");
        }
        return "redirect:/role/list";
    }

}
