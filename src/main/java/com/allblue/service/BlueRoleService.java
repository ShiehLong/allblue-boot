package com.allblue.service;

import com.allblue.model.po.BlueRole;
import com.allblue.model.dto.SearchDTO;
import com.allblue.model.vo.SystemRoleVO;
import com.allblue.model.vo.UserRoleVO;

import java.util.List;
import java.util.Map;

public interface BlueRoleService {

    /**
     * @Description: 根据条件模糊查询角色列表
     * @Author Xone
     * @Date 14:53 2018/11/9
     **/
    List<BlueRole> getRoleList(SearchDTO searchDTO);

    /**
     * @Description: 注册会员
     * @Author Xone
     * @Date 11:40 2018/7/20
     **/
    int add(BlueRole blueRole);

    /**
     * @Description: 根据角色ID查询信息
     * @Author Xone
     * @Date 15:56 2018/8/25
     **/
    BlueRole getRoleInfo(int id);

    /**
     * @Description: 根据角色ID查询信息
     * @Author Xone
     * @Date 15:56 2018/8/25
     **/
    BlueRole getRoleInfo(String name);

    /**
     * @Description: 删除角色（置为无效）
     * @Author Xone
     * @Date 20:59 2018/11/5
     **/
    int delete(int id);

    /**
     * @Description: 根据条件模糊查询角色列表
     * @Author Xone
     * @Date 14:53 2018/11/9
     **/
    int getRoleTotalCount(String opts);

    /**
     * @Description: 更新信息
     * @Author Xone
     * @Date 21:13 2018/8/25
     **/
    int update(BlueRole blueRole);

    /**
     * @Description: 根据条件模糊查询user-role关联关系数量
     * @Author Xone
     * @Date 19:53 2018/11/28
     **/
    int getUserRoleTotalCount(Integer roleId, String searchContext);

    /**
     * @Description: 根据条件模糊查询user-role关联关系列表
     * @Author Xone
     * @Date 19:53 2018/11/28
     **/
    List<UserRoleVO> queryUserRoleInfoByPage(Map<String, Object> map);

    /**
     * @Description: 判断数据user-role关联关系是否存在
     * @Author Xone
     * @Date 21:53 2018/11/28
     **/
    Boolean checkRepeatUserCode(String userName, Integer roleId);


    /**
     * @Description: 插入user-role关联关系
     * @Author Xone
     * @Date 22:53 2018/11/28
     **/
    int saveUserRole(String userName, Integer roleId, String creator);

    /**
     * @Description: 删除user-role关联关系
     * @Author Xone
     * @Date 22:53 2018/11/28
     **/
    int deleteUserRoleById(int id);

    /**
     * @Description: 保存system-role关联关系
     * @Author Xone
     * @Date 14:53 2018/11/29
     **/
    void saveAuthorityByRoleId(Map<String, Object> paramMap);

    /**
     * @Description: 根据UserName获取角色列表
     * @Author Xone
     * @Date 22:53 2018/11/29
     **/
    List<BlueRole> getRoleListByUserName(String name);

    /**
     * @Description: 获取所有system-role列表
     * @Author Xone
     * @Date 22:53 2018/11/29
     **/
    List<SystemRoleVO> getSystemRoleList();
}
