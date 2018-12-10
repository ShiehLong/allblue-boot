package com.allblue.model.vo;

/**
 * @Description:
 * @Author Xone
 * @Date 18:58 2018/12/1
 **/
public class SystemRoleVO {
    private Integer id;                 //bigint(20) unsigned NOT NULL主键
    private String systemCode;          //varchar(200) DEFAULT NULL COMMENT '菜单编号'
    private String url;                 //varchar(400) NULL  url地址
    private Integer roleId;             //bigint(20) NULL角色id
    private String roleCode;            //varchar(50) DEFAULT NULL COMMENT '角色编码'

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
