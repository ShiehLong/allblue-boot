package com.allblue.mapper;

import com.allblue.model.BlueUser;
import com.allblue.model.dto.SearchDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlueUserMapper {
    /**
     * @Description:插入用户信息
     * @Author Xone
     * @Date 14:08 2018/8/25
     **/
    int insert(BlueUser userInfo);

    /**
     * @Description:根据用户信息查询用户
     * @Author Xone
     * @Date 17:00 2018/8/6
     **/
    BlueUser selectUserInfo(BlueUser userInfo);

    /**
     * @Description:查询所有用户信息
     * @Author Xone
     * @Date 14:08 2018/8/25
     **/
    List<BlueUser> selectUserList();

    /**
     * @Description:更新用户信息
     * @Author Xone
     * @Date 21:20 2018/8/25
     **/
    int updateById(BlueUser userInfo);

    /**
     * @Description:根据条件模糊查询数据条数
     * @Author Xone
     * @Date 15:07 2018/11/9
     **/
    int selectUserTotalCount(String opts);

    /**
     * @Description:根据条件模糊查询
     * @Author Xone
     * @Date 15:07 2018/11/9
     **/
    List<BlueUser> selectUserListBySearchDTO(SearchDTO searchDTO);
}