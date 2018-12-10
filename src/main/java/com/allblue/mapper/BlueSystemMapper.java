package com.allblue.mapper;

import com.allblue.model.BlueSystem;
import com.allblue.model.dto.ZTreeNode;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author Xone
 * @Date 17:38 2018/11/26
 **/
@Repository
public interface BlueSystemMapper {
    /**
     * @Description: 获取系统列表
     * @Author Xone
     * @Date 21:08 2018/11/26
     **/
    List<ZTreeNode> selectSystemList();

    /**
     * @Description: 插入系统数据
     * @Author Xone
     * @Date 21:08 2018/11/26
     **/
    void insert(BlueSystem blueSystem);

    /**
     * @Description: 获取系统信息
     * @Author Xone
     * @Date 21:08 2018/11/26
     **/
    BlueSystem selectSystemInfo(BlueSystem blueSystem);

    /**
     * @Description: 获取所有系统列表
     * @Author Xone
     * @Date 21:08 2018/11/26
     **/
    List<ZTreeNode> selectAllSystem();

    /**
     * @Description: 更新系统数据
     * @Author Xone
     * @Date 21:08 2018/11/26
     **/
    int updateByCode(BlueSystem blueSystem);

    /**
     * @Description: 根据parent'Code获取系统code列表
     * @Author Xone
     * @Date 21:08 2018/11/26
     **/
    List<String> selectListByParentCode(String code);

    /**
     * @Description: 根据roleId获取role-system菜单树
     * @Author Xone
     * @Date 22:53 2018/11/26
     **/
    List<ZTreeNode> selectSystemListByRoleId(Integer roleId);
}
