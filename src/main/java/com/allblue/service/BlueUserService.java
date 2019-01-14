package com.allblue.service;

import com.allblue.model.po.BlueUser;
import com.allblue.model.dto.SearchDTO;

import java.util.List;

/**
 * @Description:
 * @Author Xone
 * @Date 11:34 2018/7/20
 **/
public interface BlueUserService {
    /**
     * @Description:注册会员
     * @Author Xone
     * @Date 11:40 2018/7/20
     **/
    int add(BlueUser userInfo);

    /**
    * @Description:更新会员信息
    * @Author Xone
    * @Date 21:13 2018/8/25
    **/
    int update(BlueUser userInfo);

    /**
     * @Description:根据会员信息查询
     * @Author Xone
     * @Date 11:43 2018/7/20
     **/
    BlueUser getUserInfo(String username, String password);

    /**
     * @Description:根据用户名查询会员信息
     * @Author Xone
     * @Date 17:02 2018/8/6
     **/
    BlueUser getUserInfo(String username);

    /**
     * @Description:根据用户ID查询会员信息
     * @Author Xone
     * @Date 15:56 2018/8/25
     **/
    BlueUser getUserInfo(int id);

    /**
     * @Description:获取用户列表
     * @Author Xone
     * @Date 13:59 2018/8/25
     **/
    List<BlueUser> getUserList();

    /**
     * @Description:删除用户（置为无效）
     * @Author Xone
     * @Date 20:59 2018/11/5
     **/
    int delete(int id);

    /**
     * @Description:根据条件模糊查询用户列表
     * @Author Xone
     * @Date 14:53 2018/11/9
     **/
    int getUserTotalCount(String opts);

    /**
     * @Description:根据条件模糊查询用户列表
     * @Author Xone
     * @Date 14:53 2018/11/9
     **/
    List<BlueUser> getUserListBySearchDTO(SearchDTO searchDTO);
}

