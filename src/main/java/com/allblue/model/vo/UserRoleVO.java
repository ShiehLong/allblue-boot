package com.allblue.model.vo;

import java.util.Date;

public class UserRoleVO {
    private Integer id;                 //bigint(20) unsigned NOT NULL主键
    private String userName;            //varchar(64) NULL用户code码
    private Integer roleId;             //bigint(20) NULL角色id
    private String roleName;            //角色名称
    private String creator;             //varchar(255) NOT NULL创建者
    private Date createdTime;            //timestamp NULL创建时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
