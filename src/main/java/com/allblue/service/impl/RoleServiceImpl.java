package com.allblue.service.impl;

import com.allblue.mapper.RoleMapper;
import com.allblue.model.Role;
import com.allblue.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:操作role数据
 * @Author Xone
 * @Date 17:31 2018/8/31
 **/
@Service
public class RoleServiceImpl implements RoleService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public int addRole(Role role) {
        roleMapper.insert(role);
        int id = role.getId();
        logger.info("角色ID:" + id);
        return id;
    }

    @Override
    public int updateRole(Role role) {
        int count = roleMapper.updateRoleById(role);
        logger.info("更新角色信息条数:" + count);
        return count;
    }

    @Override
    public List<Role> getRoleList() {
        return roleMapper.selectRoleList();
    }

    @Override
    public boolean deleteRole(Integer id) {
        boolean flag = false;
        int count = roleMapper.deleteRoleById(id);
        logger.info("删除角色条数:" + count);
        if(count > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public Role getRoleInfo(int id) {
        Role roleInfo = new Role();
        roleInfo.setId(id);
        roleInfo = roleMapper.selectRoleInfo(roleInfo);
        logger.info("角色信息【" + roleInfo + "】");
        return roleInfo;
    }
}
