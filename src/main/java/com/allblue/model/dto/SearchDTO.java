package com.allblue.model.dto;

/**
 * @Description:
 * @Author Xone
 * @Date 16:48 2018/11/12
 **/
public class SearchDTO {
    //查询输入内容
    private String searchContext;
    //排序列
    private String sortName;
    //排序方式
    private String sortOrder;
    //页码
    private Integer limit;
    //页面条数
    private Integer offset;

    public String getSearchContext() {
        return searchContext;
    }

    public void setSearchContext(String searchContext) {
        this.searchContext = searchContext;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "SearchDTO{" +
                "searchContext='" + searchContext + '\'' +
                ", sortName='" + sortName + '\'' +
                ", sortOrder='" + sortOrder + '\'' +
                ", limit=" + limit +
                ", offset=" + offset +
                '}';
    }
}
