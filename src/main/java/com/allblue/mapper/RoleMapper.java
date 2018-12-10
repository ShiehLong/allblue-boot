package com.allblue.mapper;

import com.allblue.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author Xone
 * @Date 17:31 2018/8/31
 **/
@Repository
public interface RoleMapper {
    /**
     * @Description:插入角色信息
     * @Author Xone
     * @Date 14:08 2018/8/25
     **/
    int insert(Role role);

    /**
     * @Description:更新角色信息
     * @Author Xone
     * @Date 21:20 2018/8/25
     **/
    int updateRoleById(Role role);

    /**
     * @Description:查询所有角色信息
     * @Author Xone
     * @Date 14:08 2018/8/25
     **/
    List<Role> selectRoleList();

    /**
     * @Description:删除角色信息
     * @Author Xone
     * @Date 14:08 2018/8/25
     **/
    int deleteRoleById(Integer id);

    /**
     * @Description:根据角色信息查询
     * @Author Xone
     * @Date 17:00 2018/8/6
     **/
    Role selectRoleInfo(Role role);
}
