package com.allblue.service.impl;

import com.allblue.mapper.BlueRoleMapper;
import com.allblue.model.BlueRole;
import com.allblue.model.dto.SearchDTO;
import com.allblue.model.vo.SystemRoleVO;
import com.allblue.model.vo.UserRoleVO;
import com.allblue.service.BlueRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BlueRoleServiceImpl implements BlueRoleService {

    @Autowired
    private BlueRoleMapper blueRoleMapper;

    @Override
    public List<BlueRole> getRoleList(SearchDTO searchDTO) {
        return blueRoleMapper.selectRoleList(searchDTO);
    }

    @Override
    public int add(BlueRole blueRole) {
        blueRoleMapper.insert(blueRole);
        return blueRole.getId();
    }

    @Override
    public BlueRole getRoleInfo(int id) {
        BlueRole roleInfo = new BlueRole();
        roleInfo.setId(id);
        roleInfo = blueRoleMapper.selectRoleInfo(roleInfo);
        return roleInfo;
    }

    @Override
    public BlueRole getRoleInfo(String name) {
        BlueRole roleInfo = new BlueRole();
        roleInfo.setName(name);
        roleInfo = blueRoleMapper.selectRoleInfo(roleInfo);
        return roleInfo;
    }

    @Override
    public int delete(int id) {
        BlueRole roleInfo = new BlueRole();
        roleInfo.setId(id);
        roleInfo.setStatus(0);
        int count = blueRoleMapper.updateById(roleInfo);
        return count;
    }

    @Override
    public int getRoleTotalCount(String opts) {
        int count = blueRoleMapper.selectRoleTotalCount(opts);
        return count;
    }

    @Override
    public int update(BlueRole blueRole) {
        int count = blueRoleMapper.updateById(blueRole);
        return count;
    }

    @Override
    public int getUserRoleTotalCount(Integer roleId, String searchContext) {
        return blueRoleMapper.selectUserRoleTotalCount(roleId, searchContext);
    }

    @Override
    public List<UserRoleVO> queryUserRoleInfoByPage(Map<String, Object> map) {
        return blueRoleMapper.queryUserRoleInfoByPage(map);
    }

    @Override
    public Boolean checkRepeatUserCode(String userName, Integer roleId) {
        int count = blueRoleMapper.checkRepeatUserCode(userName, roleId);
        if (count > 0)
            return true;
        return false;
    }

    @Override
    public int saveUserRole(String userName, Integer roleId, String creator) {
        return blueRoleMapper.saveUserRole(userName,roleId,creator);
    }

    @Override
    public int deleteUserRoleById(int id) {
        return blueRoleMapper.deleteUserRoleById(id);
    }

    @Override
    public void saveAuthorityByRoleId(Map<String, Object> paramMap) {
        blueRoleMapper.deleteAuthorityByRoleId((Integer) paramMap.get("roleId"));
        if (((String[]) paramMap.get("authorityIds")).length > 0) {
            blueRoleMapper.saveAuthorityByRoleId(paramMap);
        }
    }

    @Override
    public List<BlueRole> getRoleListByUserName(String name) {
        return blueRoleMapper.getRoleListByUserName(name);
    }

    @Override
    public List<SystemRoleVO> getSystemRoleList() {
        return blueRoleMapper.getSystemRoleList();
    }
}
