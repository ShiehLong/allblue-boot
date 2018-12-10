package com.allblue.service.impl;

import com.allblue.mapper.BlueSystemMapper;
import com.allblue.model.BlueSystem;
import com.allblue.model.dto.ZTreeNode;
import com.allblue.service.BlueSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author Xone
 * @Date 17:39 2018/11/26
 **/
@Service
public class BlueSystemServiceImpl implements BlueSystemService {

    @Autowired
    private BlueSystemMapper blueSystemMapper;

    @Override
    public List<ZTreeNode> getSystemList() {

        List<ZTreeNode> lists = blueSystemMapper.selectSystemList();

        List<ZTreeNode> list = new ArrayList<ZTreeNode>();

        for (ZTreeNode treeNode : lists) {
            String pId = treeNode.getpId();
            if (pId == null || "".equals(pId)) {
                treeNode.setOpen(true);
            }
            list.add(treeNode);
        }
        return list;
    }

    @Override
    public int add(BlueSystem blueSystem) {
        blueSystemMapper.insert(blueSystem);
        return blueSystem.getId();
    }

    @Override
    public BlueSystem getSystemInfo(String code) {
        BlueSystem blueSystem = new BlueSystem();
        blueSystem.setCode(code);
        blueSystem = blueSystemMapper.selectSystemInfo(blueSystem);
        return blueSystem;
    }

    @Override
    public int update(BlueSystem blueSystem) {
        return blueSystemMapper.updateByCode(blueSystem);
    }

    @Override
    public void delete(String code) {
        BlueSystem blueSystem = new BlueSystem();
        blueSystem.setCode(code);
        blueSystem.setStatus(0);
        blueSystemMapper.updateByCode(blueSystem);
    }

    @Override
    public List<ZTreeNode> getAllSystem() {
        return blueSystemMapper.selectAllSystem();
    }

    @Override
    public List<String> getListByParentCode(String code) {
        return blueSystemMapper.selectListByParentCode(code);
    }

    @Override
    public List<ZTreeNode> getZTreeNodesForAuthAction(Integer roleId) {
        List<ZTreeNode> listAll = blueSystemMapper.selectSystemList();
        List<ZTreeNode> listSelf = blueSystemMapper.selectSystemListByRoleId(roleId);

        List<ZTreeNode> list = new ArrayList<ZTreeNode>();

        for (ZTreeNode treeNode : listAll) {
            //比对角色用户权限，有权限将选中状态置为true
            if (listSelf.contains(treeNode)) {
                treeNode.setChecked(true);
            }
            //如果父节点为空，说明为根节点，置为打开状态
            String pId = treeNode.getpId();
            if (pId == null || "".equals(pId)) {
                treeNode.setOpen(true);
            }
            list.add(treeNode);
        }
        return list;
    }
}
