package com.allblue.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class BlueRole {
    //主键ID
    private Integer id;
    //角色名称
    private String name;
    //角色编码
    private String code;
    //状态
    private Integer status;
    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date created_time;
    //最后修改时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date modified_time;
    //创建者
    private String creator;
    //修改人
    private String modifier;
    //备注
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Date created_time) {
        this.created_time = created_time;
    }

    public Date getModified_time() {
        return modified_time;
    }

    public void setModified_time(Date modified_time) {
        this.modified_time = modified_time;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "BlueRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", created_time=" + created_time +
                ", modified_time=" + modified_time +
                ", creator='" + creator + '\'' +
                ", modifier='" + modifier + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
