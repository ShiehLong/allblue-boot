package com.allblue.service.impl;

import com.allblue.mapper.BlueUserMapper;
import com.allblue.model.BlueUser;
import com.allblue.model.dto.SearchDTO;
import com.allblue.service.BlueUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author Xone
 * @Date 11:47 2018/7/20
 **/
@Service
public class BlueUserServiceImpl implements BlueUserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BlueUserMapper blueUserMapper;

    @Override
    public int add(BlueUser userInfo) {
        blueUserMapper.insert(userInfo);
        int id = userInfo.getId();
//        logger.info("用户注册ID:" + id);
        return id;
    }

    @Override
    public int update(BlueUser userInfo) {
        int count = blueUserMapper.updateById(userInfo);
//        logger.info("更新用户信息条数:" + count);
        return count;
    }

    @Override
    public BlueUser getUserInfo(String name, String password) {
        BlueUser userInfo = new BlueUser();
        userInfo.setName(name);
        userInfo.setPassword(password);
        userInfo = blueUserMapper.selectUserInfo(userInfo);
//        logger.info("根据用户名密码查询结果【" + userInfo + "】");
        return userInfo;
    }

    @Override
    public BlueUser getUserInfo(String name) {
        BlueUser userInfo = new BlueUser();
        userInfo.setName(name);
        userInfo = blueUserMapper.selectUserInfo(userInfo);
//        logger.info("根据用户名查询结果【" + userInfo + "】");
        return userInfo;
    }

    @Override
    public BlueUser getUserInfo(int id) {
        BlueUser userInfo = new BlueUser();
        userInfo.setId(id);
        userInfo = blueUserMapper.selectUserInfo(userInfo);
//        logger.info("根据ID查询结果【" + userInfo + "】");
        return userInfo;
    }

    @Override
    public List<BlueUser> getUserList() {
        List<BlueUser> list = blueUserMapper.selectUserList();
//        for (BlueUser blueUser : list) {
//            logger.info("查询所有用户数据成功！" + blueUser);
//        }
        return list;
    }

    @Override
    public int delete(int id) {
        BlueUser userInfo = new BlueUser();
        userInfo.setId(id);
        userInfo.setStatus(0);
        int count = blueUserMapper.updateById(userInfo);
//        logger.info("用户置为无效条数:" + count);
        return count;
    }

    @Override
    public int getUserTotalCount(String opts) {
        int count = blueUserMapper.selectUserTotalCount(opts);
//        logger.info("查询用户条数为:" + count);
        return count;
    }

    @Override
    public List<BlueUser> getUserListBySearchDTO(SearchDTO searchDTO) {
        List<BlueUser> list = blueUserMapper.selectUserListBySearchDTO(searchDTO);
//        for (BlueUser blueUser : list) {
//            logger.info("查询用户数据成功！" + blueUser);
//        }
        return list;
    }
}
