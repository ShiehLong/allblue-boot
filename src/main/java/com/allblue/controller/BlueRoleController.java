package com.allblue.controller;

import com.allblue.model.BlueRole;
import com.allblue.model.BlueUser;
import com.allblue.model.dto.ResultInfo;
import com.allblue.model.dto.SearchDTO;
import com.allblue.model.vo.UserRoleVO;
import com.allblue.service.BlueRoleService;
import com.allblue.service.BlueUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/blueRole")
public class BlueRoleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlueRoleService blueRoleService;

    @Autowired
    private BlueUserService blueUserService;

    @RequestMapping(value = "/getRoleList", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo getUserListBySearchDTO(
            @RequestParam(value = "searchContext", required = false) String searchContext,
            @RequestParam(value = "sort", required = false) String sortName,
            @RequestParam(value = "order", required = false) String sortOrder,
            @RequestParam(value = "offset", required = false) Integer pageNumber,
            @RequestParam(value = "limit", required = false) Integer pageSize) {

        //获取角色数量
        int totalCount = blueRoleService.getRoleTotalCount(searchContext);
        if (totalCount > 0) {
            //设置参数
            SearchDTO searchDTO = new SearchDTO();
            searchDTO.setOffset(pageNumber);
            searchDTO.setLimit(pageSize);
            searchDTO.setSearchContext(searchContext);
            searchDTO.setSortName(sortName);
            searchDTO.setSortOrder(sortOrder);

            //获取符合条件的角色列表
            List<BlueRole> list = blueRoleService.getRoleList(searchDTO);

            return ResultInfo.success(Integer.toString(totalCount), list);
        }
        return ResultInfo.error("角色数量<0!");
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo save(@RequestParam(value = "name") String name,
                           @RequestParam(value = "code") String code,
                           @RequestParam(value = "remark") String remark,
                           HttpSession session) {
        //判断角色名是否已存在
        BlueRole isExist = blueRoleService.getRoleInfo(name);
        if (null != isExist) {
            return ResultInfo.error("角色已存在！");
        } else {
            //获取session内当前操作用户名
            BlueUser blueUser = (BlueUser) session.getAttribute("blueUser");

            BlueRole blueRole = new BlueRole();
            blueRole.setName(name);
            blueRole.setCode(code);
            blueRole.setCreator(blueUser.getName());
            blueRole.setModifier(blueUser.getName());
            blueRole.setRemark(remark);
            //插入数据库
            int id = blueRoleService.add(blueRole);
            if (id == 0) {
                return ResultInfo.error("新增角色失败！");
            }
            return ResultInfo.success("新建角色【" + name + "】成功!");
        }
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo detail(@PathVariable("id") int id) {
        if (id == 0) {
            return ResultInfo.error("角色ID不正确！");
        }
        BlueRole roleInfo = blueRoleService.getRoleInfo(id);
        if (roleInfo == null) {
            return ResultInfo.error("角色信息不存在！");
        }
        logger.info("查询角色【" + id + "】成功");
        return ResultInfo.success("SUCCESS", roleInfo);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo update(@PathVariable("id") int id,
                             @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "code", required = false) String code,
                             @RequestParam(value = "status", required = false) Integer status,
                             @RequestParam(value = "remark", required = false) String remark,
                             HttpSession session) {
        //入参判断
        if (id == 0) {
            return ResultInfo.error("角色ID不正确！");
        }
        if ((name == null || "".equals(name)) &&
                (code == null || "".equals(code)) &&
                (remark == null || "".equals(remark)) &&
                (status == null)) {
            return ResultInfo.error("请填写要修改的信息!!!");
        }

        BlueRole BlueRole = new BlueRole();
        BlueRole.setId(id);
        if (name != null && !"".equals(name)) {
            BlueRole.setName(name);
        }
        if (code != null && !"".equals(code)) {
            BlueRole.setCode(code);
        }
        if (remark != null && !"".equals(remark)) {
            BlueRole.setRemark(remark);
        }
        if (status == 0 || status == 1) {
            BlueRole.setStatus(status);
        }
        BlueUser blueUser = (BlueUser) session.getAttribute("blueUser");
        BlueRole.setModifier(blueUser.getName());

        //update数据库
        int count = blueRoleService.update(BlueRole);
        if (count != 0) {
            return ResultInfo.success("更新角色【" + id + "】信息成功！");
        } else {
            return ResultInfo.error("修改失败,请重试！");
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo deleteUser(@PathVariable("id") int id) {
        if (id == 0) {
            return ResultInfo.error("角色ID不正确！");
        }
        blueRoleService.delete(id);
        return ResultInfo.success("删除角色【" + id + "】成功！");
    }

    @RequestMapping(value = "/queryUserRoleInfoByPage", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo queryUserRoleInfoByPage(
            @RequestParam(value = "roleId", required = false) Integer roleId,
            @RequestParam(value = "searchContext", required = false) String searchContext,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "offset", required = false) Integer offset,
            @RequestParam(value = "limit", required = false) Integer limit) {

        int totalCount = blueRoleService.getUserRoleTotalCount(roleId, searchContext);
        if (totalCount > 0) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("roleId", roleId);
            paramMap.put("searchContext", searchContext);
            paramMap.put("limit", limit);
            paramMap.put("offset", offset);
            paramMap.put("order", order);
            paramMap.put("sort", sort);

            List<UserRoleVO> list = blueRoleService.queryUserRoleInfoByPage(paramMap);
            return ResultInfo.success(Integer.toString(totalCount), list);
        }
        return ResultInfo.error("没有关联数据！");
    }

    @RequestMapping(value = "/saveUserRole", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo saveUserRole(
            @RequestParam(value = "roleId") Integer roleId,
            @RequestParam(value = "userName") String userName,
            HttpSession session) {


        BlueUser blueUser = blueUserService.getUserInfo(userName);
        if (blueUser == null) {
            return ResultInfo.error("不存在此用户");
        }
        Boolean isExit = blueRoleService.checkRepeatUserCode(userName, roleId);
        if (isExit) {
            return ResultInfo.error("已关联此用户");
        }
        BlueUser blueSessionUser = (BlueUser) session.getAttribute("blueUser");
        String creator = blueSessionUser.getName();

        blueRoleService.saveUserRole(userName, roleId, creator);

        return ResultInfo.success();
    }

    @RequestMapping(value = "/deleteUserRoleById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo deleteUserRoleById(@PathVariable("id") int id) {
        if (id == 0) {
            return ResultInfo.error("ID不正确！");
        }
        blueRoleService.deleteUserRoleById(id);
        return ResultInfo.success();
    }


    @RequestMapping(value = "/saveAuthorityByRoleId", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo saveAuthorityByRoleId(
            @RequestParam(value = "roleId") Integer roleId,
            @RequestParam(value = "authorityIds") String[] authorityIds,
            HttpSession session) {
        BlueUser blueSessionUser = (BlueUser) session.getAttribute("blueUser");
        String creator = blueSessionUser.getName();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("roleId", roleId);
        paramMap.put("authorityIds", authorityIds);
        paramMap.put("creator", creator);

        blueRoleService.saveAuthorityByRoleId(paramMap);
        return ResultInfo.success("角色权限关联信息保存成功");
    }
}
