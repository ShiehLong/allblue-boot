package com.allblue.service;

import com.allblue.model.Role;

import java.util.List;

/**
 * @Description:
 * @Author Xone
 * @Date 17:30 2018/8/31
 **/
public interface RoleService {

    int addRole(Role role);

    int updateRole(Role role);

    List<Role> getRoleList();

    boolean deleteRole(Integer id);

    /**
     * @Description:根据角色ID查询信息
     * @Author Xone
     * @Date 15:56 2018/8/25
     **/
    Role getRoleInfo(int id);
}
