package com.allblue.controller;

import com.allblue.model.BlueSystem;
import com.allblue.model.BlueUser;
import com.allblue.model.dto.ResultInfo;
import com.allblue.model.dto.ZTreeNode;
import com.allblue.service.BlueSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Description:
 * @Author Xone
 * @Date 17:37 2018/11/26
 **/
@Controller
@RequestMapping("/blueSystem")
public class BlueSystemController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlueSystemService blueSystemService;

    @RequestMapping(value = "/getSystemList", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getSystemList() {
        //查询系统列表
        List<ZTreeNode> list = blueSystemService.getSystemList();
        if (list == null) return ResultInfo.error("系统数据为空");
        return ResultInfo.success("系统数据获取成功", list);
    }

    @RequestMapping(value = "/getAllSystem", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getAllSystem() {
        //查询系统列表
        List<ZTreeNode> list = blueSystemService.getAllSystem();
        if (list == null) return ResultInfo.error("系统数据为空");
        return ResultInfo.success("系统数据获取成功", list);
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo save(@RequestParam(value = "code") String code,
                           @RequestParam(value = "name") String name,
                           @RequestParam(value = "url") String url,
                           @RequestParam(value = "parent_code") String parent_code,
                           @RequestParam(value = "remark") String remark,
                           HttpSession session) {
        //判断系统名是否已存在
        BlueSystem isExist = blueSystemService.getSystemInfo(code);
        if (null != isExist) {
            return ResultInfo.error("code已存在！");
        } else {
            //获取session内当前操作用户名
            BlueUser blueUser = (BlueUser) session.getAttribute("blueUser");

            int level = 1;
            BlueSystem blueSystem = new BlueSystem();
            blueSystem.setCode(code);
            blueSystem.setName(name);
            blueSystem.setUrl(url);
            blueSystem.setLevel(level);
            blueSystem.setParent_code(parent_code);
            blueSystem.setCreator(blueUser.getName());
            blueSystem.setModifier(blueUser.getName());
            blueSystem.setRemark(remark);
            //插入数据库
            int id = blueSystemService.add(blueSystem);
            if (id == 0) {
                return ResultInfo.error("新增系统失败！");
            }
            return ResultInfo.success("新建系统【" + name + "】成功!");
        }
    }

    @RequestMapping(value = "/{code}/detail", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo detail(@PathVariable("code") String code) {

        if (code == null || "".equals(code)) {
            return ResultInfo.error("系统code为空！");
        }
        BlueSystem systemInfo = blueSystemService.getSystemInfo(code);
        if (systemInfo == null) {
            return ResultInfo.error("系统信息不存在！");
        }
        return ResultInfo.success("SUCCESS", systemInfo);
    }

    @RequestMapping(value = "/{code}/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo update(@PathVariable("code") String code,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "url", required = false) String url,
                             @RequestParam(value = "remark", required = false) String remark,
                             HttpSession session) {

        if (code == null || "".equals(code)) {
            return ResultInfo.error("系统code为空！");
        }
        if ((name == null || "".equals(name)) &&
                (remark == null || "".equals(remark)) &&
                (url == null || "".equals(url))) {
            return ResultInfo.error("请填写要修改的信息!!!");
        }

        BlueSystem blueSystem = new BlueSystem();
        blueSystem.setCode(code);
        if (name != null && !"".equals(name)) {
            blueSystem.setName(name);
        }
        if (remark != null && !"".equals(remark)) {
            blueSystem.setRemark(remark);
        }
        if (url != null && !"".equals(url)) {
            blueSystem.setUrl(url);
        }
        BlueUser blueUser = (BlueUser) session.getAttribute("blueUser");
        blueSystem.setModifier(blueUser.getName());

        //update数据库
        int count = blueSystemService.update(blueSystem);
        if (count != 0) {
            return ResultInfo.success("更新系统【" + name + "】信息成功！");
        } else {
            return ResultInfo.error("修改失败,请重试！");
        }
    }

    @RequestMapping(value = "/{code}/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo deleteUser(@PathVariable("code") String code) {
        if (code == null || "".equals(code)) {
            return ResultInfo.error("系统code为空！");
        }
        deleteAll(code);
        return ResultInfo.success("删除系统【" + code + "】成功！");
    }

    /**
     * 删除code，在删除code为父节点的子数据
     **/
    private void deleteAll(String code) {
        blueSystemService.delete(code);
        List<String> codeList = blueSystemService.getListByParentCode(code);
        if (codeList != null) {
            for (String subCode : codeList) {
                deleteAll(subCode);
            }
        }
    }

    @RequestMapping(value = "/{roleId}/getZTreeNodesForAuthAction", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getZTreeNodesForAuthAction(@PathVariable("roleId") Integer roleId) {
        List<ZTreeNode> list = blueSystemService.getZTreeNodesForAuthAction(roleId);
        if (list == null) return ResultInfo.error("系统数据为空");
        return ResultInfo.success("系统数据获取成功", list);
    }
}
