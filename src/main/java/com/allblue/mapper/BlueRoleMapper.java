package com.allblue.mapper;

import com.allblue.model.BlueRole;
import com.allblue.model.dto.SearchDTO;
import com.allblue.model.vo.SystemRoleVO;
import com.allblue.model.vo.UserRoleVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BlueRoleMapper {
    /**
     * @Description:插入角色信息
     * @Author Xone
     * @Date 14:08 2018/8/25
     **/
    int insert(BlueRole userInfo);

    /**
     * @Description:根据角色信息查询
     * @Author Xone
     * @Date 17:00 2018/8/6
     **/
    BlueRole selectRoleInfo(BlueRole blueInfo);

    /**
     * @Description:更新角色信息
     * @Author Xone
     * @Date 21:20 2018/8/25
     **/
    int updateById(BlueRole userInfo);

    /**
     * @Description:根据条件模糊查询数据条数
     * @Author Xone
     * @Date 15:07 2018/11/9
     **/
    int selectRoleTotalCount(String opts);

    /**
     * @Description:根据条件模糊查询
     * @Author Xone
     * @Date 15:07 2018/11/9
     **/
    List<BlueRole> selectRoleList(SearchDTO searchDTO);

    /**
     * @Description: 根据条件模糊查询数量
     * @Author Xone
     * @Date 19:53 2018/11/28
     **/
    int selectUserRoleTotalCount(@Param("roleId") Integer roleId, @Param("searchContext") String searchContext);

    /**
     * @Description: 根据条件模糊查询列表
     * @Author Xone
     * @Date 19:53 2018/11/28
     **/
    List<UserRoleVO> queryUserRoleInfoByPage(Map<String, Object> map);

    /**
     * @Description: 判断数据是否存在
     * @Author Xone
     * @Date 21:53 2018/11/28
     **/
    Integer checkRepeatUserCode(@Param("userName") String userName, @Param("roleId") Integer roleId);

    /**
     * @Description: 插入数据
     * @Author Xone
     * @Date 22:53 2018/11/28
     **/
    int saveUserRole(@Param("userName") String userName, @Param("roleId") Integer roleId, @Param("creator") String creator);

    /**
     * @Description: 删除user-role关联关系
     * @Author Xone
     * @Date 22:53 2018/11/28
     **/
    int deleteUserRoleById(int id);

    /**
     * @Description: 根据roleId删除system-role关联关系
     * @Author Xone
     * @Date 22:53 2018/11/28
     **/
    void deleteAuthorityByRoleId(Integer roleId);

    /**
     * @Description: 保存system-role关联关系
     * @Author Xone
     * @Date 22:53 2018/11/28
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