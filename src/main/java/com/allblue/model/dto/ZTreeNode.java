package com.allblue.model.dto;

/**
 * @Description:
 * @Author Xone
 * @Date 10:13 2018/11/27
 **/
public class ZTreeNode {
    private String id;

    private String pId;

    private  String name;

    // 默认展开
    private boolean open = false;

    // 默认选中
    private boolean checked = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ZTreeNode zTreeNode = (ZTreeNode) o;

        if (open != zTreeNode.open) return false;
        if (checked != zTreeNode.checked) return false;
        if (id != null ? !id.equals(zTreeNode.id) : zTreeNode.id != null) return false;
        if (pId != null ? !pId.equals(zTreeNode.pId) : zTreeNode.pId != null) return false;
        return name != null ? name.equals(zTreeNode.name) : zTreeNode.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (pId != null ? pId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (open ? 1 : 0);
        result = 31 * result + (checked ? 1 : 0);
        return result;
    }
}
